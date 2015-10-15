package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class FireBallModel extends BaseEnemyModel {

    public static final String FIREBALL = "FIREBALL";

    public FireBallModel() {
        super();
        mBaseModelType = BaseModelType.ENEMY;
        mHealthDamage = 5;
    }

}