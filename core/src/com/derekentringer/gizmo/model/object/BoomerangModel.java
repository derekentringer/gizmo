package com.derekentringer.gizmo.model.object;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;

public class BoomerangModel extends BasePlayerItemModel {

    public static final String BOOMERANG = "BOOMERANG";

    public static final String BOOMERANG_WOOD = "BOOMERANG_WOOD";
    public static final String BOOMERANG_AMETHYST = "BOOMERANG_AMETHYST";
    public static final String BOOMERANG_EMERALD = "BOOMERANG_EMERALD";
    public static final String BOOMERANG_BLOODSTONE = "BOOMERANG_BLOODSTONE";

    public BoomerangModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM_PRIMARY;
    }

    public BoomerangModel(String boomerangType) {
        mBaseModelType = BaseModelType.PLAYER_ITEM_PRIMARY;
        mItemType = boomerangType;
    }

}