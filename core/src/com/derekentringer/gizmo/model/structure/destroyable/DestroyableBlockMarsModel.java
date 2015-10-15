package com.derekentringer.gizmo.model.structure.destroyable;

import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.model.BaseModelType;

public class DestroyableBlockMarsModel extends BaseDestroyableModel {

    public static final String DESTROYABLE_BLOCK_MARS = "DESTROYABLE_BLOCK_MARS";

    private int mHealth = 1000000;
    private boolean mDoesLootDrop;
    private Vector2 mBlockPosition = new Vector2();

    public DestroyableBlockMarsModel() {

    }

    public DestroyableBlockMarsModel(boolean doesLootDrop, float posX, float posY) {
        super();
        mBaseModelType = BaseModelType.DESTROYABLE_BLOCK;
        mDoesLootDrop = doesLootDrop;
        mBlockPosition.add(posX, posY);
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

    @Override
    public void setPosition(Vector2 position) {
        mBlockPosition = position;
    }

    @Override
    public Vector2 getPosition() {
        return mBlockPosition;
    }

}