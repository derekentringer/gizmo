package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class BaseEnemyModel extends BaseModel {

    private int mHealth;
    private int mHealthDamage;
    private boolean mDoesLootDrop;

    public BaseEnemyModel() {
    }

    public BaseModelType getBaseModelType() {
        return mBaseModelType;
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

}