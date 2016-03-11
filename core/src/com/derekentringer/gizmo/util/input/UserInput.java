package com.derekentringer.gizmo.util.input;

import com.badlogic.gdx.controllers.Controller;

public class UserInput {

    private static Controller mController;

    public static boolean[] currentKey;
    public static boolean[] previousKey;

    public static final int NUM_KEYS = 24;

    public static final int JUMP_BUTTON = 0;
    public static final int LEFT_BUTTON = 1;
    public static final int RIGHT_BUTTON = 2;
    public static final int ENTER_DOOR = 3;
    public static final int ATTACK_BUTTON = 4;
    public static final int RUN_BUTTON = 5;
    public static final int DIG_BUTTON = 6;

    public static final int UP = 7;
    public static final int DOWN = 8;

    public static final int START_BUTTON = 9;

    public static final int BACK_BUTTON = 10;

    public static final int SWITCH_PRIMARY_BUTTON_BACKWARD = 11;
    public static final int SWITCH_PRIMARY_BUTTON_FORWARD = 12;

    public static final int SWITCH_SECONDARY_BUTTON_BACKWARD = 13;
    public static final int SWITCH_SECONDARY_BUTTON_FORWARD = 14;

    public static final int SWITCH_WEAPON_BUTTON_1 = 15;
    public static final int SWITCH_WEAPON_BUTTON_2 = 16;
    public static final int SWITCH_WEAPON_BUTTON_3 = 17;
    public static final int SWITCH_WEAPON_BUTTON_4 = 18;
    public static final int SWITCH_WEAPON_BUTTON_5 = 19;
    public static final int SWITCH_WEAPON_BUTTON_6 = 20;
    public static final int SWITCH_WEAPON_BUTTON_7 = 21;
    public static final int SWITCH_WEAPON_BUTTON_8 = 22;
    public static final int SWITCH_WEAPON_BUTTON_9 = 23;

    static {
        currentKey = new boolean[NUM_KEYS];
        previousKey = new boolean[NUM_KEYS];
    }

    public static void setController(Controller controller) {
        mController = controller;
    }

    public static Controller getController() {
        return mController;
    }

    public static void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            previousKey[i] = currentKey[i];
        }
    }

    public static void setKey(int i, boolean b) {
        currentKey[i] = b;
    }

    public static boolean isDown(int i) {
        return currentKey[i];
    }

    public static boolean isPressed(int i) {
        return currentKey[i] && previousKey[i];
    }

    public static void resetKey(int i, boolean b) {
        currentKey[i] = b;
    }

}