package com.derekentringer.gizmo.component.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.log.GLog;

public class BaseScreen implements Screen {

    private static final String TAG = BaseScreen.class.getSimpleName();

    public Rectangle baseViewPort;

    public float baseWidth = Constants.GAME_WIDTH;
    public float baseHeight = Constants.GAME_HEIGHT;
    public float baseScale = 1f;
    public Vector2 baseCrop = new Vector2(0f, 0f);


    public BaseScreen() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //set the view port
        Gdx.gl.glViewport((int) baseViewPort.x,
                (int) baseViewPort.y,
                (int) baseViewPort.width,
                (int) baseViewPort.height);
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;

        if (aspectRatio > Constants.ASPECT_RATIO) {
            baseScale = (float) height / (float) Constants.GAME_HEIGHT;
            baseCrop.x = (width - Constants.GAME_WIDTH * baseScale) / 2f;
        }
        else if (aspectRatio < Constants.ASPECT_RATIO) {
            baseScale = (float) width / (float) Constants.GAME_WIDTH;
            baseCrop.y = (height - Constants.GAME_HEIGHT * baseScale) / 2f;
        }
        else {
            baseScale = (float) width / (float) Constants.GAME_WIDTH;
        }

        baseWidth = Constants.GAME_WIDTH * baseScale;
        baseHeight = Constants.GAME_HEIGHT * baseScale;

        baseViewPort = new Rectangle(baseCrop.x, baseCrop.y, baseWidth, baseHeight);
        
        GLog.d(TAG, "RESIZE: " + baseScale);
        GLog.d(TAG, "RESIZE: " + baseCrop);
        GLog.d(TAG, "RESIZE: " + baseHeight);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}