package com.derekentringer.gizmo.model.player;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;
import com.derekentringer.gizmo.model.item.BombModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.PotionLifeModel;

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

    private ArrayList<BasePlayerItemModel> mPrimaryItemList = new ArrayList<BasePlayerItemModel>();
    private ArrayList<ArrayList> mSecondaryItemList = new ArrayList<ArrayList>();

    private ArrayList<BasePlayerItemModel> mBasePlayerItems = new ArrayList<BasePlayerItemModel>();
    private ArrayList<BasePlayerItemModel> mBombArrayList = new ArrayList<BasePlayerItemModel>();
    private ArrayList<BasePlayerItemModel> mPotionHealthArrayList = new ArrayList<BasePlayerItemModel>();

    private int mCurrentRoom;
    private int mLives;
    private int mHearts;
    private int mHealth;
    private int mCrystalBlueAmount;
    //todo
    private int mDiggingPower = 1;

    private BasePlayerItemModel mCurrentlySelectedItemPrimary;
    private BasePlayerItemModel mCurrentlySelectedItemSecondary;

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

    public ArrayList<BasePlayerItemModel> getPrimaryItems() {
        return mPrimaryItemList;
    }

    public void addPrimaryItem(BasePlayerItemModel playerItem) {
        mPrimaryItemList.add(playerItem);
    }

    public void removePrimaryItem(BasePlayerItemModel playerItem) {
        for (int i=0; i < mPrimaryItemList.size(); i++) {
            if (mPrimaryItemList.get(i).getItemType().equalsIgnoreCase(playerItem.getItemType())) {
                mPrimaryItemList.remove(i);
            }
        }
    }

    public ArrayList<BasePlayerItemModel> getBasePlayerItems() {
        if (getSecondaryItems().size() > 0) {
            for (int i = 0; i < getSecondaryItems().size(); i++) {
                ArrayList containerArray = getSecondaryItems().get(i);
                for (int ii = 0; ii < containerArray.size(); ii++) {
                    if (containerArray.get(ii).getClass().getSimpleName().equalsIgnoreCase(BombModel.class.getSimpleName())) {
                        mBasePlayerItems.add((BasePlayerItemModel) containerArray.get(ii));
                    }
                    else if (containerArray.get(ii).getClass().getSimpleName().equalsIgnoreCase(PotionLifeModel.class.getSimpleName())) {
                        mBasePlayerItems.add((BasePlayerItemModel) containerArray.get(ii));
                    }
                }
            }
        }
        return mBasePlayerItems;
    }

    public ArrayList<ArrayList> getSecondaryItems() {
        return mSecondaryItemList;
    }

    public void addSecondaryItem(BasePlayerItemModel item) {
        if (item.getItemType().equalsIgnoreCase(BombModel.BOMB)) {
            addBombItem(item);
        }
        else if (item.getItemType().equalsIgnoreCase(PotionLifeModel.POTION_LIFE)) {
            addPotionLifeItem(item);
        }
        updateSecondaryItemList();
    }

    public void removeSecondaryItem(BasePlayerItemModel item) {
        if (item.getItemType().equalsIgnoreCase(BombModel.BOMB)) {
            removeBombItem(item);
        }
        else if (item.getItemType().equalsIgnoreCase(PotionLifeModel.POTION_LIFE)) {
            removePotionLifeItem(item);
        }
        updateSecondaryItemList();
    }

    //todo this is adding more bombs and potions
    private void updateSecondaryItemList() {
        if (getBombArrayList().size() > 0) {
            mSecondaryItemList.add(mBombArrayList);
        }
        else {
            mSecondaryItemList.remove(mBombArrayList);
        }
        if (getPotionLifeArrayList().size() > 0) {
            mSecondaryItemList.add(mPotionHealthArrayList);
        }
        else {
            mSecondaryItemList.remove(mPotionHealthArrayList);
        }
    }

    public int getCrystalBlueAmount() {
        return mCrystalBlueAmount;
    }

    public void setCrystalBlueAmount(int crystalBlueAmount) {
        mCrystalBlueAmount = crystalBlueAmount;
    }

    public BasePlayerItemModel getCurrentlySelectedItemPrimary() {
        return mCurrentlySelectedItemPrimary;
    }

    public void setCurrentlySelectedItemPrimary(BasePlayerItemModel currentlySelectedItemPrimary) {
        mCurrentlySelectedItemPrimary = currentlySelectedItemPrimary;
    }

    public BasePlayerItemModel getCurrentlySelectedItemSecondary() {
        return mCurrentlySelectedItemSecondary;
    }

    public void setCurrentlySelectedItemSecondary(BasePlayerItemModel currentlySelectedItemSecondary) {
        mCurrentlySelectedItemSecondary = currentlySelectedItemSecondary;
    }

    public void addBombItem(BasePlayerItemModel bomb) {
        mBombArrayList.add(bomb);
    }

    public void removeBombItem(BasePlayerItemModel bomb) {
        mBombArrayList.remove(bomb);
    }

    public ArrayList<BasePlayerItemModel> getBombArrayList() {
        return mBombArrayList;
    }

    public void addPotionLifeItem(BasePlayerItemModel potionLife) {
        mPotionHealthArrayList.add(potionLife);
    }

    public void removePotionLifeItem(BasePlayerItemModel potionLife) {
        mPotionHealthArrayList.remove(potionLife);
    }

    public ArrayList<BasePlayerItemModel> getPotionLifeArrayList() {
        return mPotionHealthArrayList;
    }
    
}