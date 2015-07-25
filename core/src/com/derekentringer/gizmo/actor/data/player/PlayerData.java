package com.derekentringer.gizmo.actor.data.player;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;
import com.derekentringer.gizmo.actor.data.object.KeyData;

import java.util.ArrayList;

public class PlayerData extends ObjectData {

    public static final String PLAYER = "player";

    public static final int DEFAULT_HEALTH = 20;
    public static final int DEFAULT_LIVES = 5;

    private ArrayList<KeyData> keyList = new ArrayList<KeyData>();

    private int playerHealth;
    private int playerLives;

    public PlayerData() {
        super();
        objectDataType = ObjectDataType.PLAYER;
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

    public void setKey(KeyData keyData) {
        keyList.add(keyData);
    }

    public ArrayList<KeyData> getKeys() {
        return keyList;
    }

}