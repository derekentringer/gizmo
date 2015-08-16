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
import com.derekentringer.gizmo.util.log.GLog;

public class HudStage extends Stage implements IHudStageDelegate {

    private static final String TAG = HudStage.class.getSimpleName();

    private static final int HUD_PADDING = 10;

    private OrthographicCamera mHudCamera;
    private SpriteBatch mSpriteBatch;

    private Vector2 mHudLivesPosition = new Vector2();
    private Vector2 mHudHealthPosition = new Vector2();

    private Texture mHudLivesOne;
    private Texture mHudLivesTwo;
    private Texture mHudLivesThree;
    private Texture mHudLivesFour;
    private Texture mHudLivesFive;

    private Texture mHudHeartsTwo;
    private Texture mHudHeartsThree;
    private Texture mHudHeartsFour;
    private Texture mHudHeartsFive;
    private Texture mHudHeartsSix;
    private Texture mHudHeartsSeven;
    private Texture mHudHeartsEight;
    private Texture mHudHeartsNine;
    private Texture mHudHeartsTen;

    private Texture mCurrentLivesTexture;
    private Texture mCurrentHealthTexture;

    private int mLives;
    private int mHearts;

    private ShapeRenderer mRedShapeRenderer;
    private ShapeRenderer mWhiteShapeRenderer;
    private boolean mProjectionMatrixSet;

    private float mInitialWidth;
    private float mRedShapeWidth;
    private float mRedShapeHeight = 20;

    public HudStage(GameStage gameStage) {
        gameStage.hudStageDelegate = this;
        setupCamera();

        mRedShapeRenderer = new ShapeRenderer();
        mWhiteShapeRenderer = new ShapeRenderer();
        mProjectionMatrixSet = false;

        mSpriteBatch = new SpriteBatch();

        mHudLivesOne = Gizmo.assetManager.get("res/images/hud/hud_lives_one.png", Texture.class);
        mHudLivesTwo = Gizmo.assetManager.get("res/images/hud/hud_lives_two.png", Texture.class);
        mHudLivesThree = Gizmo.assetManager.get("res/images/hud/hud_lives_three.png", Texture.class);
        mHudLivesFour = Gizmo.assetManager.get("res/images/hud/hud_lives_four.png", Texture.class);
        mHudLivesFive = Gizmo.assetManager.get("res/images/hud/hud_lives_five.png", Texture.class);

        mHudHeartsTwo = Gizmo.assetManager.get("res/images/hud/hud_hearts_two.png", Texture.class);
        mHudHeartsThree = Gizmo.assetManager.get("res/images/hud/hud_hearts_three.png", Texture.class);
        mHudHeartsFour = Gizmo.assetManager.get("res/images/hud/hud_hearts_four.png", Texture.class);
        mHudHeartsFive = Gizmo.assetManager.get("res/images/hud/hud_hearts_five.png", Texture.class);
        mHudHeartsSix = Gizmo.assetManager.get("res/images/hud/hud_hearts_six.png", Texture.class);
        mHudHeartsSeven = Gizmo.assetManager.get("res/images/hud/hud_hearts_seven.png", Texture.class);
        mHudHeartsEight = Gizmo.assetManager.get("res/images/hud/hud_hearts_eight.png", Texture.class);
        mHudHeartsNine = Gizmo.assetManager.get("res/images/hud/hud_hearts_nine.png", Texture.class);
        mHudHeartsTen = Gizmo.assetManager.get("res/images/hud/hud_hearts_ten.png", Texture.class);

        mCurrentLivesTexture = mHudLivesOne;
        mCurrentHealthTexture = mHudHeartsTwo;
    }

    private void setupCamera() {
        mHudCamera = new OrthographicCamera();
        mHudCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mHudCamera.update();
    }

    @Override
    public void draw() {
        super.draw();

        mSpriteBatch.setProjectionMatrix(mHudCamera.combined);

        if (!mProjectionMatrixSet) {
            mWhiteShapeRenderer.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mRedShapeRenderer.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
        }
        mWhiteShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mWhiteShapeRenderer.setColor(Color.WHITE);
        mWhiteShapeRenderer.rect(mHudHealthPosition.x, mHudHealthPosition.y + mCurrentHealthTexture.getHeight() - mRedShapeHeight, mCurrentHealthTexture.getWidth() - 32, mRedShapeHeight);
        mWhiteShapeRenderer.end();

        mRedShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mRedShapeRenderer.setColor(193 / 255f, 0, 0, 1);
        mRedShapeRenderer.rect(mHudHealthPosition.x + 3, mHudHealthPosition.y + mCurrentHealthTexture.getHeight() - mRedShapeHeight, mRedShapeWidth, mRedShapeHeight);
        mRedShapeRenderer.end();

        mSpriteBatch.begin();
        mSpriteBatch.draw(mCurrentLivesTexture, mHudLivesPosition.x, mHudLivesPosition.y);
        mSpriteBatch.draw(mCurrentHealthTexture, mHudHealthPosition.x, mHudHealthPosition.y);
        mSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void updateHudLayout(Float scale, Vector2 crop, float gameHeight) {
        mHudLivesPosition.x = Math.abs(crop.x) / scale;
        mHudLivesPosition.y = Math.abs(gameHeight - mCurrentLivesTexture.getHeight() * scale - HUD_PADDING * scale) / scale;

        mHudHealthPosition.x = Math.abs(crop.x) / scale;
        mHudHealthPosition.y = Math.abs(gameHeight - mCurrentLivesTexture.getHeight() * scale - mCurrentHealthTexture.getHeight() * scale - HUD_PADDING * scale) / scale;

        GLog.d(TAG, "hudPosition.x: " + mHudHealthPosition.x);
        GLog.d(TAG, "hudPosition.y: " + mHudHealthPosition.y);

        mHudCamera.update();
    }

    @Override
    public void setHudHealthHearts(int hearts) {
        mHearts = hearts;
        if (mHearts == 2) {
            mCurrentHealthTexture = mHudHeartsTwo;
        }
        else if (mHearts == 3) {
            mCurrentHealthTexture = mHudHeartsThree;
        }
        else if (mHearts == 4) {
            mCurrentHealthTexture = mHudHeartsFour;
        }
        else if (mHearts == 5) {
            mCurrentHealthTexture = mHudHeartsFive;
        }
        else if (mHearts == 6) {
            mCurrentHealthTexture = mHudHeartsSix;
        }
        else if (mHearts == 7) {
            mCurrentHealthTexture = mHudHeartsSeven;
        }
        else if (mHearts == 8) {
            mCurrentHealthTexture = mHudHeartsEight;
        }
        else if (mHearts == 9) {
            mCurrentHealthTexture = mHudHeartsNine;
        }
        else if (mHearts == 10) {
            mCurrentHealthTexture = mHudHeartsTen;
        }
        resetHudShapes();
        setHudHealth(mHearts * PlayerModel.HEART_HEALTH_AMOUNT);
    }

    @Override
    public void setHudHealth(int health) {
        float fullHealth = mHearts * PlayerModel.HEART_HEALTH_AMOUNT;
        float percentFull = health / fullHealth;
        float newWidth = percentFull * mInitialWidth;

        if (mHearts == 5 && health > 29) {
            newWidth = newWidth + 3;
        }
        else if (mHearts == 4 && health > 19) {
            newWidth = newWidth + 2;
        }
        else if (mHearts == 3 && health > 9) {
            newWidth = newWidth + 1;
        }

        mRedShapeWidth = newWidth;
    }

    @Override
    public void resetHudShapes() {
        mInitialWidth = mHearts * 18;
        mRedShapeWidth = mInitialWidth;
        mRedShapeHeight = 20;
    }

    @Override
    public void setHudLives(int lives) {
        mLives = lives;
        if(mLives == 5) {
            mCurrentLivesTexture = mHudLivesFive;
        }
        else if (mLives == 4) {
            mCurrentLivesTexture = mHudLivesFour;
        }
        else if (mLives == 3) {
            mCurrentLivesTexture = mHudLivesThree;
        }
        else if (mLives == 2) {
            mCurrentLivesTexture = mHudLivesTwo;
        }
        else if (mLives == 1) {
            mCurrentLivesTexture = mHudLivesOne;
        }
    }

}