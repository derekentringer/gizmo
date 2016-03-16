package com.derekentringer.gizmo.component.actor.object;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;

public class PotionLifeModel extends BasePlayerItemModel {

    public static final String POTION_LIFE = "POTION_LIFE";

    public PotionLifeModel() {
        mBaseModelType = BaseModelType.PLAYER_ITEM_SECONDARY;
        //could add multiple potions with
        //a PotionModel as the base model
        mItemType = POTION_LIFE;
    }

}