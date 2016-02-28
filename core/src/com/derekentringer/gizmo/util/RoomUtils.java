package com.derekentringer.gizmo.util;

import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.model.room.RoomModel;

import java.util.ArrayList;

public class RoomUtils {

    public static final ArrayList<RoomModel> rooms = new ArrayList<RoomModel>();
    private static final int numRooms = LocalDataManager.getNumberOfLevels();

    public static void buildRoomList() {
        for (int i = 0; i <= numRooms; i++) {
            rooms.add(new RoomModel(
                    i,
                    LocalDataManager.DIR_ROOMS + i + "/room.tmx",
                    LocalDataManager.DIR_ROOMS + i + "/mid_background.tmx",
                    LocalDataManager.DIR_ROOMS + i + "/background.tmx"
            ));
        }
    }

}