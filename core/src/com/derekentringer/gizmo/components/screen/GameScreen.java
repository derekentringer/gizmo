package com.derekentringer.gizmo.components.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.components.stage.GameStage;
import com.derekentringer.gizmo.components.stage.HudStage;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.GameLevelUtils;
import com.derekentringer.gizmo.util.log.GLog;

public class GameScreen implements Screen {

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
        //clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //set the viewport
        Gdx.gl.glViewport((int) mViewPort.x,
                (int) mViewPort.y,
                (int) mViewPort.width,
                (int) mViewPort.height);

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
        float aspectRatio = (float) width / (float) height;
        float scale = 1f;
        Vector2 crop;
        float w;
        float h;

        crop = new Vector2(0f, 0f);

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

        w = (float) Constants.GAME_WIDTH * scale;
        h = (float) Constants.GAME_HEIGHT * scale;

        mViewPort = new Rectangle(crop.x, crop.y, w, h);

        mHudStage.updateHudLayout(scale, crop, h);

        GLog.d(TAG, "RESIZE: " + scale);
        GLog.d(TAG, "RESIZE: " + crop);
        GLog.d(TAG, "RESIZE: " + h);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        mGameStage.quitGame();
    }

    @Override
    public void dispose() {
        mHudStage.dispose();
        mGameStage.dispose();
    }

}