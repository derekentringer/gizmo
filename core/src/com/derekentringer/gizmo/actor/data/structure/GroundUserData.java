package com.derekentringer.gizmo.actor.data.structure;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class GroundUserData extends ObjectData {

    public static final String TILE_GROUND = "ground";

    public GroundUserData() {
        super();
        objectDataType = ObjectDataType.GROUND;
    }

}