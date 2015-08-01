package com.derekentringer.gizmo.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.stage.GameStage;
import com.derekentringer.gizmo.stage.HudStage;
import com.derekentringer.gizmo.settings.Constants;

public class GameScreen implements Screen {

    private HudStage hudStage;
    private GameStage gameStage;
    private Rectangle viewPort;
    private int levelToLoad = 0;

    //private FPSLogger fpsLogger = new FPSLogger();

    public GameScreen() {
        Constants.buildGameLevelList();
        gameStage = new GameStage();
        hudStage = new HudStage(gameStage);
        if (LocalDataManager.loadPlayerActorData() != null) {
            levelToLoad = LocalDataManager.loadPlayerActorData().getCurrentLevel();
        }
        gameStage.init(Constants.gameLevels.get(levelToLoad));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        //clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //set the viewport
        Gdx.gl.glViewport((int) viewPort.x, (int) viewPort.y,
                (int) viewPort.width, (int) viewPort.height);

        //update the game stage
        gameStage.act(delta);
        gameStage.draw();

        //update the hud stage
        hudStage.act(delta);
        hudStage.draw();

        //fpsLogger.log();
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

        viewPort = new Rectangle(crop.x, crop.y, w, h);

        hudStage.updateHudLayout(scale, crop, h);

        System.out.println("RESIZE: " + scale);
        System.out.println("RESIZE: " + crop);
        System.out.println("RESIZE: " + h);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        gameStage.quitGame();
    }

    @Override
    public void dispose() {
        hudStage.dispose();
        gameStage.dispose();
    }

}