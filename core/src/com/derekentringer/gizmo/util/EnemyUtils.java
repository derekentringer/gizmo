package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.settings.Constants;

public class EnemyUtils {

    private static final String TAG = EnemyUtils.class.getSimpleName();

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

        fixtureDef.filter.categoryBits = Constants.ENEMY_ENTITY;
        fixtureDef.filter.maskBits = Constants.WORLD_ENTITY | Constants.PLAYER_ENTITY | Constants.PLAYER_ATTACK_ENTITY;

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

        shape.setAsBox(WorldUtils.ppmCalc(100), WorldUtils.ppmCalc(150));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;

        fixtureDef.filter.categoryBits = Constants.ENEMY_ENTITY;
        fixtureDef.filter.maskBits = Constants.WORLD_ENTITY | Constants.PLAYER_ENTITY | Constants.PLAYER_ATTACK_ENTITY;

        body.createFixture(fixtureDef).setUserData(userData);

        body.setUserData(userData);

        shape.dispose();
        return body;
    }

    public static Body createFireBall(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(coordinates.x), WorldUtils.ppmCalc(coordinates.y));
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(13), WorldUtils.ppmCalc(13));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;

        fixtureDef.filter.categoryBits = Constants.ENEMY_ATTACK_ENTITY;
        fixtureDef.filter.maskBits = Constants.ENEMY_ENTITY;
        
        body.createFixture(fixtureDef).setUserData(userData);

        body.setUserData(userData);

        shape.dispose();
        return body;
    }

}