package com.derekentringer.gizmo.model;

public abstract class BaseModel {

    public static final String TILE_IGNORE = "ignore";

    protected BaseModelType mBaseModelType;

    public BaseModel() {
    }

    public BaseModelType getBaseModelType() {
        return mBaseModelType;
    }

}