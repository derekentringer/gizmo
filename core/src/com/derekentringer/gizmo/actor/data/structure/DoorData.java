package com.derekentringer.gizmo.actor.data.structure;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class DoorData extends ObjectData {

    private String sDoorType;
    private int sLevelNumber;

    public DoorData(String doorType, int levelNumber) {
        super();
        objectDataType = ObjectDataType.DOOR;
        sDoorType = doorType;
        sLevelNumber = levelNumber;
    }

    public String getDoorType() {
        return sDoorType;
    }

    public int getLevelNumber() {
        return sLevelNumber;
    }

}