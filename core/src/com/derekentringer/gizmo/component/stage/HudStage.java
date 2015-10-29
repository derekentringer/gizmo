package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.stage.interfaces.IGameStage;
import com.derekentringer.gizmo.component.stage.interfaces.IHudStage;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.StageUtils;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class HudStage extends Stage implements IGameStage {

    private static final String TAG = HudStage.class.getSimpleName();

    private ArrayList<IHudStage> listeners = new ArrayList<IHudStage>();

    private static final int HUD_PADDING = 10;

    private static final float TIME_TO_FADE = 1;
    private static final int FADE_DELAY = 900;
    private static final String FADE_IN = "FADE_IN";
    private static final String FADE_OUT = "FADE_OUT";
    private static final String FADE_COMPLETE = "FADE_COMPLETE";

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
    private ShapeRenderer mTransitionShapeRenderer;
    private boolean mProjectionMatrixSet;

    private float mInitialWidth;
    private float mRedShapeWidth;
    private float mRedShapeHeight = 20;

    private StageUtils mStageUtils = new StageUtils();
    private String mDoorType;

    public HudStage(GameStage gameStage) {
        gameStage.addListener(this);

        mHudCamera = new OrthographicCamera();
        mHudCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mHudCamera.update();

        mRedShapeRenderer = new ShapeRenderer();
        mWhiteShapeRenderer = new ShapeRenderer();
        mTransitionShapeRenderer = new ShapeRenderer();
        mProjectionMatrixSet = false;

        mSpriteBatch = new SpriteBatch();

        mHudLivesOne = Gizmo.assetManager.get("res/image/hud/hud_lives_one.png", Texture.class);
        mHudLivesTwo = Gizmo.assetManager.get("res/image/hud/hud_lives_two.png", Texture.class);
        mHudLivesThree = Gizmo.assetManager.get("res/image/hud/hud_lives_three.png", Texture.class);
        mHudLivesFour = Gizmo.assetManager.get("res/image/hud/hud_lives_four.png", Texture.class);
        mHudLivesFive = Gizmo.assetManager.get("res/image/hud/hud_lives_five.png", Texture.class);

        mHudHeartsTwo = Gizmo.assetManager.get("res/image/hud/hud_hearts_two.png", Texture.class);
        mHudHeartsThree = Gizmo.assetManager.get("res/image/hud/hud_hearts_three.png", Texture.class);
        mHudHeartsFour = Gizmo.assetManager.get("res/image/hud/hud_hearts_four.png", Texture.class);
        mHudHeartsFive = Gizmo.assetManager.get("res/image/hud/hud_hearts_five.png", Texture.class);
        mHudHeartsSix = Gizmo.assetManager.get("res/image/hud/hud_hearts_six.png", Texture.class);
        mHudHeartsSeven = Gizmo.assetManager.get("res/image/hud/hud_hearts_seven.png", Texture.class);
        mHudHeartsEight = Gizmo.assetManager.get("res/image/hud/hud_hearts_eight.png", Texture.class);
        mHudHeartsNine = Gizmo.assetManager.get("res/image/hud/hud_hearts_nine.png", Texture.class);
        mHudHeartsTen = Gizmo.assetManager.get("res/image/hud/hud_hearts_ten.png", Texture.class);

        mCurrentLivesTexture = mHudLivesOne;
        mCurrentHealthTexture = mHudHeartsTwo;
    }

    public void addListener(IHudStage listener) {
        listeners.add(listener);
    }

    @Override
    public void draw() {
        super.draw();

        mSpriteBatch.setProjectionMatrix(mHudCamera.combined);

        if (!mProjectionMatrixSet) {
            mWhiteShapeRenderer.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mRedShapeRenderer.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mStageUtils.setProjectionMatrix(mTransitionShapeRenderer, mSpriteBatch);
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
        if (mStageUtils.getShowTransition()) {
            mStageUtils.fadeStageLights(delta, mTransitionShapeRenderer, listeners, mDoorType);
        }
    }

    public void setTransition(String doorType, boolean transition) {
        mDoorType = doorType;
        mStageUtils.setShowTransitions(transition);
        mStageUtils.setFadeStatus(mStageUtils.FADE_STATUS_IN);
    }

    public void updateHudLayout(Float scale, Vector2 crop, float gameHeight) {
        GLog.d(TAG, "updateHudLayout");
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
        GLog.d(TAG, "setHudHealthHearts");
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
        GLog.d(TAG, "setHudHealth");
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
        GLog.d(TAG, "resetHudShapes");
        mInitialWidth = mHearts * 18;
        mRedShapeWidth = mInitialWidth;
        mRedShapeHeight = 20;
    }

    @Override
    public void setHudLives(int lives) {
        GLog.d(TAG, "setHudLives");
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

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
    }

}