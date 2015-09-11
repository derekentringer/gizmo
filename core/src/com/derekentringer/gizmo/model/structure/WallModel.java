package com.derekentringer.gizmo.model.structure;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class WallModel extends BaseModel {

    public static final String TILE_WALL = "wall";
    public static final String TILE_WALL_INVISIBLE = "wall_invisible";

    public WallModel() {
        super();
        mBaseModelType = BaseModelType.WALL;
    }

}