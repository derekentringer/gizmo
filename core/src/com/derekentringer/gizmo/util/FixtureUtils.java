package com.derekentringer.gizmo.util;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class FixtureUtils {

    public static boolean fixtureIsPlayerHitArea(Fixture fixture) {
        ObjectData userData = (ObjectData) fixture.getUserData();
        return userData != null && userData.getObjectDataType().equals(ObjectDataType.PLAYER_HIT_AREA);
    }

    public static boolean fixtureIsDoor(Fixture fixture) {
        ObjectData userData = (ObjectData) fixture.getUserData();
        return userData != null && userData.getObjectDataType().equals(ObjectDataType.DOOR);
    }

    public static boolean fixtureIsGround(Fixture fixture) {
        ObjectData userData = (ObjectData) fixture.getUserData();
        return userData != null && userData.getObjectDataType().equals(ObjectDataType.GROUND);
    }

    public static boolean fixtureIsWall(Fixture fixture) {
        ObjectData userData = (ObjectData) fixture.getUserData();
        return userData != null && userData.getObjectDataType().equals(ObjectDataType.WALL);
    }

}