package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.derekentringer.gizmo.model.level.LevelModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.log.GLog;

public class LocalDataManager {

    private static final String TAG = LocalDataManager.class.getSimpleName();

    private static final String GAME_SAVE_FILE = "game.sav";
    private static final String LEVEL_SAVE_SUFFIX = "level.sav";

    public static void resetAllPlayerData() {
        FileHandle file = Gdx.files.local(GAME_SAVE_FILE);
        if (file != null && file.exists()) {
            //file.delete();
            file.writeString("", false);
        }
    }

    public static void savePlayerActorData(PlayerModel playerModel) {
        Json json = new Json();
        String playerDataString = json.toJson(playerModel, PlayerModel.class);
        GLog.d(TAG, "*** SAVING PLAYER DATA *** " + playerDataString);
        writeFile(GAME_SAVE_FILE, playerDataString);
    }

    public static PlayerModel loadPlayerActorData() {
        String saveFile = readFile(GAME_SAVE_FILE);
        if (!saveFile.isEmpty()) {
            Json json = new Json();
            PlayerModel playerData = json.fromJson(PlayerModel.class, saveFile);
            GLog.d(TAG, "*** LOADED PLAYER DATA *** ");
            GLog.d(TAG, "getPlayerHearts: " + playerData.getPlayerHearts());
            GLog.d(TAG, "getPlayerLives: " + playerData.getPlayerLives());
            GLog.d(TAG, "getPlayerHealth: " + playerData.getPlayerHealth());
            GLog.d(TAG, "getPlayerKeys: " + playerData.getPlayerKeys().size());
            GLog.d(TAG, "getCurrentLevel: " + playerData.getCurrentLevel());
            return playerData;
        }
        return null;
    }

    public static LevelModel loadLevelData(LevelModel levelModel) {
        String fileName = levelModel.getLevelInt() + "_" + LEVEL_SAVE_SUFFIX;
        GLog.d(TAG, "*** LOADING LEVEL DATA *** " + fileName);
        String savedLevelFile = readFile(fileName);
        if (!savedLevelFile.isEmpty()) {
            Json json = new Json();
            LevelModel loadedLevelModel = json.fromJson(LevelModel.class, savedLevelFile);
            GLog.d(TAG, "*** LOADED LEVEL DATA *** ");
            return loadedLevelModel;
        }
        return null;
    }

    public static void saveLevelData(LevelModel levelModel) {
        String fileName = levelModel.getLevelInt() + "_" + LEVEL_SAVE_SUFFIX;
        Json json = new Json();
        String levelModelString = json.toJson(levelModel, LevelModel.class);
        GLog.d(TAG, "*** SAVING LEVEL DATA *** " + levelModelString);
        writeFile(fileName, levelModelString);
    }

    public static void writeFile(String fileName, String string) {
        FileHandle file = Gdx.files.local(fileName);
        if (!Constants.IS_DEBUG) {
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
                if (!Constants.IS_DEBUG) {
                    return com.badlogic.gdx.utils.Base64Coder.decodeString(string);
                }
                else {
                    return string;
                }
            }
        }
        return "";
    }

}