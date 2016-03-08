package com.derekentringer.gizmo.model.player;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;
import com.derekentringer.gizmo.model.object.KeyModel;

import java.util.ArrayList;

public class PlayerModel extends BaseModel {

    public static final String PLAYER = "PLAYER";
    public static final String PLAYER_DESTINATIONS = "PLAYER_DESTINATIONS";

    public static final int HEART_HEALTH_AMOUNT = 10;

    public static final int DEFAULT_ROOM = 0;
    public static final int DEFAULT_HEARTS = 2;
    public static final int DEFAULT_HEALTH = 20;
    public static final int DEFAULT_LIVES = 2;
    public static final int DEFAULT_MAX_LIVES = 5;
    public static final int DEFAULT_MAX_HEARTS = 10;
    public static final int DEFAULT_DIGGING_POWER = 1;
    public static final int DEFAULT_CRYSTAL_BLUE = 0;

    private ArrayList<KeyModel> mKeyList = new ArrayList<KeyModel>();
    private ArrayList<BasePlayerItemModel> mItemList = new ArrayList<BasePlayerItemModel>();

    private int mCurrentRoom;
    private int mLives;
    private int mHearts;
    private int mHealth;
    private int mCrystalBlueAmount;
    //TODO
    private int mDiggingPower = 1;

    private BasePlayerItemModel mCurrentlySelectedItem;

    public PlayerModel() {
        mBaseModelType = BaseModelType.PLAYER;
    }

    public void setCurrentRoom(int room) {
        mCurrentRoom = room;
    }

    public int getCurrentRoom() {
        return mCurrentRoom;
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

    public ArrayList<BasePlayerItemModel> getItems() {
        return mItemList;
    }

    public void addItem(BasePlayerItemModel playerItem) {
        mItemList.add(playerItem);
    }

    public void removeItem(BasePlayerItemModel playerItem) {
        mItemList.remove(playerItem);
    }

    public int getCrystalBlueAmount() {
        return mCrystalBlueAmount;
    }

    public void setCrystalBlueAmount(int crystalBlueAmount) {
        mCrystalBlueAmount = crystalBlueAmount;
    }

    public BasePlayerItemModel getCurrentlySelectedItem() {
        return mCurrentlySelectedItem;
    }

    public void setCurrentlySelectedItem(BasePlayerItemModel currentlySelectedItem) {
        mCurrentlySelectedItem = currentlySelectedItem;
    }

}