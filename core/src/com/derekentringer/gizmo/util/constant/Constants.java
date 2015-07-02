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
    public static final GameLevel<Integer, String> LEVEL_ONE = new GameLevel<Integer, String>(0, "res/maps/level_one.tmx");
    public static final GameLevel<Integer, String> LEVEL_TWO = new GameLevel<Integer, String>(1, "res/maps/level_two.tmx");
    public static final GameLevel<Integer, String> LEVEL_THREE = new GameLevel<Integer, String>(2, "res/maps/level_three.tmx");
    public static final GameLevel<Integer, String> LEVEL_FOUR = new GameLevel<Integer, String>(3, "res/maps/level_four.tmx");
    public static final GameLevel<Integer, String> LEVEL_FIVE = new GameLevel<Integer, String>(4, "res/maps/level_five.tmx");
    public static final GameLevel<Integer, String> LEVEL_SIX = new GameLevel<Integer, String>(5, "res/maps/level_six.tmx");
    public static final GameLevel<Integer, String> LEVEL_SEVEN = new GameLevel<Integer, String>(6, "res/maps/level_seven.tmx");

}

