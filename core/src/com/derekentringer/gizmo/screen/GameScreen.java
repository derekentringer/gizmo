package com.derekentringer.gizmo.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.stage.GameStage;
import com.derekentringer.gizmo.util.constant.Constants;

public class GameScreen implements Screen {

    private GameStage stage;
    private Rectangle viewPort;

    public GameScreen() {
        stage = new GameStage(Constants.LEVEL_SEVEN);
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

        //Update the stage
        stage.draw();
        stage.act(delta);
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
        //TODO not sure how to destroy
        //the world for Android successfully
        stage.dispose();
    }

}