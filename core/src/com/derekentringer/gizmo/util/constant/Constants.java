package com.derekentringer.gizmo.util.constant;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

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
    public static final GameLevels<Integer, String, String, String, Integer, Integer> LEVEL_ONE = new GameLevels<Integer, String, String, String, Integer, Integer>(0, "res/maps/level_one/level_one.tmx", "res/maps/level_one/mid_background.tmx", "res/maps/level_one/background.tmx", 143, 130);
    public static final GameLevels<Integer, String, String, String, Integer, Integer> LEVEL_TWO = new GameLevels<Integer, String, String, String, Integer, Integer>(1, "res/maps/level_two/level_two.tmx", "res/maps/level_one/mid_background.tmx", "res/maps/level_one/background.tmx", 240, 90);
    public static final GameLevels<Integer, String, String, String, Integer, Integer> LEVEL_THREE = new GameLevels<Integer, String, String, String, Integer, Integer>(2, "res/maps/level_three/level_three.tmx", "res/maps/level_one/mid_background.tmx", "res/maps/level_one/background.tmx", 275, 210);

    public static final ArrayList<GameLevels> gameLevels = new ArrayList<GameLevels>();

    public Constants() {
        buildGameLevelList();
    }

    public static void buildGameLevelList() {
        gameLevels.add(LEVEL_ONE);
        gameLevels.add(LEVEL_TWO);
        gameLevels.add(LEVEL_THREE);
    }

}

