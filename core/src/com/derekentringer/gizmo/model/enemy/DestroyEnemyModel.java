package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class DestroyEnemyModel extends BaseModel {

    public static final String DESTROY_ENEMY = "DESTROY_ENEMY";

    public DestroyEnemyModel() {
        mBaseModelType = BaseModelType.DESTROY_ENEMY;
    }

}