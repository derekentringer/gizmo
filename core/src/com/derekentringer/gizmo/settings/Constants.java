package com.derekentringer.gizmo.settings;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

public class Constants {

    // when a collision occurs, the collider will check it’s
    // categoryBits against the collided’s maskBits.
    public static final short ENEMY_ENTITY = 0x1;      // 0001
    public static final short WORLD_ENTITY = 0x1 << 1; // 0010 or 0x2 in hex

    // debugging
    public static final boolean IS_DEBUG = true;
    public static final boolean IS_DEBUG_BOX2D = false;
    public static final int LOG_LEVEL = Logger.DEBUG;

    // rendering
    public static final float TIME_STEP = 1 / 300f;
    public static float ACCUMULATOR = 0f;

    // game
    public static final int GAME_WIDTH = 640;
    public static final int GAME_HEIGHT = 360;
    public static final int ASPECT_RATIO = GAME_WIDTH / GAME_HEIGHT;
    public static final float PPM = 100;

    // world
    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -9.81f);

}