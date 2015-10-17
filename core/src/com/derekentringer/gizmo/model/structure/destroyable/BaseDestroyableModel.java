package com.derekentringer.gizmo.model.structure.destroyable;

import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.model.BaseModel;

public class BaseDestroyableModel extends BaseModel {

    protected int mHealth;
    protected boolean mDoesLootDrop;
    protected Vector2 mBlockPosition = new Vector2();
    private boolean mIsPlayerStandingOn;

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