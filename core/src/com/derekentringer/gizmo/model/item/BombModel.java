package com.derekentringer.gizmo.model.item;

import com.derekentringer.gizmo.model.BaseModelType;

public class BombModel extends BasePlayerItemModel {

    public static final String BOMB = "BOMB";

    private static final int DEFAULT_HEALTH_DAMAGE = 1;

    public BombModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM_SECONDARY;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}