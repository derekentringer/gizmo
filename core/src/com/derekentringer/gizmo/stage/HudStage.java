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
    private Texture hudHeartsThree;
    private Texture hudHeartsFour;
    private Texture hudHeartsFive;
    private Texture hudHeartsSix;
    private Texture hudHeartsSeven;
    private Texture hudHeartsEight;
    private Texture hudHeartsNine;
    private Texture hudHeartsTen;

    private Texture currentTexture;

    private int sHealth;

    public HudStage() {
        setupCamera();

        sSpriteBatch = new SpriteBatch();

        hudHeartsTwo = Gizmo.assetManager.get("res/images/hud/hud_hearts_two.png", Texture.class);
        hudHeartsThree = Gizmo.assetManager.get("res/images/hud/hud_hearts_three.png", Texture.class);
        hudHeartsFour = Gizmo.assetManager.get("res/images/hud/hud_hearts_four.png", Texture.class);
        hudHeartsFive = Gizmo.assetManager.get("res/images/hud/hud_hearts_five.png", Texture.class);
        hudHeartsSix = Gizmo.assetManager.get("res/images/hud/hud_hearts_six.png", Texture.class);
        hudHeartsSeven = Gizmo.assetManager.get("res/images/hud/hud_hearts_seven.png", Texture.class);
        hudHeartsEight = Gizmo.assetManager.get("res/images/hud/hud_hearts_eight.png", Texture.class);
        hudHeartsNine = Gizmo.assetManager.get("res/images/hud/hud_hearts_nine.png", Texture.class);
        hudHeartsTen = Gizmo.assetManager.get("res/images/hud/hud_hearts_ten.png", Texture.class);

        currentTexture = hudHeartsTwo;
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
        sSpriteBatch.draw(currentTexture, hudPosition.x, hudPosition.y);
        sSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void updateHudLayout(Float scale, Vector2 crop, float gameHeight) {
        hudPosition.x = Math.abs(crop.x) / scale;
        hudPosition.y = Math.abs(gameHeight - hudHeartsTwo.getHeight() * scale - HUD_PADDING * scale) / scale;
        System.out.print("hudPosition.x: "+hudPosition.x);
        System.out.print("hudPosition.y: "+hudPosition.y);
    }

    public void setHealthHearts(int hearts) {
        if(hearts == 2) {
            currentTexture = hudHeartsTwo;
        }
    }

    public int getHealth() {
        return sHealth;
    }
    public void setHealth(int health) {
        sHealth = health;
        //adjust background with accordingly
    }

}