package com.derekentringer.gizmo.component.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.util.ScreenUtils;

public class StartScreen extends ScreenAdapter {

    private final static String TAG = StartScreen.class.getSimpleName();

    private Rectangle mViewPort;

    private Gizmo mGizmo;

    public StartScreen(Gizmo gizmo) {
        mGizmo = gizmo;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.renderScreen(mViewPort);
    }

    @Override
    public void resize(int width, int height) {
        mViewPort = ScreenUtils.resizeScreen(width, height);
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