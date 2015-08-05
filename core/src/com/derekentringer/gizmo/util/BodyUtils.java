package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.settings.Constants;

public class BodyUtils {

    public static boolean bodyIsPlayer(Body body) {
        BaseModel userData = (BaseModel) body.getUserData();
        return userData != null && userData.getBaseModelType() == BaseModelType.PLAYER;
    }

    public static boolean bodyIsGround(Body body) {
        BaseModel userData = (BaseModel) body.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.GROUND);
    }

    public static boolean bodyIsEnemy(Body body) {
        BaseModel userData = (BaseModel) body.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.ENEMY);
    }

    public static boolean bodyIsKey(Body body) {
        BaseModel userData = (BaseModel) body.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.KEY);
    }

    public static boolean bodyIsHeart(Body body) {
        BaseModel userData = (BaseModel) body.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.HEART);
    }

    public static boolean bodyIsDoor(Body body) {
        BaseModel userData = (BaseModel) body.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.DOOR);
    }

    public static boolean bodyIsDoorOff(Body body) {
        BaseModel userData = (BaseModel) body.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.DOOR_OFF);
    }

    public static int getBodyDamageAmount(Body body) {
        BaseModel userData = (BaseModel) body.getUserData();
        return userData.getHealthDamage();
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

    public static Body createStaticBody(BaseModel userData, World world, float tileSize, int row, int col, boolean isSensor) {
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
        fixtureDef.filter.categoryBits = 1;
        fixtureDef.filter.maskBits = -1;
        fixtureDef.isSensor = isSensor;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(userData);
        body.setUserData(userData);

        chainShape.dispose();
        return body;
    }

}