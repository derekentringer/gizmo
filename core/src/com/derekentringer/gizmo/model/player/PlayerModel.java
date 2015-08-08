package com.derekentringer.gizmo.model.player;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.object.KeyModel;

import java.util.ArrayList;

public class PlayerModel extends BaseModel {

    public static final String PLAYER = "player";
    public static final String PLAYER_DESTINATIONS = "playerdestinations";

    public static final int HEART_HEALTH_AMOUNT = 10;

    public static final int DEFAULT_HEARTS = 2;
    public static final int DEFAULT_HEALTH = 20;
    public static final int DEFAULT_LIVES = 2;
    public static final int DEFAULT_LEVEL = 0;

    private ArrayList<KeyModel> keyList = new ArrayList<KeyModel>();

    private int currentLevel;
    private int playerLives;
    private int playerHearts;
    private int playerHealth;

    public PlayerModel() {
        super();
        baseModelType = BaseModelType.PLAYER;
    }

    public void setCurrentLevel(int level) {
        currentLevel = level;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setPlayerLives(int lives) {
        playerLives = lives;
    }

    public int getPlayerLives() {
        return playerLives;
    }

    public void setPlayerHearts(int hearts) {
        playerHearts = hearts;
    }

    public int getPlayerHearts() {
        return playerHearts;
    }

    public void setPlayerHealth(int health) {
        playerHealth = health;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerKey(KeyModel keyData) {
        keyList.add(keyData);
    }

    public ArrayList<KeyModel> getPlayerKeys() {
        return keyList;
    }

    public void removePlayerKey(String keyType) {
        for (int i=0; i < keyList.size(); i++) {
            if (keyList.get(i).getKeyType().equalsIgnoreCase(keyType)) {
                keyList.remove(i);
            }
        }
    }

}