package com.derekentringer.gizmo.model.player_item.boomerang;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.player_item.BasePlayerItemModel;

public class BoomerangEmeraldModel extends BasePlayerItemModel {

    public static final String BOOMERANG_EMERALD = "BOOMERANG_EMERALD";

    private static final int DEFAULT_HEALTH_DAMAGE = 4;

    public BoomerangEmeraldModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}