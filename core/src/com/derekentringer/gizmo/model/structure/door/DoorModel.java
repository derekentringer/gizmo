package com.derekentringer.gizmo.model.structure.door;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class DoorModel extends BaseModel {

    private String mDoorType;
    private int mLevelNumber;
    private String mDestinationDoor;
    private boolean mIsLocked;

    public DoorModel() {
    }

    public DoorModel(String doorType) {
        mBaseModelType = BaseModelType.DOOR;
        mDoorType = doorType;
    }

    public DoorModel(String doorType, int levelNumber, String destinationDoor, boolean isLocked) {
        super();
        mBaseModelType = BaseModelType.DOOR;
        mDoorType = doorType;
        mLevelNumber = levelNumber;
        mDestinationDoor = destinationDoor;
        mIsLocked = isLocked;
    }

    public String getDoorType() {
        return mDoorType;
    }

    public int getLevelNumber() {
        return mLevelNumber;
    }

    public String getDestinationDoor() {
        if(mDestinationDoor != null && !mDestinationDoor.isEmpty()) {
            return mDestinationDoor;
        }
        return DoorType.DOOR_PREVIOUS;
    }

    public boolean getIsLocked() {
        return mIsLocked;
    }

    public void setIsLocked(boolean isLocked) {
        mIsLocked = isLocked;
    }

}