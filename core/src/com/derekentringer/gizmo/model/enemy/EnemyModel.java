package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class EnemyModel extends BaseModel {

    protected BaseModelType baseModelType;
    private int healthDamage;

    public EnemyModel() {
    }

    public BaseModelType getBaseModelType() {
        return baseModelType;
    }

    public int getHealthDamage() {
        return healthDamage;
    }

    public void setHealthDamage(int damage) {
        healthDamage = damage;
    }

}