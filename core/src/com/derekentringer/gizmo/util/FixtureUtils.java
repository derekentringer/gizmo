package com.derekentringer.gizmo.util;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.derekentringer.gizmo.actor.data.UserData;
import com.derekentringer.gizmo.actor.data.UserDataType;

public class FixtureUtils {

    public static boolean fixtureIsPlayerHitArea(Fixture fixture) {
        UserData userData = (UserData) fixture.getUserData();
        return userData != null && userData.getUserDataType().equals(UserDataType.PLAYER_HIT_AREA);
    }

    public static boolean fixtureIsGround(Fixture fixture) {
        UserData userData = (UserData) fixture.getUserData();
        return userData != null && userData.getUserDataType().equals(UserDataType.GROUND);
    }

    public static boolean fixtureIsWall(Fixture fixture) {
        UserData userData = (UserData) fixture.getUserData();
        return userData != null && userData.getUserDataType().equals(UserDataType.WALL);
    }

}