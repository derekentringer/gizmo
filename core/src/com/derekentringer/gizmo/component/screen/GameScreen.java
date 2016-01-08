package com.derekentringer.gizmo.component.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.component.stage.HudStage;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.util.RoomUtils;
import com.derekentringer.gizmo.util.ScreenUtils;
import com.derekentringer.gizmo.util.log.GLog;

public class GameScreen extends ScreenAdapter {

    private static final String TAG = GameScreen.class.getSimpleName();

    private HudStage mHudStage;
    private GameStage mGameStage;

    private Rectangle mViewPort;

    private int mRoomToLoad = 0;

    public enum GameState {
        RUNNING,
        PAUSED
    }

    GameState mGameState = GameState.RUNNING;

    //private FPSLogger mFpsLogger = new FPSLogger();

    public GameScreen() {
        RoomUtils.buildRoomList();
        mGameStage = new GameStage(this);
        mHudStage = new HudStage(mGameStage);
        mHudStage.addListener(mGameStage);
        if (LocalDataManager.loadPlayerActorData() != null) {
            mRoomToLoad = LocalDataManager.loadPlayerActorData().getCurrentRoom();
        }
        mGameStage.init(RoomUtils.rooms.get(mRoomToLoad));
    }

    public void pauseGame(){
        if(mGameState == mGameState.RUNNING) {
            mGameState = mGameState.PAUSED;
            //TODO stop music
            //TODO load pause screen

        } else {
            mGameState = mGameState.RUNNING;
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        switch (mGameState)
        {
            case RUNNING:
                ScreenUtils.renderScreen(mViewPort);
                //update the game stage
                mGameStage.act(delta);
                mGameStage.draw();
                //update the hud stage
                mHudStage.act(delta);
                mHudStage.draw();
                //mFpsLogger.log();
                break;

            case PAUSED:
                break;

            default:
                break;
        }

        //TODO should be handled in listener callbacks
        //TODO from the InputProcessor for encapsulation reasons
        mGameStage.handleInput();
    }

    @Override
    public void resize(int width, int height) {
        mViewPort = ScreenUtils.resizeScreen(width, height);
        mHudStage.updateHudLayout(ScreenUtils.scale, ScreenUtils.crop, ScreenUtils.scaledHeight);
    }

    @Override
    public void pause() {
        GLog.d(TAG, "pause");
        pauseGame();
    }

    @Override
    public void resume() {
        GLog.d(TAG, "resume");
        pauseGame();
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