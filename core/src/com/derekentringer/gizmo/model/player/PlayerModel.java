package com.derekentringer.gizmo.model.player;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.item.BaseItemModel;
import com.derekentringer.gizmo.model.object.KeyModel;

import java.util.ArrayList;

public class PlayerModel extends BaseModel {

    public static final String PLAYER = "PLAYER";
    public static final String PLAYER_DESTINATIONS = "PLAYER_DESTINATIONS";

    public static final int HEART_HEALTH_AMOUNT = 10;

    public static final int DEFAULT_LEVEL = 0;
    public static final int DEFAULT_HEARTS = 2;
    public static final int DEFAULT_HEALTH = 20;
    public static final int DEFAULT_LIVES = 2;
    public static final int DEFAULT_MAX_LIVES = 5;
    public static final int DEFAULT_MAX_HEARTS = 5;
    public static final int DEFAULT_DIGGING_POWER = 1;

    private ArrayList<KeyModel> mKeyList = new ArrayList<KeyModel>();
    private ArrayList<BaseItemModel> mItemList = new ArrayList<BaseItemModel>();

    private int mCurrentLevel;
    private int mLives;
    private int mHearts;
    private int mHealth;
    //TODO
    private int mDiggingPower = 1;

    private int mCrystalBlueAmount;

    public PlayerModel() {
        mBaseModelType = BaseModelType.PLAYER;
    }

    public void setCurrentLevel(int level) {
        mCurrentLevel = level;
    }

    public int getCurrentLevel() {
        return mCurrentLevel;
    }

    public void setLives(int lives) {
        mLives = lives;
    }

    public int getLives() {
        return mLives;
    }

    public void setHearts(int hearts) {
        mHearts = hearts;
    }

    public int getHearts() {
        return mHearts;
    }

    public void setHealth(int health) {
        mHealth = health;
        if (mHealth > (getHearts() * HEART_HEALTH_AMOUNT)) {
            mHealth = getHearts() * HEART_HEALTH_AMOUNT;
        }
    }

    public int getHealth() {
        return mHealth;
    }

    public int getDiggingPower() {
        return mDiggingPower;
    }

    public void setDiggingPower(int diggingPower) {
        mDiggingPower = diggingPower;
    }

    public ArrayList<KeyModel> getKeys() {
        return mKeyList;
    }

    public void addKey(KeyModel keyData) {
        mKeyList.add(keyData);
    }

    public void removeKey(String keyType) {
        for (int i=0; i < mKeyList.size(); i++) {
            if (mKeyList.get(i).getKeyType().equalsIgnoreCase(keyType)) {
                mKeyList.remove(i);
            }
        }
    }

    public ArrayList<BaseItemModel> getItems() {
        return mItemList;
    }

    public void addItem(BaseItemModel playerItem) {
        mItemList.add(playerItem);
    }

    public void removeItem(BaseItemModel playerItem) {
        mItemList.remove(playerItem);
    }

    public int getCrystalBlueAmount() {
        return mCrystalBlueAmount;
    }

    public void incrementCrystalBlueAmount() {
        mCrystalBlueAmount = mCrystalBlueAmount + 1;
    }

}