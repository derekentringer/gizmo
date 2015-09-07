package com.derekentringer.gizmo.model.object;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.item.BaseItemModel;

public class BoomerangModel extends BaseItemModel {

    public static final String BOOMERANG = "BOOMERANG";

    public static final String BOOMERANG_WOOD = "WOOD";
    public static final String BOOMERANG_AMETHYST = "BAMETHYST";
    public static final String BOOMERANG_EMERALD = "EMERALD";

    private String mBoomerangType;

    public BoomerangModel() {
        super();
        mBaseModelType = BaseModelType.BOOMERANG;
    }

    public BoomerangModel(String boomerangType) {
        super();
        mBaseModelType = BaseModelType.BOOMERANG;
        mBoomerangType = boomerangType;
    }

    @Override
    public String getItemType() {
        return mBoomerangType;
    }

}