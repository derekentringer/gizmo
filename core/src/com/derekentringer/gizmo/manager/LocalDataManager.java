package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.derekentringer.gizmo.analytics.model.AnalyticsModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.model.room.RoomModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.log.GLog;

public class LocalDataManager {

    private static final String TAG = LocalDataManager.class.getSimpleName();

    private static final String DIR_GAME_SAVE = "sav/";
    private static final String GAME_SAVE_FILE = "game.sav";
    private static final String ROOM_SAVE_SUFFIX = "_room.sav";
    private static final String ANALYTICS_SAVE_FILE = "gamea.sav";

    public static final String DIR_ROOMS = "res/maps/rooms/";

    public static int getNumberOfLevels() {
        FileHandle[] files = Gdx.files.local(DIR_ROOMS).list();
        int numDirs = 0;
        for (FileHandle file : files) {
            if (file.isDirectory()) {
                numDirs++;
            }
        }
        return numDirs;
    }

    public static void resetAllPlayerData() {
        FileHandle[] files = Gdx.files.local(DIR_GAME_SAVE).list();
        for (FileHandle file : files) {
            file.delete();
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
            GLog.d(TAG, "getItems: " + playerData.getItems());
            GLog.d(TAG, "getHearts: " + playerData.getHearts());
            GLog.d(TAG, "getLives: " + playerData.getLives());
            GLog.d(TAG, "getHealth: " + playerData.getHealth());
            GLog.d(TAG, "getKeys: " + playerData.getKeys());
            GLog.d(TAG, "getBlueCrystals: " + playerData.getCrystalBlueAmount());
            GLog.d(TAG, "getCurrentRoom: " + playerData.getCurrentRoom());

            return playerData;
        }
        return null;
    }

    public static RoomModel loadRoomData(RoomModel roomModel) {
        String fileName = roomModel.getRoomInt() + ROOM_SAVE_SUFFIX;
        GLog.d(TAG, "*** LOADING ROOM DATA *** " + fileName);
        String savedRoomFile = readFile(fileName);
        if (!savedRoomFile.isEmpty()) {
            Json json = new Json();
            RoomModel loadedRoomModel = json.fromJson(RoomModel.class, savedRoomFile);
            GLog.d(TAG, "*** LOADED ROOM DATA *** ");
            return loadedRoomModel;
        }
        return null;
    }

    public static void saveRoomData(RoomModel roomModel) {
        String fileName = roomModel.getRoomInt() + ROOM_SAVE_SUFFIX;
        Json json = new Json();
        String roomModelString = json.toJson(roomModel, RoomModel.class);
        GLog.d(TAG, "*** SAVING ROOM DATA *** " + roomModelString);
        writeFile(fileName, roomModelString);
    }

    public static void saveGameAnalyticsData(AnalyticsModel analyticsModel) {
        Json json = new Json();
        String analyticsDataString = json.toJson(analyticsModel, AnalyticsModel.class);
        GLog.d(TAG, "*** SAVING ANALYTICS DATA *** " + analyticsDataString);
        writeFile(ANALYTICS_SAVE_FILE, analyticsDataString);
    }

    public static AnalyticsModel loadGameAnalyticsData() {
        String fileName = ANALYTICS_SAVE_FILE;
        GLog.d(TAG, "*** LOADING ANALYTICS DATA *** " + fileName);
        String savedAnalyticsFile = readFile(fileName);
        if (!savedAnalyticsFile.isEmpty()) {
            Json json = new Json();
            AnalyticsModel loadedAnalytics = json.fromJson(AnalyticsModel.class, savedAnalyticsFile);
            GLog.d(TAG, "*** LOADED ANALYTICS DATA *** ");
            return loadedAnalytics;
        }
        return null;
    }

    public static void writeFile(String fileName, String string) {
        FileHandle file = Gdx.files.local(DIR_GAME_SAVE + fileName);
        if (!Constants.IS_DEBUG) {
            file.writeString(Base64Coder.encodeString(string), false);
        }
        else {
            file.writeString(string, false);
        }
    }

    public static String readFile(String fileName) {
        FileHandle file = Gdx.files.local(DIR_GAME_SAVE + fileName);
        if (file != null && file.exists()) {
            String string = file.readString();
            if (!string.isEmpty()) {
                if (!Constants.IS_DEBUG) {
                    return Base64Coder.decodeString(string);
                }
                else {
                    return string;
                }
            }
        }
        return "";
    }

}