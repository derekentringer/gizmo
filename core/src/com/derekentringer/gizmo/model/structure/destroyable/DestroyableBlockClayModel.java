package com.derekentringer.gizmo.model.structure.destroyable;

import com.derekentringer.gizmo.model.BaseModelType;

public class DestroyableBlockClayModel extends BaseDestroyableModel {

    public static final String DESTROYABLE_BLOCK_CLAY = "DESTROYABLE_BLOCK_CLAY";

    private static final int DEFAULT_HEALTH = 100;

    public DestroyableBlockClayModel() {
    }

    public DestroyableBlockClayModel(boolean doesLootDrop, float posX, float posY) {
        mBaseModelType = BaseModelType.BLOCK_DESTROYABLE;
        mHealth = DEFAULT_HEALTH;
        mDoesLootDrop = doesLootDrop;
        mBlockPosition.add(posX, posY);
    }

}