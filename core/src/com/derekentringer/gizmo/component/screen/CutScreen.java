package com.derekentringer.gizmo.component.screen;

import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.stage.CutStage;
import com.derekentringer.gizmo.util.ScreenUtils;
import com.derekentringer.gizmo.util.log.GLog;

public class CutScreen extends BaseScreen {

    private static final String TAG = CutScreen.class.getSimpleName();

    private CutStage mCutStage;
    private Gizmo mGizmo;

    public CutScreen(Gizmo gizmo) {
        mGizmo = gizmo;
        mCutStage = new CutStage(this);
    }

    public void onContinueToGame() {
        mGizmo.setScreen(new GameScreen());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.renderScreen(mViewPort);

        mCutStage.act(delta);
        mCutStage.draw();
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
        mCutStage.dispose();
    }

}