package com.myapp.lifesaver.android;

import android.util.DisplayMetrics;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class Gamearea {

    private World world;
    private double accumulator;
    private double currentTime;
    private float step = 1.0f / 60.0f;

    DisplayMetrics metrics = new DisplayMetrics();

    public void Gamearea(){
        createWorld();
        createSpikes();
    }

    public Body createSpikes(){
        Body body;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(1, metrics.heightPixels);
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(metrics.widthPixels, 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
//        fixtureDef.density = 0.1f;
        body.createFixture(fixtureDef).setUserData(this);
//        body.setLinearVelocity(20, 0);
        shape.dispose();

        return(body);
    }

    private void createWorld() {
        world = new World(new Vector2(0, 9.8f), false);
    }

    public void render() {
//        double newTime = TimeUtils.millis() / 1000.0;
//        double frameTime = Math.min(newTime - currentTime, 0.25);
//        float deltaTime = (float) frameTime;
        world.step(step, 6, 2);
    }
}
