package com.derekentringer.gizmo.settings;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.derekentringer.gizmo.model.level.LevelModel;

import java.util.ArrayList;

public class Constants {

    public static final boolean IS_DEBUG = true;
    public static final boolean IS_DEBUG_BOX2D = true;
    public static final int LOG_LEVEL = Logger.DEBUG;

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

    public static final ArrayList<LevelModel> gameLevels = new ArrayList<LevelModel>();

    public static void buildGameLevelList() {
        LevelModel levelOne = new LevelModel(0,
                "res/maps/level_one/level_one.tmx",
                "res/maps/level_one/mid_background.tmx",
                "res/maps/level_one/background.tmx");

        LevelModel levelTwo = new LevelModel(1,
                "res/maps/level_two/level_two.tmx",
                "res/maps/level_two/mid_background.tmx",
                "res/maps/level_two/background.tmx");

        LevelModel levelThree = new LevelModel(2,
                "res/maps/level_three/level_three.tmx",
                "res/maps/level_three/mid_background.tmx",
                "res/maps/level_three/background.tmx");

        gameLevels.add(levelOne);
        gameLevels.add(levelTwo);
        gameLevels.add(levelThree);
    }

}