package com.derekentringer.gizmo.model.structure.destroyable;

import com.derekentringer.gizmo.model.BaseModelType;

public class DestroyableBlockDirtModel extends BaseDestroyableModel {

    public static final String DESTROYABLE_BLOCK_DIRT = "DESTROYABLE_BLOCK_DIRT";

    private static final int DEFAULT_HEALTH = 25;

    public DestroyableBlockDirtModel() {
    }

    public DestroyableBlockDirtModel(boolean doesLootDrop, float posX, float posY) {
        mBaseModelType = BaseModelType.BLOCK_DESTROYABLE;
        mHealth = DEFAULT_HEALTH;
        mDoesLootDrop = doesLootDrop;
        mBlockPosition.add(posX, posY);
    }

}