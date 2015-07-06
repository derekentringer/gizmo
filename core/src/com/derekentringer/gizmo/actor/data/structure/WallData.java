package com.derekentringer.gizmo.actor.data.structure;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class WallData extends ObjectData {

    public static final String TILE_WALL = "wall";

    public WallData() {
        super();
        objectDataType = ObjectDataType.GROUND;
    }

}