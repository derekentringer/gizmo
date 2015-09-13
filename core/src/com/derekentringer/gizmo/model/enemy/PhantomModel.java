package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomModel extends EnemyModel {

    public static final String PHANTOM = "PHANTOM";

    private int mHealth = 4;
    private int mHealthDamage = 5;
    private boolean mDoesLootDrop;

    public PhantomModel(boolean dropsLoot) {
        super();
        mBaseModelType = BaseModelType.ENEMY;
        mDoesLootDrop = dropsLoot;
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

    @Override
    public boolean getDoesLootDrop() {
        return mDoesLootDrop;
    }

    @Override
    public void setDoesLootDrop(boolean doesLootDrop) {
        mDoesLootDrop = doesLootDrop;
    }

}