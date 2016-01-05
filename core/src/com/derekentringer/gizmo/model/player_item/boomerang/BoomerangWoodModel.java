package com.derekentringer.gizmo.model.player_item.boomerang;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.player_item.BasePlayerItemModel;

public class BoomerangWoodModel extends BasePlayerItemModel {

    public static final String BOOMERANG_WOOD = "BOOMERANG_WOOD";

    private static final int DEFAULT_HEALTH_DAMAGE = 2;

    public BoomerangWoodModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}