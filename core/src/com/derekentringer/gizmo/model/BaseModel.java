package com.derekentringer.gizmo.model;

public abstract class BaseModel {

    protected BaseModelType baseModelType;

    public int healthDamage;

    public BaseModel() {
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