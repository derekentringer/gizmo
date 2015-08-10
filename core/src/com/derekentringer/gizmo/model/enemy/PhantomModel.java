package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomModel extends EnemyModel {

    public static final String PHANTOM = "phantom";
    private int mHealthDamage = 5;

    public PhantomModel() {
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