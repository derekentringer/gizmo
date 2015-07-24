package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.derekentringer.gizmo.actor.data.player.PlayerData;
import com.derekentringer.gizmo.actor.player.PlayerActor;
import com.derekentringer.gizmo.util.constant.Constants;

public class DataManager {

    private static final String GAME_SAVE_FILE = "game.sav";

    public static void writeFile(String fileName, String string) {
        FileHandle file = Gdx.files.local(fileName);
        if(!Constants.DEBUGGING) {
            file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(string), false);
        }
        else {
            file.writeString(string, false);
        }
    }

    public static String readFile(String fileName) {
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String string = file.readString();
            if (!string.isEmpty()) {
                if(!Constants.DEBUGGING) {
                    return com.badlogic.gdx.utils.Base64Coder.decodeString(string);
                }
                else {
                    return string;
                }
            }
        }
        return "";
    }

    public static void savePlayerActorData(PlayerActor playerActor) {
        Json json = new Json();
        writeFile(GAME_SAVE_FILE, json.toJson(playerActor.getUserData()));
    }

    public static PlayerData loadPlayerActorData() {
        String save = readFile(GAME_SAVE_FILE);
        if (!save.isEmpty()) {
            Json json = new Json();
            return json.fromJson(PlayerData.class, save);
        }
        return null;
    }

    public static void resetPlayerData() {
        FileHandle file = Gdx.files.local(GAME_SAVE_FILE);
        if (file != null && file.exists()) {
            file.delete();
        }
    }

}