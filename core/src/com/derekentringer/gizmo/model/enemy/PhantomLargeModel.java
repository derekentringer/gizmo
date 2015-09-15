package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomLargeModel extends EnemyModel {

    public static final String PHANTOM_LARGE = "PHANTOM_LARGE";

    private int mHealth = 60;
    private int mHealthDamage = 10;

    public PhantomLargeModel() {
        super();
        mBaseModelType = BaseModelType.ENEMY;
    }

    @Override
    public int getHealth() {
        return mHealth;
    }

    @Override
    public void setHealth(int health) {
        mHealth = health;
    }

    @Override
    public int getHealthDamage() {
        return mHealthDamage;
    }

}