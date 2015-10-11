package com.derekentringer.gizmo.model.level;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.item.BaseItemModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.LifeModel;
import com.derekentringer.gizmo.model.structure.DoorModel;
import com.derekentringer.gizmo.model.structure.destroyable.BaseDestroyableModel;

import java.util.ArrayList;

public class LevelModel extends BaseModel {

    private ArrayList<BaseItemModel> mPickedUpItems = new ArrayList<BaseItemModel>();
    private ArrayList<KeyModel> mPickedUpKeys = new ArrayList<KeyModel>();
    private ArrayList<HeartModel> mPickedUpHearts = new ArrayList<HeartModel>();
    private ArrayList<LifeModel> mPickedUpLives = new ArrayList<LifeModel>();
    private ArrayList<DoorModel> mOpenedDoors = new ArrayList<DoorModel>();
    private ArrayList<BaseDestroyableModel> mDestroyedBlockList = new ArrayList<BaseDestroyableModel>();

    private int mLevelInt;
    private String mLevelMap;
    private String mLevelMidMap;
    private String mLevelBackMap;

    private KeyModel mLastKeyAdded;
    private HeartModel mLastHeartAdded;
    private BaseDestroyableModel mLastBlockAdded;

    public LevelModel() {
    }

    public LevelModel(int levelInt, String levelMap, String levelMidMap, String levelBackMap) {
        mBaseModelType = BaseModelType.LEVEL;
        mLevelInt = levelInt;
        mLevelMap = levelMap;
        mLevelMidMap = levelMidMap;
        mLevelBackMap = levelBackMap;
    }

    public int getLevelInt() {
        return mLevelInt;
    }

    public void setLevelInt(int levelInt) {
        mLevelInt = levelInt;
    }

    public String getLevelMap() {
        return mLevelMap;
    }

    public void setLevelMap(String levelMap) {
        mLevelMap = levelMap;
    }

    public String getLevelMidMap() {
        return mLevelMidMap;
    }

    public void setLevelMidMap(String levelMidMap) {
        mLevelMidMap = levelMidMap;
    }

    public String getLevelBackMap() {
        return mLevelBackMap;
    }

    public void setLevelBackMap(String levelBackMap) {
        mLevelBackMap = levelBackMap;
    }

    public void addPickedUpItem(BaseItemModel itemModel) {
        mPickedUpItems.add(itemModel);
    }

    public ArrayList<BaseItemModel> getPickedUpItems() {
        return mPickedUpItems;
    }

    public void addPickedUpKey(KeyModel keyModel) {
        if (mLastKeyAdded == null || !mLastKeyAdded.equals(keyModel)) {
            mPickedUpKeys.add(keyModel);
            mLastKeyAdded = keyModel;
        }
    }

    public ArrayList<KeyModel> getPickedUpKeys() {
        return mPickedUpKeys;
    }

    public void addPickedUpHeart(HeartModel heartModel) {
        if (mLastHeartAdded == null || !mLastHeartAdded.equals(heartModel)) {
            mPickedUpHearts.add(heartModel);
            mLastHeartAdded = heartModel;
        }
    }

    public ArrayList<HeartModel> getPickedUpHearts() {
        return mPickedUpHearts;
    }

    public void addPickedUpLife(LifeModel lifeModel) {
        mPickedUpLives.add(lifeModel);
    }

    public ArrayList<LifeModel> getPickedUpLives() {
        return mPickedUpLives;
    }

    public void addOpenedDoor(DoorModel doorModel) {
        mOpenedDoors.add(doorModel);
    }

    public ArrayList<DoorModel> getOpenedDoors() {
        return mOpenedDoors;
    }

    public ArrayList<BaseDestroyableModel> getDestroyedBlockList() {
        return mDestroyedBlockList;
    }

    public void addDestroyedBlock(BaseDestroyableModel block) {
        if (block != null && (mLastBlockAdded == null || !mLastBlockAdded.equals(block))) {
            mDestroyedBlockList.add(block);
            mLastBlockAdded = block;
        }
    }

}