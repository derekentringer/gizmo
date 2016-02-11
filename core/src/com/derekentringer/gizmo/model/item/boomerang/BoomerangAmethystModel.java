package com.derekentringer.gizmo.model.item.boomerang;

import com.derekentringer.gizmo.model.BaseModelType;

public class BoomerangAmethystModel extends com.derekentringer.gizmo.model.item.BasePlayerItemModel {

    public static final String BOOMERANG_AMETHYST = "BOOMERANG_AMETHYST";

    private static final int DEFAULT_HEALTH_DAMAGE = 6;

    public BoomerangAmethystModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}