package com.derekentringer.gizmo.model.item;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class BaseItemModel extends BaseModel {

    public static final String PLAYER_ITEM = "PLAYER_ITEM";

    public static final String BOOMERANG = "BOOMERANG";

    private int mHealthDamage;
    private String mItemType;

    public BaseItemModel() {
    }

    public BaseModelType getBaseModelType() {
        return mBaseModelType;
    }

    public int getHealthDamage() {
        return mHealthDamage;
    }

    public String getItemType() {
        return mItemType;
    }

}