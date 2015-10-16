package com.derekentringer.gizmo.model.structure.destroyable;

import com.derekentringer.gizmo.model.BaseModelType;

public class DestroyableBlockDirtModel extends BaseDestroyableModel {

    public static final String DESTROYABLE_BLOCK_DIRT = "DESTROYABLE_BLOCK_DIRT";

    private static final int DEFAULT_HEALTH = 50;

    public DestroyableBlockDirtModel() {
    }

    public DestroyableBlockDirtModel(boolean doesLootDrop, float posX, float posY) {
        super();
        mBaseModelType = BaseModelType.DESTROYABLE_BLOCK;
        mHealth = DEFAULT_HEALTH;
        mDoesLootDrop = doesLootDrop;
        mBlockPosition.add(posX, posY);
    }

}