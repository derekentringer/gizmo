package com.derekentringer.gizmo.model.structure.destroyable;

import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.model.BaseModel;

public class BaseDestroyableModel extends BaseModel {

    private int mHealth;
    private boolean mDoesLootDrop;
    private Vector2 mBlockPosition;

    public BaseDestroyableModel() {
    }

    public int getHealth() {
        return mHealth;
    }

    public void setHealth(int health) {
        mHealth = health;
    }

    public boolean getDoesLootDrop() {
        return mDoesLootDrop;
    }

    public void setDoesLootDrop(boolean doesLootDrop) {
        mDoesLootDrop = doesLootDrop;
    }

    public Vector2 getPosition() {
        return mBlockPosition;
    }

    public void setPosition(Vector2 position) {
        mBlockPosition = position;
    }

}