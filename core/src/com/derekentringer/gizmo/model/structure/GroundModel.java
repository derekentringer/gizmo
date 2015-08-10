package com.derekentringer.gizmo.model.structure;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class GroundModel extends BaseModel {

    public static final String TILE_GROUND = "ground";

    public GroundModel() {
        super();
        mBaseModelType = BaseModelType.GROUND;
    }

}