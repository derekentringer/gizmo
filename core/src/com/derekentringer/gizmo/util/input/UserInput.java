package com.derekentringer.gizmo.util.input;

public class UserInput {

    private static final String TAG = UserInput.class.getSimpleName();

    public static boolean[] currentKey;
    public static boolean[] previousKey;

    public static final int NUM_KEYS = 6;

    public static final int JUMP_BUTTON = 0;
    public static final int LEFT_BUTTON = 1;
    public static final int RIGHT_BUTTON = 2;
    public static final int ENTER_DOOR = 3;
    public static final int ATTACK = 4;
    public static final int RUN = 5;

    static {
        currentKey = new boolean[NUM_KEYS];
        previousKey = new boolean[NUM_KEYS];
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
        return currentKey[i] && !previousKey[i];
    }

}