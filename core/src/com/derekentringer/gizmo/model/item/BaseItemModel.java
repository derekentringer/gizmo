package com.derekentringer.gizmo.model.item;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class BaseItemModel extends BaseModel {

    private int mHealthDamage;

    public BaseItemModel() {
    }

    public BaseModelType getBaseModelType() {
        return mBaseModelType;
    }

    public int getHealthDamage() {
        return mHealthDamage;
    }

}