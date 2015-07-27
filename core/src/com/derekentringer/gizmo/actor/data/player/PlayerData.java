package com.derekentringer.gizmo.actor.data.player;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;
import com.derekentringer.gizmo.actor.data.object.KeyData;

import java.util.ArrayList;

public class PlayerData extends ObjectData {

    public static final String PLAYER = "player";

    public static final int HEART_HEALTH_AMOUNT = 10;
    public static final int DEFAULT_HEARTS = 2;
    public static final int DEFAULT_HEALTH = 20;
    public static final int DEFAULT_LIVES = 5;
    public static final int DEFAULT_LEVEL = 0;

    private ArrayList<KeyData> keyList = new ArrayList<KeyData>();

    private int playerHearts;
    private int playerHealth;
    private int playerLives;
    private int currentLevel;

    public PlayerData() {
        super();
        objectDataType = ObjectDataType.PLAYER;
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

    public void setPlayerLives(int lives) {
        playerLives = lives;
    }

    public int getPlayerLives() {
        return playerLives;
    }

    public void setPlayerKey(KeyData keyData) {
        keyList.add(keyData);
    }

    public ArrayList<KeyData> getPlayerKeys() {
        return keyList;
    }

    public void setCurrentLevel(int level) {
        currentLevel = level;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

}