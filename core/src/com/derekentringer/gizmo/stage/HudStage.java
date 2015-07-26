package com.derekentringer.gizmo.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.util.constant.Constants;

public class HudStage extends Stage {

    private static final int HUD_PADDING = 10;

    private OrthographicCamera hudCamera;
    private SpriteBatch sSpriteBatch;

    private Vector2 hudPosition = new Vector2();

    private Texture hudHeartsTwo;

    public HudStage() {
        setupCamera();
        sSpriteBatch = new SpriteBatch();
        hudHeartsTwo = Gizmo.assetManager.get("res/images/hud/hud_hearts_two.png", Texture.class);
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
        sSpriteBatch.draw(hudHeartsTwo, hudPosition.x, hudPosition.y);
        sSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void updateHudLayout(Float scale, Vector2 crop, float gameHeight) {
        hudPosition.x = Math.abs(crop.x) / scale;
        hudPosition.y = Math.abs(gameHeight - hudHeartsTwo.getHeight() * scale - HUD_PADDING * scale) / scale;
    }

}