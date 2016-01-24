package com.derekentringer.gizmo.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.log.GLog;

public class ScreenUtils {

    private static final String TAG = ScreenUtils.class.getSimpleName();

    public static float scale = 1f;
    public static float scaledWidth;
    public static float scaledHeight;
    public static Vector2 crop;

    public static void renderScreen(Rectangle viewPort) {
        //clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //set the viewport
        Gdx.gl.glViewport((int) viewPort.x,
                (int) viewPort.y,
                (int) viewPort.width,
                (int) viewPort.height);
    }

    public static Rectangle resizeScreen(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        crop = new Vector2(0f, 0f);
        scale = 1f;

        if (aspectRatio > Constants.ASPECT_RATIO) {
            scale = (float) height / (float) Constants.GAME_HEIGHT;
            crop.x = (width - Constants.GAME_WIDTH * scale) / 2f;
        }
        else if (aspectRatio < Constants.ASPECT_RATIO) {
            scale = (float) width / (float) Constants.GAME_WIDTH;
            crop.y = (height - Constants.GAME_HEIGHT * scale) / 2f;
        }
        else {
            scale = (float) width / (float) Constants.GAME_WIDTH;
        }

        scaledWidth = (float) Constants.GAME_WIDTH * scale;
        scaledHeight = (float) Constants.GAME_HEIGHT * scale;

        GLog.d(TAG, "RESIZE: " + scale);
        GLog.d(TAG, "RESIZE: " + crop);
        GLog.d(TAG, "RESIZE: " + scaledWidth);
        GLog.d(TAG, "RESIZE: " + scaledHeight);

        return new Rectangle(crop.x, crop.y, scaledWidth, scaledHeight);
    }

}