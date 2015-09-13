package com.derekentringer.gizmo.model.player;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.item.BaseItemModel;

import java.util.ArrayList;

public class PlayerModel extends BaseModel {

    public static final String PLAYER = "PLAYER";
    public static final String PLAYER_DESTINATIONS = "PLAYER_DESTINATIONS";

    public static final int HEART_HEALTH_AMOUNT = 10;

    public static final int DEFAULT_HEARTS = 2;
    public static final int DEFAULT_HEALTH = 20;
    public static final int DEFAULT_LIVES = 2;
    public static final int DEFAULT_MAX_LIVES = 5;
    public static final int DEFAULT_LEVEL = 0;

    private ArrayList<KeyModel> mKeyList = new ArrayList<KeyModel>();
    private ArrayList<BaseItemModel> mPlayerItemsList = new ArrayList<BaseItemModel>();

    private int mCurrentLevel;
    private int mPlayerLives;
    private int mPlayerHearts;
    private int mPlayerHealth;

    public PlayerModel() {
        super();
        mBaseModelType = BaseModelType.PLAYER;
    }

    public void setCurrentLevel(int level) {
        mCurrentLevel = level;
    }

    public int getCurrentLevel() {
        return mCurrentLevel;
    }

    public void setPlayerLives(int lives) {
        mPlayerLives = lives;
    }

    public int getPlayerLives() {
        return mPlayerLives;
    }

    public void setPlayerHearts(int hearts) {
        mPlayerHearts = hearts;
    }

    public int getPlayerHearts() {
        return mPlayerHearts;
    }

    public void setPlayerHealth(int health) {
        mPlayerHealth = health;
        if (mPlayerHealth > (getPlayerHearts() * HEART_HEALTH_AMOUNT)) {
            mPlayerHealth = getPlayerHearts() * HEART_HEALTH_AMOUNT;
        }
    }

    public int getPlayerHealth() {
        return mPlayerHealth;
    }

    public ArrayList<KeyModel> getPlayerKeys() {
        return mKeyList;
    }

    public void addPlayerKey(KeyModel keyData) {
        mKeyList.add(keyData);
    }

    public void removePlayerKey(String keyType) {
        for (int i=0; i < mKeyList.size(); i++) {
            if (mKeyList.get(i).getKeyType().equalsIgnoreCase(keyType)) {
                mKeyList.remove(i);
            }
        }
    }

    public ArrayList<BaseItemModel> getPlayerItems() {
        return mPlayerItemsList;
    }

    public void addPlayerItem(BaseItemModel playerItem) {
        mPlayerItemsList.add(playerItem);
    }

    public void removePlayerItem(BaseItemModel playerItem) {
        mPlayerItemsList.remove(playerItem);
    }

}