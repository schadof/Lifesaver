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
    static final float BOX_STEP=1/120f;
    static final int  BOX_VELOCITY_ITERATIONS=8;
    static final int BOX_POSITION_ITERATIONS=3;
    float accumulator;

    DisplayMetrics metrics = new DisplayMetrics();

    public void Gamearea(){
        createWorld();
        createSpikes();
    }

    public void createSpikes(){
        Body body;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(0, metrics.heightPixels);
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(metrics.widthPixels, 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
//        fixtureDef.density = 0.1f;
        body.createFixture(fixtureDef).setUserData(this);
//        body.setLinearVelocity(20, 0);
        shape.dispose();
    }


    public void createHenry(){
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
    }

    private void createWorld() {
        world = new World(new Vector2(0, 9.8f), false);
    }

    public void Update(float dt){
        accumulator+=dt;
        while(accumulator>BOX_STEP){
            world.step(BOX_STEP,BOX_VELOCITY_ITERATIONS,BOX_POSITION_ITERATIONS);
            accumulator-=BOX_STEP;
        }
    }
}
