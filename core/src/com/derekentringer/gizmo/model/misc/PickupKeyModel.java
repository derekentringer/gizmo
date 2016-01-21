package com.derekentringer.gizmo.model.misc;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class PickupKeyModel extends BaseModel {

    public static final String PICKUP_KEY = "PICKUP_KEY";

    public PickupKeyModel() {
        mBaseModelType = BaseModelType.PICKUP_LIFE;
    }

}