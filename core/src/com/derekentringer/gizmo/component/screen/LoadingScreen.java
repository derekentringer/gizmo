package com.derekentringer.gizmo.component.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.derekentringer.gizmo.Gizmo;

public class LoadingScreen extends AbstractScreen {

    private static final String TAG = LoadingScreen.class.getSimpleName();

    public LoadingScreen(Gizmo gizmoGame) {
        super(gizmoGame);
    }

    @Override
    public void show() {
        mGizmoGame.assetManager.load("res/images/hud/hud_lives_one.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_lives_two.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_lives_three.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_lives_four.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_lives_five.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/hud/hud_hearts_two.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_hearts_three.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_hearts_four.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_hearts_five.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_hearts_six.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_hearts_seven.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_hearts_eight.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_hearts_nine.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/hud/hud_hearts_ten.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_running_left.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_running_right.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_standing_right_large.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_standing_left_large.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_jump_up_right_large.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_jump_up_left_large.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_jump_fall_right_large.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_jump_fall_left_large.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_running_flinching_left.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_running_flinching_right.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_standing_left_large_flinching.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_standing_right_large_flinching.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_jump_up_left_large_flinching.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_jump_up_right_large_flinching.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_jump_fall_left_large_flinching.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/gizmo/gizmo_jump_fall_right_large_flinching.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/enemies/phantom/phantom_left.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/enemies/phantom/phantom_right.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/enemies/boss/phantom.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/enemies/boss/fireball.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/objects/key_gold.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/objects/key_bronze.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/objects/key_blood.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/objects/key_black.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/objects/heart.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/objects/life.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/items/drop_heart.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/doors/door_gold.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/doors/door_bronze.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/doors/door_blood.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/doors/door_black.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/doors/door_gold_opening.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/doors/door_bronze_opening.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/doors/door_blood_opening.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/doors/door_black_opening.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/doors/door_opened.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/items/boomerang_wood.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/items/boomerang_wood_pickup.png", Texture.class);
        mGizmoGame.assetManager.load("res/images/items/boomerang_wood_pickup_shine.png", Texture.class);

        mGizmoGame.assetManager.load("res/images/tiles/lava.png", Texture.class);

        mGizmoGame.assetManager.load("res/music/background.ogg", Music.class);
        mGizmoGame.assetManager.load("res/sfx/jump.ogg", Sound.class);

        mGizmoGame.assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        if (mGizmoGame.assetManager.update()) {
            mGizmoGame.setScreen(new GameScreen(mGizmoGame));
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
        mGizmoGame.assetManager.dispose();
    }

}