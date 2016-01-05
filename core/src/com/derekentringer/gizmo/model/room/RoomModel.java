package com.derekentringer.gizmo.model.room;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.enemy.BaseEnemyModel;
import com.derekentringer.gizmo.model.player_item.BasePlayerItemModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.LifeModel;
import com.derekentringer.gizmo.model.structure.destroyable.BaseDestroyableModel;
import com.derekentringer.gizmo.model.structure.door.DoorModel;

import java.util.ArrayList;

public class RoomModel extends BaseModel {

    private ArrayList<BasePlayerItemModel> mPickedUpItems = new ArrayList<BasePlayerItemModel>();
    private ArrayList<KeyModel> mPickedUpKeys = new ArrayList<KeyModel>();
    private ArrayList<HeartModel> mPickedUpHearts = new ArrayList<HeartModel>();
    private ArrayList<LifeModel> mPickedUpLives = new ArrayList<LifeModel>();
    private ArrayList<DoorModel> mOpenedDoors = new ArrayList<DoorModel>();
    private ArrayList<BaseDestroyableModel> mDestroyedBlockList = new ArrayList<BaseDestroyableModel>();
    private ArrayList<BaseEnemyModel> mDestroyedBossList = new ArrayList<BaseEnemyModel>();

    private String mRoomRegion;
    private int mRoomInt;
    private String mRoomMap;
    private String mRoomMidMap;
    private String mRoomBackMap;

    private KeyModel mLastKeyAdded;
    private HeartModel mLastHeartAdded;
    private BaseDestroyableModel mLastBlockAdded;
    private BaseEnemyModel mLastBossAdded;

    public RoomModel() {
    }

    public RoomModel(String roomRegion, int roomInt, String roomMap, String roomMidMap, String roomBackMap) {
        mBaseModelType = BaseModelType.ROOM;
        mRoomRegion = roomRegion;
        mRoomInt = roomInt;
        mRoomMap = roomMap;
        mRoomMidMap = roomMidMap;
        mRoomBackMap = roomBackMap;
    }

    public String getRoomRegion() {
        return mRoomRegion;
    }

    public void setRoomRegion(String roomRegion) {
        mRoomRegion = roomRegion;
    }

    public int getRoomInt() {
        return mRoomInt;
    }

    public void setRoomInt(int roomInt) {
        mRoomInt = roomInt;
    }

    public String getRoomMap() {
        return mRoomMap;
    }

    public void setRoomMap(String roomMap) {
        mRoomMap = roomMap;
    }

    public String getRoomMidMap() {
        return mRoomMidMap;
    }

    public void setRoomMidMap(String roomMidMap) {
        mRoomMidMap = roomMidMap;
    }

    public String getRoomBackMap() {
        return mRoomBackMap;
    }

    public void setRoomBackMap(String roomBackMap) {
        mRoomBackMap = roomBackMap;
    }

    public void addPickedUpItem(BasePlayerItemModel itemModel) {
        mPickedUpItems.add(itemModel);
    }

    public ArrayList<BasePlayerItemModel> getPickedUpItems() {
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

    public ArrayList<BaseEnemyModel> getDestroyedBossList() {
        return mDestroyedBossList;
    }

    public void addDestroyedBoss(BaseEnemyModel boss) {
        if (boss != null && (mLastBossAdded == null || !mLastBossAdded.equals(boss))) {
            mDestroyedBossList.add(boss);
            mLastBossAdded = boss;
        }
    }

}