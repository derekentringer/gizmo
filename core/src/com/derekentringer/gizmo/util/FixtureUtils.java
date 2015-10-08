package com.derekentringer.gizmo.util;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class FixtureUtils {

    private static final String TAG = FixtureUtils.class.getSimpleName();

    public static boolean fixtureIsPlayerHitArea(Fixture fixture) {
        BaseModel userData = (BaseModel) fixture.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.PLAYER_HIT_AREA);
    }

    public static boolean fixtureIsDoor(Fixture fixture) {
        BaseModel userData = (BaseModel) fixture.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.DOOR);
    }

    public static boolean fixtureIsGround(Fixture fixture) {
        BaseModel userData = (BaseModel) fixture.getUserData();
        return userData != null
                && (userData.getBaseModelType().equals(BaseModelType.GROUND)
                || userData.getBaseModelType().equals(BaseModelType.DESTROYABLE_BLOCK));
    }

    public static boolean fixtureIsWall(Fixture fixture) {
        BaseModel userData = (BaseModel) fixture.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.WALL);
    }

}