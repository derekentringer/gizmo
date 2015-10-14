package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomLargeModel extends BaseEnemyModel {

    public static final String PHANTOM_LARGE = "PHANTOM_LARGE";

    private int mHealth = 4;
    private int mHealthDamage = 10;
    private boolean mDoesLootDrop;
    private boolean mIsBoss;

    public PhantomLargeModel(boolean dropsLoot) {
        super();
        mBaseModelType = BaseModelType.ENEMY;
        mDoesLootDrop = dropsLoot;
        mIsBoss = true;
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

    @Override
    public boolean isBoss() {
        return mIsBoss;
    }

    @Override
    public void setIsBoss(boolean isBoss) {
        mIsBoss = isBoss;
    }

}