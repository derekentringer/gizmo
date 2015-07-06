package com.derekentringer.gizmo.actor.data.structure;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class GroundData extends ObjectData {

    public static final String TILE_GROUND = "ground";

    public GroundData() {
        super();
        objectDataType = ObjectDataType.GROUND;
    }

}