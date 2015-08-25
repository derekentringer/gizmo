package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class FireBallModel extends EnemyModel {

    public static final String FIREBALL = "fireball";

    private int mHealthDamage = 5;

    public FireBallModel() {
        super();
        mBaseModelType = BaseModelType.ENEMY;
    }

    @Override
    public int getHealthDamage() {
        return mHealthDamage;
    }

    @Override
    public void setHealthDamage(int damage) {
        mHealthDamage = damage;
    }

}