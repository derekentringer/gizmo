package com.derekentringer.gizmo.model.item.boomerang;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.item.BaseItemModel;

public class BoomerangEmeraldModel extends BaseItemModel {

    public static final String BOOMERANG_EMERALD = "BOOMERANG_EMERALD";

    private static final int DEFAULT_HEALTH_DAMAGE = 4;

    public BoomerangEmeraldModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
    }

}