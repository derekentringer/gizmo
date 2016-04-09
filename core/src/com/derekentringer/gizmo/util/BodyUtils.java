package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.settings.Constants;

public class BodyUtils {

    private static final String TAG = BodyUtils.class.getSimpleName();

    public static boolean bodyTypeCheck(Body body, BaseModelType baseModelType) {
        BaseModel userData = (BaseModel) body.getUserData();
        return userData != null && userData.getBaseModelType().equals(baseModelType);
    }

    public static void applyLinearImpulseToBody(Body body, float force, String direction) {
        float velChange;
        Vector2 velocity = body.getLinearVelocity();
        if (direction.equalsIgnoreCase("x")) {
            velChange = force - velocity.x;
        }
        else {
            //TODO might cause an issue for other .y updates
            velChange = force - velocity.y / 2;
        }
        float impulse = body.getMass() * velChange;
        if (direction.equalsIgnoreCase("x")) {
            body.applyLinearImpulse(new Vector2(impulse, 0), body.getWorldCenter(), true);
        }
        else {
            body.applyLinearImpulse(new Vector2(0, impulse), body.getWorldCenter(), true);
        }
    }

    public static Body createGroundBody(BaseModel userData, World world, float tileSize, int row, int col, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(
                (col + 0.5f) * tileSize / Constants.PPM,
                (row + 0.5f) * tileSize / Constants.PPM
        );

        //don't have to use chainshape here...there are other options
        ChainShape chainShape = new ChainShape();
        Vector2[] v = new Vector2[5];
        v[0] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[1] = new Vector2(-tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[2] = new Vector2(tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[3] = new Vector2(tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[4] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        chainShape.createChain(v);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.shape = chainShape;
        fixtureDef.isSensor = isSensor;

        fixtureDef.filter.categoryBits = Constants.GROUND_ENTITY | Constants.WORLD_ENTITY;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(userData);
        body.setUserData(userData);

        chainShape.dispose();
        return body;
    }

    public static Body createWallBody(BaseModel userData, World world, float tileSize, int row, int col, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(
                (col + 0.5f) * tileSize / Constants.PPM,
                (row + 0.5f) * tileSize / Constants.PPM
        );

        //don't have to use chainshape here...there are other options
        ChainShape chainShape = new ChainShape();
        Vector2[] v = new Vector2[5];
        v[0] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[1] = new Vector2(-tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[2] = new Vector2(tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[3] = new Vector2(tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[4] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        chainShape.createChain(v);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.shape = chainShape;
        fixtureDef.isSensor = isSensor;

        fixtureDef.filter.categoryBits = Constants.WALL_ENTITY | Constants.WORLD_ENTITY;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(userData);
        body.setUserData(userData);

        chainShape.dispose();
        return body;
    }

    //TODO cleanup
    public static Body createStaticWorldBody(BaseModel userData, World world, float tileSize, int row, int col, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(
                (col + 0.5f) * tileSize / Constants.PPM,
                (row + 0.5f) * tileSize / Constants.PPM
        );

        //don't have to use chainshape here...there are other options
        ChainShape chainShape = new ChainShape();
        Vector2[] v = new Vector2[5];
        v[0] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[1] = new Vector2(-tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[2] = new Vector2(tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[3] = new Vector2(tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[4] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        chainShape.createChain(v);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.shape = chainShape;
        fixtureDef.isSensor = isSensor;

        fixtureDef.filter.categoryBits = Constants.WORLD_ENTITY;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(userData);
        body.setUserData(userData);

        chainShape.dispose();
        return body;
    }

    //TODO cleanup
    public static Body createFallingBlockBody(BaseModel userData, World world, float tileSize, int row, int col, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                (col + 0.5f) * tileSize / Constants.PPM,
                (row + 0.5f) * tileSize / Constants.PPM
        );

        //don't have to use chainshape here...there are other options
        ChainShape chainShape = new ChainShape();
        Vector2[] v = new Vector2[5];
        v[0] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[1] = new Vector2(-tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[2] = new Vector2(tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[3] = new Vector2(tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[4] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        chainShape.createChain(v);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.shape = chainShape;
        fixtureDef.isSensor = isSensor;

        fixtureDef.filter.categoryBits = Constants.WORLD_ENTITY;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(userData);
        body.setUserData(userData);

        chainShape.dispose();
        return body;
    }

    public static Body createDestroyableBody(BaseModel userData, World world, float tileSize, int row, int col, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(
                (col + 0.5f) * tileSize / Constants.PPM,
                (row + 0.5f) * tileSize / Constants.PPM
        );

        //don't have to use chainshape here...there are other options
        ChainShape chainShape = new ChainShape();
        Vector2[] v = new Vector2[5];
        v[0] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[1] = new Vector2(-tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[2] = new Vector2(tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[3] = new Vector2(tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[4] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        chainShape.createChain(v);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.shape = chainShape;
        fixtureDef.isSensor = isSensor;

        fixtureDef.filter.categoryBits = Constants.DESTROYABLE | Constants.WORLD_ENTITY;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(userData);
        body.setUserData(userData);

        chainShape.dispose();
        return body;
    }

    public static Body createBoomerang(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(coordinates.x, coordinates.y);
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;

        fixtureDef.filter.categoryBits = Constants.PLAYER_ATTACK_ENTITY;
        fixtureDef.filter.maskBits = Constants.ENEMY_ENTITY;

        body.createFixture(fixtureDef).setUserData(userData);

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    public static Body createBomb(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(coordinates.x, coordinates.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;

        fixtureDef.filter.categoryBits = Constants.PLAYER_ATTACK_ENTITY;
        fixtureDef.filter.maskBits = Constants.ENEMY_ENTITY | Constants.DESTROYABLE | Constants.GROUND_ENTITY;

        body.createFixture(fixtureDef).setUserData(userData);

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    public static Body createBombExplosion(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(coordinates.x, coordinates.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(28), WorldUtils.ppmCalc(28));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;

        body.createFixture(fixtureDef).setUserData(userData);

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

}