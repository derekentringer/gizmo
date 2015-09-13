package com.derekentringer.gizmo.model.object;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class DropHeartModel extends BaseModel {

    public static final String HEART_SMALL = "HEART_SMALL";

    public int mHealthAmount = 1;

    public DropHeartModel() {
        super();
        mBaseModelType = BaseModelType.HEART_SMALL;
    }

    public int getHealthAmount() {
        return mHealthAmount;
    }

    public void setHealthAmount(int healthAmount) {
        mHealthAmount = healthAmount;
    }

}