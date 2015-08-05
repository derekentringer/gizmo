package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.settings.Constants;

public class LocalDataManager {

    private static final String GAME_SAVE_FILE = "game.sav";

    public static void resetAllPlayerData() {
        FileHandle file = Gdx.files.local(GAME_SAVE_FILE);
        if (file != null && file.exists()) {
            //file.delete();
            file.writeString("", false);
        }
    }

    public static void writeFile(String fileName, String string) {
        FileHandle file = Gdx.files.local(fileName);
        if (!Constants.DEBUGGING) {
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
                if (!Constants.DEBUGGING) {
                    return com.badlogic.gdx.utils.Base64Coder.decodeString(string);
                }
                else {
                    return string;
                }
            }
        }
        return "";
    }

    public static void savePlayerActorData(PlayerModel playerData) {
        Json json = new Json();
        String playerDataString = json.toJson(playerData, PlayerModel.class);
        System.out.println("*** SAVING PLAYER DATA *** " + playerDataString);
        writeFile(GAME_SAVE_FILE, playerDataString);
    }

    public static PlayerModel loadPlayerActorData() {
        String saveFile = readFile(GAME_SAVE_FILE);
        if (!saveFile.isEmpty()) {
            Json json = new Json();
            PlayerModel playerData = json.fromJson(PlayerModel.class, saveFile);
            System.out.println("*** LOADED PLAYER DATA *** ");
            System.out.println("getPlayerHearts: " + playerData.getPlayerHearts());
            System.out.println("getPlayerLives: " + playerData.getPlayerLives());
            System.out.println("getPlayerHealth: " + playerData.getPlayerHealth());
            System.out.println("getPlayerKeys: " + playerData.getPlayerKeys().size());
            System.out.println("getCurrentLevel: " + playerData.getCurrentLevel());
            return playerData;
        }
        return null;
    }

}