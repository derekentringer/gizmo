package com.derekentringer.gizmo.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.data.player.PlayerData;
import com.derekentringer.gizmo.stage.interfaces.IHudStageDelegate;
import com.derekentringer.gizmo.util.constant.Constants;

public class HudStage extends Stage implements IHudStageDelegate {

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
    private int sHearts;

    private ShapeRenderer redShapeRenderer;
    private ShapeRenderer whiteShapeRenderer;
    private static boolean projectionMatrixSet;

    private float initialWidth;
    private float redShapeWidth;
    private float redShapeHeight;

    public HudStage(GameStage gameStage) {
        gameStage.hudStageDelegate = this;
        setupCamera();

        redShapeRenderer = new ShapeRenderer();
        whiteShapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;

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

        sSpriteBatch.setProjectionMatrix(hudCamera.combined);

        if(!projectionMatrixSet){
            redShapeRenderer.setProjectionMatrix(sSpriteBatch.getProjectionMatrix());
        }
        whiteShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        whiteShapeRenderer.setColor(Color.WHITE);
        whiteShapeRenderer.rect(hudPosition.x, hudPosition.y + currentTexture.getHeight() - redShapeHeight, currentTexture.getWidth() - 32, redShapeHeight);
        whiteShapeRenderer.end();

        redShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        redShapeRenderer.setColor(193/255f, 0, 0, 1);
        redShapeRenderer.rect(hudPosition.x + 3, hudPosition.y + currentTexture.getHeight() - redShapeHeight, redShapeWidth, redShapeHeight);
        redShapeRenderer.end();

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
        System.out.println("hudPosition.x: " + hudPosition.x);
        System.out.println("hudPosition.y: " + hudPosition.y);

        hudCamera.update();
    }

    @Override
    public void setHudHealthHearts(int hearts) {
        sHearts = hearts;
        if(sHearts == 2) {
            currentTexture = hudHeartsTwo;
        }
        else if(sHearts == 3) {
            currentTexture = hudHeartsThree;
        }
        else if(sHearts == 4) {
            currentTexture = hudHeartsFour;
        }
        else if(sHearts == 5) {
            currentTexture = hudHeartsFive;
        }
        else if(sHearts == 6) {
            currentTexture = hudHeartsSix;
        }
        else if(sHearts == 7) {
            currentTexture = hudHeartsSeven;
        }
        else if(sHearts == 8) {
            currentTexture = hudHeartsEight;
        }
        else if(sHearts == 9) {
            currentTexture = hudHeartsNine;
        }
        else if(sHearts == 10) {
            currentTexture = hudHeartsTen;
        }
    }

    @Override
    public void setHudHealth(int health) {
        sHealth = health;
        float fullHealth = PlayerData.DEFAULT_HEALTH;
        float percentFull = sHealth / fullHealth;
        float newWidth = percentFull * initialWidth;
        redShapeWidth = newWidth;
    }

    @Override
    public void resetHudShapes() {
        initialWidth = sHearts * 18;
        redShapeWidth = initialWidth;
        redShapeHeight = 20;
    }

}