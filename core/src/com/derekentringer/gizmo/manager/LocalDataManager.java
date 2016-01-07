package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.derekentringer.gizmo.model.room.RoomModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.log.GLog;

public class LocalDataManager {

    private static final String TAG = LocalDataManager.class.getSimpleName();

    private static final String GAME_SAVE_DIR = "sav/";
    private static final String GAME_SAVE_FILE = "game.sav";
    private static final String ROOM_SAVE_SUFFIX = "_room.sav";

    public static void resetAllPlayerData() {
        FileHandle[] files = Gdx.files.local(GAME_SAVE_DIR).list();
        for(FileHandle file: files) {
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

    public static void writeFile(String fileName, String string) {
        FileHandle file = Gdx.files.local(GAME_SAVE_DIR + fileName);
        if (!Constants.IS_DEBUG) {
            file.writeString(Base64Coder.encodeString(string), false);
        }
        else {
            file.writeString(string, false);
        }
    }

    public static String readFile(String fileName) {
        FileHandle file = Gdx.files.local(GAME_SAVE_DIR + fileName);
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