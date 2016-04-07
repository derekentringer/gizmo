package com.derekentringer.gizmo.component.screen;

import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.stage.AboutStage;
import com.derekentringer.gizmo.util.ScreenUtils;
import com.derekentringer.gizmo.util.log.GLog;

public class AboutScreen extends BaseScreen {

    private final static String TAG = AboutScreen.class.getSimpleName();

    private AboutStage mAboutStage;
    private Gizmo mGizmo;

    public AboutScreen(Gizmo gizmo) {
        mGizmo = gizmo;
        mAboutStage = new AboutStage(this);
    }

    public void goBackToStartScreen() {
        mGizmo.setScreen(new StartScreen(mGizmo));
        this.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.renderScreen(mViewPort);

        //update the about stage
        mAboutStage.act(delta);
        mAboutStage.draw();
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
        mAboutStage.dispose();
    }

}