package com.derekentringer.gizmo.util.log;

import com.badlogic.gdx.Gdx;
import com.derekentringer.gizmo.settings.Constants;

public class GLog {

    public static void d(String tag, String message) {
        if (Constants.IS_DEBUG) {
            Gdx.app.debug(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (Constants.IS_DEBUG) {
            Gdx.app.error(tag, message);
        }
    }

}