package com.derekentringer.gizmo.model.structure.destroyable;

import com.derekentringer.gizmo.model.BaseModelType;

public class DestroyableBlockDirtModel extends BaseDestroyableModel {

    public static final String DESTROYABLE_BLOCK_DIRT = "DESTROYABLE_BLOCK_DIRT";

    private int mHealth = 2;
    private boolean mDoesLootDrop;

    public DestroyableBlockDirtModel(boolean doesLootDrop) {
        super();
        mBaseModelType = BaseModelType.DESTROYABLE_BLOCK;
        mDoesLootDrop = doesLootDrop;
    }

    @Override
    public int getHealth() {
        return mHealth;
    }

    @Override
    public void setHealth(int health) {
        mHealth = health;
    }

    @Override
    public boolean getDoesLootDrop() {
        return mDoesLootDrop;
    }

    @Override
    public void setDoesLootDrop(boolean doesLootDrop) {
        mDoesLootDrop = doesLootDrop;
    }

}