package com.derekentringer.gizmo.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.derekentringer.gizmo.Gizmo;

public class LoadingScreen extends AbstractScreen {

    public LoadingScreen(Gizmo game) {
        super(game);
    }

    @Override
    public void show() {
        game.assetManager.load("res/images/gizmo_running_left.png", Texture.class);
        game.assetManager.load("res/images/gizmo_running_right.png", Texture.class);
        game.assetManager.load("res/images/gizmo_standing_right.png", Texture.class);
        game.assetManager.load("res/images/gizmo_standing_left.png", Texture.class);

        game.assetManager.load("res/images/gizmo_jump_up_right.png", Texture.class);
        game.assetManager.load("res/images/gizmo_jump_up_left.png", Texture.class);
        game.assetManager.load("res/images/gizmo_jump_fall_right.png", Texture.class);
        game.assetManager.load("res/images/gizmo_jump_fall_left.png", Texture.class);

        game.assetManager.load("res/images/phantom_left.png", Texture.class);
        game.assetManager.load("res/images/phantom_right.png", Texture.class);

        game.assetManager.load("res/music/background.ogg", Music.class);
        game.assetManager.load("res/sfx/jump.ogg", Sound.class);
        game.assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        if (game.assetManager.update()) {
            game.setScreen(new GameScreen());
        }
    }

    @Override
    public void resize(int width, int height) {
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
    }

}