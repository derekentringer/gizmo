package com.derekentringer.gizmo.model.structure;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class DoorModel extends BaseModel {

    private String sDoorType;
    private int sLevelNumber;
    private String sDestinationDoor;

    public DoorModel() {
        super();
    }

    public DoorModel(String doorType, int levelNumber, String destinationDoor) {
        super();
        baseModelType = BaseModelType.DOOR;
        sDoorType = doorType;
        sLevelNumber = levelNumber;
        sDestinationDoor = destinationDoor;
    }

    public DoorModel(String doorType) {
        super();
        baseModelType = BaseModelType.DOOR;
        sDoorType = doorType;
    }

    public String getDoorType() {
        return sDoorType;
    }

    public int getLevelNumber() {
        return sLevelNumber;
    }

    public String getDestinationDoor() {
        if(sDestinationDoor != null && !sDestinationDoor.isEmpty()) {
            return sDestinationDoor;
        }
        return DoorType.PREVIOUS;
    }

}