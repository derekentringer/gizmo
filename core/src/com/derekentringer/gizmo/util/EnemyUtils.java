package com.derekentringer.gizmo.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class EnemyUtils {

    public static Body createPhantom(ObjectData userData, World world, int posX, int posY) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(posX), WorldUtils.ppmCalc(posY));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        body.createFixture(fixtureDef).setUserData(userData);

        body.setUserData(userData);

        shape.dispose();
        return body;
    }

    public static Body createLargePhantom(ObjectData userData, World world, int posX, int posY) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(posX), WorldUtils.ppmCalc(posY));
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