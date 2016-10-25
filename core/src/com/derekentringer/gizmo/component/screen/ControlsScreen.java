package com.derekentringer.gizmo.component.screen;

import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.stage.ControlsStage;
import com.derekentringer.gizmo.util.ScreenUtils;
import com.derekentringer.gizmo.util.log.GLog;

public class ControlsScreen extends BaseScreen {

    private final static String TAG = ControlsScreen.class.getSimpleName();

    private ControlsStage mControlsStage;
    private Gizmo mGizmo;

    public ControlsScreen(Gizmo gizmo) {
        mGizmo = gizmo;
        mControlsStage = new ControlsStage(this);
    }

    public void goBackToStartScreen() {
        mGizmo.setScreen(new StartScreen(mGizmo));
        this.dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.renderScreen(mViewPort);

        //update the controls stage
        mControlsStage.act(delta);
        mControlsStage.draw();
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
        mControlsStage.dispose();
    }

}