package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.model.BaseModel;

public class EnemyUtils {

    public static Body createPhantom(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(coordinates.x), WorldUtils.ppmCalc(coordinates.y));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(6), WorldUtils.ppmCalc(13));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        body.createFixture(fixtureDef).setUserData(userData);

        body.setUserData(userData);

        shape.dispose();
        return body;
    }

    public static Body createLargePhantom(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(coordinates.x), WorldUtils.ppmCalc(coordinates.y));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(100), WorldUtils.ppmCalc(140));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        body.createFixture(fixtureDef).setUserData(userData);

        body.setUserData(userData);

        shape.dispose();
        return body;
    }

}