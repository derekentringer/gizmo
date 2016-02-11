package com.derekentringer.gizmo.model.item;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class BasePlayerItemModel extends BaseModel {

    public static final String PLAYER_ITEM = "PLAYER_ITEM";

    protected int mHealthDamage;
    protected String mItemType;

    public BasePlayerItemModel() {
    }

    public BaseModelType getBaseModelType() {
        return mBaseModelType;
    }

    public int getHealthDamage() {
        return mHealthDamage;
    }

    public void setHealthDamage(int healthDamage) {
        mHealthDamage = healthDamage;
    }

    public String getItemType() {
        return mItemType;
    }

    public void setItemType(String itemType) {
        mItemType = itemType;
    }

}