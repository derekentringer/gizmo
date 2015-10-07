package com.derekentringer.gizmo.model.structure.destroyable;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class BaseDestroyableModel extends BaseModel {

    private int mHealth;
    private boolean mDoesLootDrop;

    public BaseDestroyableModel() {
    }

    public BaseModelType getBaseModelType() {
        return mBaseModelType;
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

}