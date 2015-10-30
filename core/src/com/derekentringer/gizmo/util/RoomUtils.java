package com.derekentringer.gizmo.util;

import com.derekentringer.gizmo.model.room.RoomModel;

import java.util.ArrayList;

public class RoomUtils {

    public static final ArrayList<RoomModel> rooms = new ArrayList<RoomModel>();

    public static void buildRoomList() {

        RoomModel roomZero = new RoomModel(
                "intro",
                0,
                "res/maps/intro/room_zero/room_zero.tmx",
                "res/maps/intro/room_zero/mid_background.tmx",
                "res/maps/intro/room_zero/background.tmx");

        RoomModel roomOne = new RoomModel(
                "intro",
                1,
                "res/maps/intro/room_one/room_one.tmx",
                "res/maps/intro/room_one/mid_background.tmx",
                "res/maps/intro/room_one/background.tmx");

        RoomModel roomTwo = new RoomModel(
                "intro",
                2,
                "res/maps/intro/room_two/room_two.tmx",
                "res/maps/intro/room_two/mid_background.tmx",
                "res/maps/intro/room_two/background.tmx");

        RoomModel roomThree = new RoomModel(
                "intro",
                3,
                "res/maps/intro/room_three/room_three.tmx",
                "res/maps/intro/room_three/mid_background.tmx",
                "res/maps/intro/room_three/background.tmx");

        rooms.add(roomZero);
        rooms.add(roomOne);
        rooms.add(roomTwo);
        rooms.add(roomThree);
    }

}