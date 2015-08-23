package com.derekentringer.gizmo.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.derekentringer.gizmo.settings.Constants;

public class WorldUtils {

    private static final String TAG = WorldUtils.class.getSimpleName();

    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static void destroyBodies(World world) {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            world.destroyBody(body);
        }
    }

    public static void destroyBody(World world, Body body) {
        body.setUserData(null);
        body.setActive(false);
        world.destroyBody(body);
    }

    public static float ppmCalc(int dimension) {
        return dimension / Constants.PPM;
    }

    public static float ppmCalc(float dimension) {
        return dimension / Constants.PPM;
    }

    public static float ppmCalcReverse(float dimension) {
        return dimension * Constants.PPM;
    }

    public static float generatRandomPositiveNegativeValue(float max, float min) {
        float i = -min + (float) (Math.random() * ((max - (-min)) + 1));
        return i;
    }

}