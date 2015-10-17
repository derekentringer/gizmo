package com.derekentringer.gizmo.model.structure.destroyable;

import com.derekentringer.gizmo.model.BaseModelType;

public class DestroyableBlockFallModel extends BaseDestroyableModel {

    public static final String DESTROYABLE_BLOCK_FALL = "DESTROYABLE_BLOCK_FALL";

    public DestroyableBlockFallModel() {
    }

    public DestroyableBlockFallModel(float posX, float posY) {
        mBaseModelType = BaseModelType.BLOCK_DESTROYABLE_FALL;
        mBlockPosition.add(posX, posY);
    }

}