package com.derekentringer.gizmo.util.constant;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final boolean DEBUGGING = true;

    //rendering
    public static final float TIME_STEP = 1 / 300f;
    public static float ACCUMULATOR = 0f;

    //game
    public static final int GAME_WIDTH = 640;
    public static final int GAME_HEIGHT = 360;
    public static final int ASPECT_RATIO = GAME_WIDTH / GAME_HEIGHT;
    public static final float PPM = 100;

    //world
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -9.81f);

    //levels
    public static final String LEVEL_ONE = "res/maps/level_one.tmx";
    public static final String LEVEL_TWO = "res/maps/level_two.tmx";
    public static final String LEVEL_THREE = "res/maps/level_three_simple.tmx";

}