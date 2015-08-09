package com.derekentringer.gizmo.components.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.derekentringer.gizmo.Gizmo;

public class LoadingScreen extends com.derekentringer.gizmo.components.screen.AbstractScreen {

    public LoadingScreen(Gizmo game) {
        super(game);
    }

    @Override
    public void show() {
        game.assetManager.load("res/images/hud/hud_lives_one.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_lives_two.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_lives_three.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_lives_four.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_lives_five.png", Texture.class);

        game.assetManager.load("res/images/hud/hud_hearts_two.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_hearts_three.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_hearts_four.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_hearts_five.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_hearts_six.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_hearts_seven.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_hearts_eight.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_hearts_nine.png", Texture.class);
        game.assetManager.load("res/images/hud/hud_hearts_ten.png", Texture.class);

        game.assetManager.load("res/images/gizmo_running_left.png", Texture.class);
        game.assetManager.load("res/images/gizmo_running_right.png", Texture.class);
        game.assetManager.load("res/images/gizmo_standing_right_large.png", Texture.class);
        game.assetManager.load("res/images/gizmo_standing_left_large.png", Texture.class);

        game.assetManager.load("res/images/gizmo_jump_up_right_large.png", Texture.class);
        game.assetManager.load("res/images/gizmo_jump_up_left_large.png", Texture.class);
        game.assetManager.load("res/images/gizmo_jump_fall_right_large.png", Texture.class);
        game.assetManager.load("res/images/gizmo_jump_fall_left_large.png", Texture.class);

        game.assetManager.load("res/images/gizmo_running_flinching_left.png", Texture.class);
        game.assetManager.load("res/images/gizmo_running_flinching_right.png", Texture.class);
        game.assetManager.load("res/images/gizmo_standing_left_large_flinching.png", Texture.class);
        game.assetManager.load("res/images/gizmo_standing_right_large_flinching.png", Texture.class);
        game.assetManager.load("res/images/gizmo_jump_up_left_large_flinching.png", Texture.class);
        game.assetManager.load("res/images/gizmo_jump_up_right_large_flinching.png", Texture.class);
        game.assetManager.load("res/images/gizmo_jump_fall_left_large_flinching.png", Texture.class);
        game.assetManager.load("res/images/gizmo_jump_fall_right_large_flinching.png", Texture.class);

        game.assetManager.load("res/images/enemies/phantom/phantom_left.png", Texture.class);
        game.assetManager.load("res/images/enemies/phantom/phantom_right.png", Texture.class);
        game.assetManager.load("res/images/enemies/phantom/phantom_large.png", Texture.class);

        game.assetManager.load("res/images/key_gold.png", Texture.class);
        game.assetManager.load("res/images/key_bronze.png", Texture.class);
        game.assetManager.load("res/images/key_blood.png", Texture.class);

        game.assetManager.load("res/images/heart.png", Texture.class);

        game.assetManager.load("res/images/door_gold.png", Texture.class);
        game.assetManager.load("res/images/door_bronze.png", Texture.class);
        game.assetManager.load("res/images/door_blood.png", Texture.class);
        game.assetManager.load("res/images/door_black.png", Texture.class);

        game.assetManager.load("res/images/door_opened.png", Texture.class);

        game.assetManager.load("res/music/background.ogg", Music.class);
        game.assetManager.load("res/sfx/jump.ogg", Sound.class);

        game.assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        if (game.assetManager.update()) {
            game.setScreen(new com.derekentringer.gizmo.components.screen.GameScreen());
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