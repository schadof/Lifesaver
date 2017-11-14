package com.myapp.lifesaver.android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;


public class GameArea extends Thread implements ContactListener, SensorEventListener {
    SensorManager sensorManager;
    Sensor sensor;

    Handler handler;
    World world;
    Body spikes;
    Body henry;
    Joint joint;

    DisplayMetrics metrics = new DisplayMetrics();

    public GameArea(Context context, Handler handler){
        this.handler = handler;
        world = new World(new Vector2(0, 9.8f), false);
        world.setContactListener(this);
        spikes = createSpikes(world, metrics);
        henry = createHenry(world, metrics);

        RopeJointDef ropeJointDef = new RopeJointDef();
        ropeJointDef.bodyA = spikes;
        ropeJointDef.bodyB = henry;
        ropeJointDef.maxLength = metrics.heightPixels / 2;

        joint = world.createJoint(ropeJointDef);

        // ask for sensor manager
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // get the default gyro
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void run() {
        long minDeltaTime = 16;

        long lastUpdate = System.currentTimeMillis();

        while (true) {
            long time = System.currentTimeMillis();
            if (time - lastUpdate < minDeltaTime) {
                try {
                    Thread.sleep(minDeltaTime - (time - lastUpdate));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
                time = System.currentTimeMillis();
            }
            float deltaTime = (time - lastUpdate) * 0.001f;
            lastUpdate = time;
            update(deltaTime);
        }
    }

    private static Body createSpikes(World world, DisplayMetrics metrics){
        Body body;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(0, metrics.heightPixels);
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(metrics.widthPixels, 100);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
//        fixtureDef.density = 0.1f;
//        body.setLinearVelocity(20, 0);
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }


    private static Body createHenry(World world, DisplayMetrics metrics){
        Body body;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = false;
        def.position.set(metrics.widthPixels/2, metrics.heightPixels/2);
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(100, 100);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
//        fixtureDef.density = 0.1f;
        body.createFixture(fixtureDef);
//        body.setLinearVelocity(20, 0);
        shape.dispose();
        return body;
    }

    public void update(float dt){
        world.step(dt, 8, 3);
        Message message = handler.obtainMessage(
                0,
                new HenryMsg(henry.getPosition(), henry.getAngle())
        );
        handler.sendMessage(message);
    }

    @Override
    public void beginContact(Contact contact) {
        //set to gameover screen
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        henry.applyForceToCenter(sensorEvent.values[0], sensorEvent.values[1], true);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public static class HenryMsg {
        Vector2 pos;
        float ang;

        public HenryMsg(Vector2 pos, float ang) {
            this.pos = pos;
            this.ang = ang;
        }

        @Override
        public String toString() {
            return "HenryMsg{" +
                    "pos=" + pos +
                    ", ang=" + ang +
                    '}';
        }
    }
}
