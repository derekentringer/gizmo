package com.derekentringer.gizmo.model.object;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.item.BaseItemModel;

public class BoomerangModel extends BaseItemModel {

    public static final String BOOMERANG = "BOOMERANG";

    public static final String BOOMERANG_WOOD = "BOOMERANG_WOOD";
    public static final String BOOMERANG_AMETHYST = "BOOMERANG_AMETHYST";
    public static final String BOOMERANG_EMERALD = "BOOMERANG_EMERALD";

    private String mBoomerangType;
    private String mItemType;

    public BoomerangModel() {
        super();
        mBaseModelType = BaseModelType.PLAYER_ITEM;
    }

    public BoomerangModel(String boomerangType) {
        super();
        mBaseModelType = BaseModelType.PLAYER_ITEM;
        mBoomerangType = boomerangType;
        mItemType = boomerangType;
    }

    @Override
    public String getItemType() {
        return mItemType;
    }

    @Override
    public void setItemType(String itemType) {
        mItemType = itemType;
    }

}