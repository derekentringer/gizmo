package com.derekentringer.gizmo.component.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.component.stage.HudStage;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.util.GameLevelUtils;
import com.derekentringer.gizmo.util.ScreenUtils;
import com.derekentringer.gizmo.util.log.GLog;

public class GameScreen extends ScreenAdapter {

    private static final String TAG = GameScreen.class.getSimpleName();

    private HudStage mHudStage;
    private GameStage mGameStage;

    private Rectangle mViewPort;

    private int mLevelToLoad = 0;

    //private FPSLogger mFpsLogger = new FPSLogger();

    public GameScreen() {
        GameLevelUtils.buildGameLevelList();
        mGameStage = new GameStage();
        mHudStage = new HudStage(mGameStage);
        mHudStage.addListener(mGameStage);
        if (LocalDataManager.loadPlayerActorData() != null) {
            mLevelToLoad = LocalDataManager.loadPlayerActorData().getCurrentLevel();
        }
        mGameStage.init(GameLevelUtils.gameLevels.get(mLevelToLoad));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.renderScreen(mViewPort);

        //update the game stage
        mGameStage.act(delta);
        mGameStage.draw();

        //update the hud stage
        mHudStage.act(delta);
        mHudStage.draw();

        //mFpsLogger.log();
    }

    @Override
    public void resize(int width, int height) {
        mViewPort = ScreenUtils.resizeScreen(width, height);
        mHudStage.updateHudLayout(ScreenUtils.scale, ScreenUtils.crop, ScreenUtils.scaledHeight);
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
        mGameStage.quitGame();
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
        this.mGameStage.dispose();
        this.mHudStage.dispose();
    }

}