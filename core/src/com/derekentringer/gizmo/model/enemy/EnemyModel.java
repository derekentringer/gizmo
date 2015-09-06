package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class EnemyModel extends BaseModel {

    private int mHealth;
    private int mHealthDamage;

    public EnemyModel() {
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

    public int getHealthDamage() {
        return mHealthDamage;
    }

}