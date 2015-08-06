package com.derekentringer.gizmo.model.level;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;

import java.util.ArrayList;

public class LevelModel extends BaseModel {

    private ArrayList<KeyModel> pickedUpKeys = new ArrayList<KeyModel>();
    private ArrayList<HeartModel> pickedUpHearts = new ArrayList<HeartModel>();

    private int sLevelInt;
    private String sLevelMap;
    private String sLevelMidMap;
    private String sLevelBackMap;

    public LevelModel() {
    }

    public LevelModel(int levelInt, String levelMap, String levelMidMap, String levelBackMap) {
        baseModelType = BaseModelType.LEVEL;
        sLevelInt = levelInt;
        sLevelMap = levelMap;
        sLevelMidMap = levelMidMap;
        sLevelBackMap = levelBackMap;
    }

    public int getLevelInt() {
        return sLevelInt;
    }

    public void setLevelInt(int levelInt) {
        sLevelInt = levelInt;
    }

    public String getLevelMap() {
        return sLevelMap;
    }

    public void setLevelMap(String levelMap) {
        sLevelMap = levelMap;
    }

    public String getsLevelMidMap() {
        return sLevelMidMap;
    }

    public void setsLevelMidMap(String levelMidMap) {
        sLevelMidMap = levelMidMap;
    }

    public String getsLevelBackMap() {
        return sLevelBackMap;
    }

    public void setsLevelBackMap(String levelBackMap) {
        sLevelBackMap = levelBackMap;
    }

    public void addKey(KeyModel keyModel) {
        pickedUpKeys.add(keyModel);
    }

    public ArrayList<KeyModel> getPickedUpKeys() {
        return pickedUpKeys;
    }

    public void addHeart(HeartModel heartModel) {
        pickedUpHearts.add(heartModel);
    }

    public ArrayList<HeartModel> getPickedUpHearts() {
        return pickedUpHearts;
    }

}