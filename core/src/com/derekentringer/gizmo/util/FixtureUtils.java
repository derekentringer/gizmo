package com.derekentringer.gizmo.util;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class FixtureUtils {

    private static final String TAG = FixtureUtils.class.getSimpleName();

    public static boolean fixtureIsPlayerHitAreaBottom(Fixture fixture) {
        BaseModel userData = (BaseModel) fixture.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.PLAYER_SENSOR_BOTTOM);
    }

    public static boolean fixtureIsGround(Fixture fixture) {
        BaseModel userData = (BaseModel) fixture.getUserData();
        return userData != null
                && (userData.getBaseModelType().equals(BaseModelType.GROUND)
                || userData.getBaseModelType().equals(BaseModelType.DESTROYABLE_BLOCK));
    }

    public static boolean fixtureIsDestroyable(Fixture fixture) {
        BaseModel userData = (BaseModel) fixture.getUserData();
        return userData != null && userData.getBaseModelType().equals(BaseModelType.DESTROYABLE_BLOCK);
    }

}