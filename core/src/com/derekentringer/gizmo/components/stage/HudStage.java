package com.derekentringer.gizmo.components.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.components.stage.interfaces.IHudStageDelegate;
import com.derekentringer.gizmo.settings.Constants;

public class HudStage extends Stage implements IHudStageDelegate {

    private static final int HUD_PADDING = 10;

    private OrthographicCamera hudCamera;
    private SpriteBatch sSpriteBatch;

    private Vector2 hudLivesPosition = new Vector2();
    private Vector2 hudHealthPosition = new Vector2();

    private Texture hudLivesOne;
    private Texture hudLivesTwo;
    private Texture hudLivesThree;
    private Texture hudLivesFour;
    private Texture hudLivesFive;

    private Texture hudHeartsTwo;
    private Texture hudHeartsThree;
    private Texture hudHeartsFour;
    private Texture hudHeartsFive;
    private Texture hudHeartsSix;
    private Texture hudHeartsSeven;
    private Texture hudHeartsEight;
    private Texture hudHeartsNine;
    private Texture hudHeartsTen;

    private Texture currentLivesTexture;
    private Texture currentHealthTexture;

    private int sLives;
    private int sHearts;

    private ShapeRenderer redShapeRenderer;
    private ShapeRenderer whiteShapeRenderer;
    private boolean projectionMatrixSet;

    private float initialWidth;
    private float redShapeWidth;
    private float redShapeHeight = 20;

    public HudStage(com.derekentringer.gizmo.components.stage.GameStage gameStage) {
        gameStage.hudStageDelegate = this;
        setupCamera();

        redShapeRenderer = new ShapeRenderer();
        whiteShapeRenderer = new ShapeRenderer();
        projectionMatrixSet = false;

        sSpriteBatch = new SpriteBatch();

        hudLivesOne = Gizmo.assetManager.get("res/images/hud/hud_lives_one.png", Texture.class);
        hudLivesTwo = Gizmo.assetManager.get("res/images/hud/hud_lives_two.png", Texture.class);
        hudLivesThree = Gizmo.assetManager.get("res/images/hud/hud_lives_three.png", Texture.class);
        hudLivesFour = Gizmo.assetManager.get("res/images/hud/hud_lives_four.png", Texture.class);
        hudLivesFive = Gizmo.assetManager.get("res/images/hud/hud_lives_five.png", Texture.class);

        hudHeartsTwo = Gizmo.assetManager.get("res/images/hud/hud_hearts_two.png", Texture.class);
        hudHeartsThree = Gizmo.assetManager.get("res/images/hud/hud_hearts_three.png", Texture.class);
        hudHeartsFour = Gizmo.assetManager.get("res/images/hud/hud_hearts_four.png", Texture.class);
        hudHeartsFive = Gizmo.assetManager.get("res/images/hud/hud_hearts_five.png", Texture.class);
        hudHeartsSix = Gizmo.assetManager.get("res/images/hud/hud_hearts_six.png", Texture.class);
        hudHeartsSeven = Gizmo.assetManager.get("res/images/hud/hud_hearts_seven.png", Texture.class);
        hudHeartsEight = Gizmo.assetManager.get("res/images/hud/hud_hearts_eight.png", Texture.class);
        hudHeartsNine = Gizmo.assetManager.get("res/images/hud/hud_hearts_nine.png", Texture.class);
        hudHeartsTen = Gizmo.assetManager.get("res/images/hud/hud_hearts_ten.png", Texture.class);

        currentLivesTexture = hudLivesOne;
        currentHealthTexture = hudHeartsTwo;
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

        if (!projectionMatrixSet) {
            whiteShapeRenderer.setProjectionMatrix(sSpriteBatch.getProjectionMatrix());
            redShapeRenderer.setProjectionMatrix(sSpriteBatch.getProjectionMatrix());
        }
        whiteShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        whiteShapeRenderer.setColor(Color.WHITE);
        whiteShapeRenderer.rect(hudHealthPosition.x, hudHealthPosition.y + currentHealthTexture.getHeight() - redShapeHeight, currentHealthTexture.getWidth() - 32, redShapeHeight);
        whiteShapeRenderer.end();

        redShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        redShapeRenderer.setColor(193 / 255f, 0, 0, 1);
        redShapeRenderer.rect(hudHealthPosition.x + 3, hudHealthPosition.y + currentHealthTexture.getHeight() - redShapeHeight, redShapeWidth, redShapeHeight);
        redShapeRenderer.end();

        sSpriteBatch.begin();
        sSpriteBatch.draw(currentLivesTexture, hudLivesPosition.x, hudLivesPosition.y);
        sSpriteBatch.draw(currentHealthTexture, hudHealthPosition.x, hudHealthPosition.y);
        sSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void updateHudLayout(Float scale, Vector2 crop, float gameHeight) {

        hudLivesPosition.x = Math.abs(crop.x) / scale;
        hudLivesPosition.y = Math.abs(gameHeight - currentLivesTexture.getHeight() * scale - HUD_PADDING * scale) / scale;

        hudHealthPosition.x = Math.abs(crop.x) / scale;
        hudHealthPosition.y = Math.abs(gameHeight - currentLivesTexture.getHeight() * scale - currentHealthTexture.getHeight() * scale - HUD_PADDING * scale) / scale;



        System.out.println("hudPosition.x: " + hudHealthPosition.x);
        System.out.println("hudPosition.y: " + hudHealthPosition.y);

        hudCamera.update();
    }

    @Override
    public void setHudHealthHearts(int hearts) {
        sHearts = hearts;
        if (sHearts == 2) {
            currentHealthTexture = hudHeartsTwo;
        }
        else if (sHearts == 3) {
            currentHealthTexture = hudHeartsThree;
        }
        else if (sHearts == 4) {
            currentHealthTexture = hudHeartsFour;
        }
        else if (sHearts == 5) {
            currentHealthTexture = hudHeartsFive;
        }
        else if (sHearts == 6) {
            currentHealthTexture = hudHeartsSix;
        }
        else if (sHearts == 7) {
            currentHealthTexture = hudHeartsSeven;
        }
        else if (sHearts == 8) {
            currentHealthTexture = hudHeartsEight;
        }
        else if (sHearts == 9) {
            currentHealthTexture = hudHeartsNine;
        }
        else if (sHearts == 10) {
            currentHealthTexture = hudHeartsTen;
        }
    }

    @Override
    public void setHudHealth(int health) {
        float fullHealth = sHearts * PlayerModel.HEART_HEALTH_AMOUNT;
        float percentFull = health / fullHealth;
        float newWidth = percentFull * initialWidth;

        if (sHearts == 5 && health > 29) {
            newWidth = newWidth + 3;
        }
        else if (sHearts == 4 && health > 19) {
            newWidth = newWidth + 2;
        }
        else if (sHearts == 3 && health > 9) {
            newWidth = newWidth + 1;
        }

        redShapeWidth = newWidth;
    }

    @Override
    public void resetHudShapes() {
        initialWidth = sHearts * 18;
        redShapeWidth = initialWidth;
        redShapeHeight = 20;
    }

    @Override
    public void setHudLives(int lives) {
        sLives = lives;
        if(sLives == 5) {
            currentLivesTexture = hudLivesFive;
        }
        else if (sLives == 4) {
            currentLivesTexture = hudLivesFour;
        }
        else if (sLives == 3) {
            currentLivesTexture = hudLivesThree;
        }
        else if (sLives == 2) {
            currentLivesTexture = hudLivesTwo;
        }
        else if (sLives == 1) {
            currentLivesTexture = hudLivesOne;
        }
    }

}