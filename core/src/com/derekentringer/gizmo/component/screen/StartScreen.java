package com.derekentringer.gizmo.component.screen;

import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.stage.StartStage;
import com.derekentringer.gizmo.util.ScreenUtils;
import com.derekentringer.gizmo.util.log.GLog;

public class StartScreen extends BaseScreen {

    private final static String TAG = StartScreen.class.getSimpleName();

    private Gizmo mGizmo;
    private StartStage mStartStage;

    public StartScreen(Gizmo gizmo) {
        mGizmo = gizmo;
        mStartStage = new StartStage(this);
    }

    public void startGame() {
        mGizmo.setScreen(new GameScreen());
        this.dispose();
        //Gizmo.getGooglePlayServices().showAchievement();
    }

    public void viewGameControls() {
        mGizmo.setScreen(new ControlsScreen(mGizmo));
        this.dispose();
    }

    public void viewAbout() {
        mGizmo.setScreen(new AboutScreen(mGizmo));
        this.dispose();
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
        GLog.d(TAG, "pause");
    }

    @Override
    public void resume() {
        GLog.d(TAG, "resume");
    }

    @Override
    public void hide() {
        GLog.d(TAG, "hide");
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
        mStartStage.dispose();
    }

}