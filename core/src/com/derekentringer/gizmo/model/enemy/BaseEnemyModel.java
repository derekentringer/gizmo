package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModel;

public class BaseEnemyModel extends BaseModel {

    protected int mHealth;
    protected int mHealthDamage;
    protected boolean mDoesLootDrop;
    protected boolean mIsBoss;

    public BaseEnemyModel() {
    }

    public int getHealth() {
        return mHealth;
    }

    public void setHealth(int health) {
        mHealth = health;
    }

    public void setHealthDamage(int healthDamage) {
        mHealthDamage = healthDamage;
    }

    public int getHealthDamage() {
        return mHealthDamage;
    }

    public boolean getDoesLootDrop() {
        return mDoesLootDrop;
    }

    public void setDoesLootDrop(boolean doesLootDrop) {
        mDoesLootDrop = doesLootDrop;
    }

    public boolean isBoss() {
        return mIsBoss;
    }

    public void setIsBoss(boolean isBoss) {
        mIsBoss = isBoss;
    }

}