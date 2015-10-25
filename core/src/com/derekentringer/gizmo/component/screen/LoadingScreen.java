package com.derekentringer.gizmo.component.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.derekentringer.gizmo.Gizmo;

public class LoadingScreen extends ScreenAdapter {

    private static final String TAG = LoadingScreen.class.getSimpleName();

    private Gizmo mGizmo;

    public LoadingScreen(Gizmo gizmo) {
        mGizmo = gizmo;
    }

    @Override
    public void show() {
        Gizmo.assetManager.load("res/image/start/heart.png", Texture.class);

        Gizmo.assetManager.load("res/image/hud/hud_lives_one.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_lives_two.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_lives_three.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_lives_four.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_lives_five.png", Texture.class);

        Gizmo.assetManager.load("res/image/hud/hud_hearts_two.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_hearts_three.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_hearts_four.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_hearts_five.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_hearts_six.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_hearts_seven.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_hearts_eight.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_hearts_nine.png", Texture.class);
        Gizmo.assetManager.load("res/image/hud/hud_hearts_ten.png", Texture.class);

        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_running_left.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_running_right.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_standing_right_large.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_standing_left_large.png", Texture.class);

        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_jump_up_right_large.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_jump_up_left_large.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_jump_fall_right_large.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_jump_fall_left_large.png", Texture.class);

        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_running_flinching_left.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_running_flinching_right.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_standing_left_large_flinching.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_standing_right_large_flinching.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_jump_up_left_large_flinching.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_jump_up_right_large_flinching.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_jump_fall_left_large_flinching.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_jump_fall_right_large_flinching.png", Texture.class);

        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_digging_right.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/gizmo/gizmo_digging_left.png", Texture.class);

        Gizmo.assetManager.load("res/image/character/enemy/phantom/phantom_left.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/enemy/phantom/phantom_right.png", Texture.class);

        Gizmo.assetManager.load("res/image/character/boss/phantom/phantom.png", Texture.class);
        Gizmo.assetManager.load("res/image/character/boss/phantom/fireball.png", Texture.class);

        Gizmo.assetManager.load("res/image/object/key_gold.png", Texture.class);
        Gizmo.assetManager.load("res/image/object/key_bronze.png", Texture.class);
        Gizmo.assetManager.load("res/image/object/key_blood.png", Texture.class);
        Gizmo.assetManager.load("res/image/object/key_black.png", Texture.class);

        Gizmo.assetManager.load("res/image/object/heart.png", Texture.class);
        Gizmo.assetManager.load("res/image/object/life.png", Texture.class);

        Gizmo.assetManager.load("res/image/drop/drop_heart.png", Texture.class);
        Gizmo.assetManager.load("res/image/drop/drop_crystal_blue.png", Texture.class);

        Gizmo.assetManager.load("res/image/door/door_gold.png", Texture.class);
        Gizmo.assetManager.load("res/image/door/door_bronze.png", Texture.class);
        Gizmo.assetManager.load("res/image/door/door_blood.png", Texture.class);
        Gizmo.assetManager.load("res/image/door/door_black.png", Texture.class);

        Gizmo.assetManager.load("res/image/door/door_gold_opening.png", Texture.class);
        Gizmo.assetManager.load("res/image/door/door_bronze_opening.png", Texture.class);
        Gizmo.assetManager.load("res/image/door/door_blood_opening.png", Texture.class);
        Gizmo.assetManager.load("res/image/door/door_black_opening.png", Texture.class);
        Gizmo.assetManager.load("res/image/door/door_opened.png", Texture.class);

        Gizmo.assetManager.load("res/image/item/boomerang_wood.png", Texture.class);
        Gizmo.assetManager.load("res/image/item/boomerang_wood_pickup_shine.png", Texture.class);
        Gizmo.assetManager.load("res/image/item/boomerang_emerald.png", Texture.class);
        Gizmo.assetManager.load("res/image/item/boomerang_emerald_pickup_shine.png", Texture.class);
        Gizmo.assetManager.load("res/image/item/boomerang_amethyst.png", Texture.class);
        Gizmo.assetManager.load("res/image/item/boomerang_amethyst_pickup_shine.png", Texture.class);
        Gizmo.assetManager.load("res/image/item/boomerang_bloodstone.png", Texture.class);
        Gizmo.assetManager.load("res/image/item/boomerang_bloodstone_pickup_shine.png", Texture.class);

        Gizmo.assetManager.load("res/image/tile/lava.png", Texture.class);

        Gizmo.assetManager.load("res/image/tile/destroyable_block_fall.png", Texture.class);
        Gizmo.assetManager.load("res/image/tile/destroyable_block_dirt.png", Texture.class);
        Gizmo.assetManager.load("res/image/tile/destroyable_block_clay.png", Texture.class);
        Gizmo.assetManager.load("res/image/tile/destroy_block.png", Texture.class);

        //Gizmo.assetManager.load("res/music/background.ogg", Music.class);
        //Gizmo.assetManager.load("res/sfx/jump.ogg", Sound.class);

        Gizmo.assetManager.finishLoading();
    }

    @Override
    public void render(float delta) {
        if (Gizmo.assetManager.update()) {
            mGizmo.setScreen(new StartScreen(mGizmo));
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