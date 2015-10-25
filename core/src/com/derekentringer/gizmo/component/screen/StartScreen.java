package com.derekentringer.gizmo.component.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.stage.StartStage;
import com.derekentringer.gizmo.util.ScreenUtils;

public class StartScreen extends ScreenAdapter {

    private final static String TAG = StartScreen.class.getSimpleName();

    private Rectangle mViewPort;

    private StartStage mStartStage;
    private Gizmo mGizmo;

    public StartScreen(Gizmo gizmo) {
        mGizmo = gizmo;
        mStartStage = new StartStage(this);
    }

    public void startGame() {
        mGizmo.setScreen(new GameScreen());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.renderScreen(mViewPort);

        //update the start stage
        mStartStage.act(delta);
        mStartStage.draw();
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