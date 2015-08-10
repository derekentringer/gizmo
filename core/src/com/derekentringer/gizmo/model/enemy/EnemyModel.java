package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class EnemyModel extends BaseModel {

    private int mHealthDamage;

    public EnemyModel() {
    }

    public BaseModelType getBaseModelType() {
        return mBaseModelType;
    }

    public int getHealthDamage() {
        return mHealthDamage;
    }

    public void setHealthDamage(int damage) {
        mHealthDamage = damage;
    }

}