package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class FireBallModel extends BaseModel {

    public static final String FIREBALL = "fireball";

    public FireBallModel() {
        super();
        mBaseModelType = BaseModelType.FIREBALL;
    }

}