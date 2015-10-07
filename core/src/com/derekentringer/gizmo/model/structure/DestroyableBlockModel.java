package com.derekentringer.gizmo.model.structure;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class DestroyableBlockModel extends BaseModel {

    public static final String DESTROYABLE_BLOCK_DIRT = "DESTROYABLE_BLOCK_DIRT";

    public DestroyableBlockModel() {
        super();
        mBaseModelType = BaseModelType.DESTROYABLE_BLOCK;
    }

}