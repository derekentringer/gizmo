package com.derekentringer.gizmo.model.player_item.boomerang;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.player_item.BasePlayerItemModel;

public class BoomerangAmethystModel extends BasePlayerItemModel {

    public static final String BOOMERANG_AMETHYST = "BOOMERANG_AMETHYST";

    private static final int DEFAULT_HEALTH_DAMAGE = 6;

    public BoomerangAmethystModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}