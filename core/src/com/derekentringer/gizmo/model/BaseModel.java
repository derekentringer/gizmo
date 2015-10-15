package com.derekentringer.gizmo.model;

public class BaseModel {

    public static final String TILE_IGNORE = "ignore";

    protected BaseModelType mBaseModelType;

    public BaseModel() {
    }

    public BaseModelType getBaseModelType() {
        return mBaseModelType;
    }

}