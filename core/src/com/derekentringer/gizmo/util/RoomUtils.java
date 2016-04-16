package com.derekentringer.gizmo.util;

import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.model.room.RoomModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class RoomUtils {

    private static final String TAG = RoomUtils.class.getSimpleName();

    public static final ArrayList<RoomModel> rooms = new ArrayList<RoomModel>();
    private static final int numRooms = Constants.TOTAL_NUM_ROOMS;

    public static void buildRoomList() {
        for (int i = 0; i <= numRooms; i++) {
            rooms.add(new RoomModel(
                    i,
                    LocalDataManager.DIR_ROOMS + i + "/room.tmx",
                    LocalDataManager.DIR_ROOMS + i + "/mid_background.tmx",
                    LocalDataManager.DIR_ROOMS + i + "/background.tmx"
            ));

            if (Constants.IS_DEBUG) {
                GLog.d(TAG, "rooms info: " + rooms.get(i).getRoomInt());
                GLog.d(TAG, "rooms info: " + rooms.get(i).getRoomBackMap());
                GLog.d(TAG, "rooms info: " + rooms.get(i).getRoomMidMap());
                GLog.d(TAG, "rooms info: " + rooms.get(i).getRoomMap());
            }
        }
    }

}