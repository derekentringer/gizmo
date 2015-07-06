package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class BodyUtils {

    public static boolean bodyIsPlayer(Body body) {
        ObjectData userData = (ObjectData) body.getUserData();
        return userData != null && userData.getObjectDataType() == ObjectDataType.PLAYER;
    }

    public static boolean bodyIsGround(Body body) {
        ObjectData userData = (ObjectData) body.getUserData();
        return userData != null && userData.getObjectDataType().equals(ObjectDataType.GROUND);
    }

    public static boolean bodyIsEnemy(Body body) {
        ObjectData userData = (ObjectData) body.getUserData();
        return userData != null && userData.getObjectDataType().equals(ObjectDataType.ENEMY);
    }

    public static int getBodyDamageAmount(Body body) {
        ObjectData userData = (ObjectData) body.getUserData();
        return userData.getHealthDamage();
    }

    public static void applyLinearImpulseToBody(Body body, float force, String direction) {
        float velChange;
        Vector2 velocity = body.getLinearVelocity();
        if(direction.equalsIgnoreCase("x")) {
            velChange = force - velocity.x;
        }
        else {
            //TODO might cause an issue for other .y updates
            velChange = force - velocity.y / 2;
        }
        float impulse = body.getMass() * velChange;
        if(direction.equalsIgnoreCase("x")) {
            body.applyLinearImpulse(new Vector2(impulse, 0), body.getWorldCenter(), true);
        }
        else {
            body.applyLinearImpulse(new Vector2(0, impulse), body.getWorldCenter(), true);
        }
    }

}