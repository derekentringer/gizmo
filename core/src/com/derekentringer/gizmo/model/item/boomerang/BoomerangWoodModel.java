package com.derekentringer.gizmo.model.item.boomerang;

import com.derekentringer.gizmo.model.BaseModelType;

public class BoomerangWoodModel extends com.derekentringer.gizmo.model.item.BasePlayerItemModel {

    public static final String BOOMERANG_WOOD = "BOOMERANG_WOOD";

    private static final int DEFAULT_HEALTH_DAMAGE = 2;

    public BoomerangWoodModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM_PRIMARY;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}