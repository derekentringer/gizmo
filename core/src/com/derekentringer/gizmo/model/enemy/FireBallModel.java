package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class FireBallModel extends BaseEnemyModel {

    public static final String FIREBALL = "FIREBALL";

    private static final int DEFAULT_HEALTH_DAMAGE = 5;

    public FireBallModel() {
        mBaseModelType = BaseModelType.ENEMY;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}