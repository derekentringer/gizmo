package com.derekentringer.gizmo.actor.data.structure;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class DoorUserData extends ObjectData {

    private String sDoorType;

    public DoorUserData(String doorType) {
        super();
        objectDataType = ObjectDataType.DOOR;
        sDoorType = doorType;
    }

    public String getDoorType() {
        return sDoorType;
    }

}