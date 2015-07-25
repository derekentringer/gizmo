package com.derekentringer.gizmo.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.util.constant.Constants;

public class HudStage extends Stage {

    private OrthographicCamera hudCamera;
    private SpriteBatch sSpriteBatch;

    private Texture texture;

    public HudStage() {
        setupCamera();
        sSpriteBatch = new SpriteBatch();
        texture = Gizmo.assetManager.get("res/banner/banner.png", Texture.class);
    }

    private void setupCamera() {
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        hudCamera.update();
    }

    @Override
    public void draw() {
        super.draw();
        sSpriteBatch.begin();
        sSpriteBatch.draw(texture, 10, 10);
        sSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}