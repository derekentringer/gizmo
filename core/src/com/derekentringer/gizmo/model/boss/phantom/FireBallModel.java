package com.derekentringer.gizmo.model.boss.phantom;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.enemy.BaseEnemyModel;

public class FireBallModel extends BaseEnemyModel {

    public static final String FIREBALL = "FIREBALL";

    private static final int DEFAULT_HEALTH_DAMAGE = 5;

    public FireBallModel() {
        mBaseModelType = BaseModelType.ENEMY;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}