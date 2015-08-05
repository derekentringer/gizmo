package com.derekentringer.gizmo.model.structure;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class DoorModel extends BaseModel {

    private String sDoorType;
    private int sLevelNumber;

    public DoorModel(String doorType, int levelNumber) {
        super();
        baseModelType = BaseModelType.DOOR;
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