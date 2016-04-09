package com.derekentringer.gizmo.model.item;

import com.derekentringer.gizmo.model.BaseModelType;

public class BombExplodeModel extends BasePlayerItemModel {

    public static final String BOMB_EXPOLODE = "BOMB_EXPOLODE";

    private static final int DEFAULT_HEALTH_DAMAGE = 10;

    public BombExplodeModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM_SECONDARY;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}