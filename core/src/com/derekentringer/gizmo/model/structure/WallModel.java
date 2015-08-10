package com.derekentringer.gizmo.model.structure;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class WallModel extends BaseModel {

    public static final String TILE_WALL = "wall";

    public WallModel() {
        super();
        mBaseModelType = BaseModelType.GROUND;
    }

}