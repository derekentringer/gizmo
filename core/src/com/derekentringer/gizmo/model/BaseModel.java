package com.derekentringer.gizmo.model;

public abstract class BaseModel {

    protected BaseModelType baseModelType;

    public int healthDamage;

    public BaseModel() {
    }

    public BaseModelType getBaseModelType() {
        return baseModelType;
    }

}