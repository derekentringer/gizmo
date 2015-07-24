package com.derekentringer.gizmo.actor.data.player;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;
import com.derekentringer.gizmo.actor.data.object.KeyData;

import java.util.ArrayList;

public class PlayerData extends ObjectData {

    public static final String PLAYER = "player";

    private int playerHealth = 20;
    private int playerLives = 5;
    private int numKeys = 0;
    private ArrayList<KeyData> keyList = new ArrayList<KeyData>();

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

    public void setKeys(KeyData keyData) {
        keyList.add(keyData);
    }

    public ArrayList<KeyData> getKeys() {
        return keyList;
    }

    public void setNumKeys() {
        numKeys++;
    }

    public int getNumKeys() {
        return numKeys;
    }

}