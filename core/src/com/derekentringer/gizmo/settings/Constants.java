package com.derekentringer.gizmo.settings;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

import java.util.Locale;

public class Constants {

    public static final String VERSION = "0.0.1";

    // categoryBits declares what entity it is
    // maskBits declares which entities collide with
    public static final short WORLD_ENTITY = 0x1;
    public static final short PLAYER_ENTITY = 0x1 << 1;
    public static final short PLAYER_ATTACK_ENTITY = 0x1 << 2;
    public static final short ENEMY_ENTITY = 0x1 << 3;
    public static final short DROP_ENTITY = 0x1 << 4;
    public static final short IGNORE_ENTITY = 0x1 << 5;
    public static final short DESTROYABLE = 0x1 << 6;
    public static final short GROUND_ENTITY = 0x1 << 7;
    public static final short WALL_ENTITY = 0x1 << 8;

    // debugging
    public static final boolean IS_DEBUG = true;
    public static final boolean IS_DEBUG_BOX2D = false;
    public static final boolean IS_DEBUG_FPS = false;
    public static final int LOG_LEVEL = Logger.DEBUG;
    public static final Locale debugLocale = new Locale("en");

    // rendering
    public static final float TIME_STEP = 1 / 300f;
    public static float ACCUMULATOR = 0f;

    // game
    public static final int GAME_WIDTH = 640;
    public static final int GAME_HEIGHT = 360;
    public static final int ASPECT_RATIO = GAME_WIDTH / GAME_HEIGHT;
    public static final float PPM = 100;
    public static final int TOTAL_NUM_ROOMS = 3;

    // world
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -9.81f);

}