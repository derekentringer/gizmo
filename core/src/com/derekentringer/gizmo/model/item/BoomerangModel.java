package com.derekentringer.gizmo.model.item;

import com.derekentringer.gizmo.model.BaseModelType;

public class BoomerangModel extends BaseItemModel {

    public static final String BOOMERANG_WOOD = "BOOMERANG_WOOD";

    private int mHealthDamage = 2;

    public BoomerangModel() {
        super();
        mBaseModelType = BaseModelType.PLAYER_ITEM;
    }

    @Override
    public int getHealthDamage() {
        return mHealthDamage;
    }

}