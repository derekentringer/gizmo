package com.derekentringer.gizmo.model.object;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;

public class BombModel extends BasePlayerItemModel {

    public static final String BOMB = "BOMB";

    public BombModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM_SECONDARY;
        mItemType = BOMB;
    }

}