package com.derekentringer.gizmo.model.item;

import com.derekentringer.gizmo.model.BaseModelType;

public class BoomerangWoodModel extends BaseItemModel {

    public static final String BOOMERANG_WOOD = "BOOMERANG_WOOD";

    private static final int DEFAULT_HEALTH_DAMAGE = 2;

    public BoomerangWoodModel() {
        super();
        mBaseModelType = BaseModelType.PLAYER_ITEM;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}