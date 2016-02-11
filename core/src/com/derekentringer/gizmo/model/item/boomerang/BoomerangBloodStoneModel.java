package com.derekentringer.gizmo.model.item.boomerang;

import com.derekentringer.gizmo.model.BaseModelType;

public class BoomerangBloodStoneModel extends com.derekentringer.gizmo.model.item.BasePlayerItemModel {

    public static final String BOOMERANG_BLOODSTONE = "BOOMERANG_BLOODSTONE";

    private static final int DEFAULT_HEALTH_DAMAGE = 8;

    public BoomerangBloodStoneModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}