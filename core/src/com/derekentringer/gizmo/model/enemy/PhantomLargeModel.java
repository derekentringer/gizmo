package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomLargeModel extends EnemyModel {

    public static final String PHANTOM_LARGE = "PHANTOM_LARGE";

    private int mHealthDamage = 10;

    public PhantomLargeModel() {
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