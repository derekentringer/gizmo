package com.derekentringer.gizmo.component.screen;

import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.component.stage.HudStage;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.util.GameLevelUtils;

public class GameScreen extends BaseScreen {

    private static final String TAG = GameScreen.class.getSimpleName();

    private HudStage mHudStage;
    private GameStage mGameStage;

    private int mLevelToLoad = 0;

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
        //update the game stage
        mGameStage.act(delta);
        mGameStage.draw();

        //update the hud stage
        mHudStage.act(delta);
        mHudStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        mHudStage.updateHudLayout(baseScale, baseCrop, baseHeight);
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