package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomModel extends EnemyModel {

    public static final String PHANTOM = "PHANTOM";

    private int mHealth = 4;
    private int mHealthDamage = 5;

    public PhantomModel() {
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