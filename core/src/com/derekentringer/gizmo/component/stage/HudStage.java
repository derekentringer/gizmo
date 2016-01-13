package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

    private ShapeRenderer mRedShapeRendererTop;
    private ShapeRenderer mRedShapeRendererBottom;
    private ShapeRenderer mWhiteShapeRendererTop;
    private ShapeRenderer mWhiteShapeRendererBottom;
    private ShapeRenderer mTransitionShapeRenderer;
    private boolean mProjectionMatrixSet;

    private float mInitialWidth;
    private float mRedShapeWidthTop;
    private float mRedShapeWidthBottom;
    private float mRedShapeHeight = 20;

    private String mDoorType;
    private boolean mShowTransition;
    private String mFadeStatus;
    private float mTimeAccumulated;
    private float mNewAlpha;
    private boolean isFadeInAlreadyRun;

    public HudStage(GameStage gameStage) {
        gameStage.addListener(this);

        mHudCamera = new OrthographicCamera();
        mHudCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mHudCamera.update();

        mRedShapeRendererTop = new ShapeRenderer();
        mRedShapeRendererBottom = new ShapeRenderer();
        mWhiteShapeRendererTop = new ShapeRenderer();
        mWhiteShapeRendererBottom = new ShapeRenderer();
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
            mWhiteShapeRendererTop.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mWhiteShapeRendererBottom.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mRedShapeRendererTop.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mRedShapeRendererBottom.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mTransitionShapeRenderer.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
        }

        mWhiteShapeRendererTop.begin(ShapeRenderer.ShapeType.Filled);
        mWhiteShapeRendererTop.setColor(Color.WHITE);
        mWhiteShapeRendererTop.rect(mHudHealthPosition.x, mHudHealthPosition.y + mCurrentHealthTexture.getHeight() - mRedShapeHeight, mCurrentHealthTexture.getWidth() - 32, mRedShapeHeight);
        mWhiteShapeRendererTop.end();

        if (mHearts > 5) {
            mWhiteShapeRendererBottom.begin(ShapeRenderer.ShapeType.Filled);
            mWhiteShapeRendererBottom.setColor(Color.WHITE);
            mWhiteShapeRendererBottom.rect(mHudHealthPosition.x, mHudHealthPosition.y + mCurrentHealthTexture.getHeight() - mRedShapeHeight*2, mCurrentHealthTexture.getWidth() - 32, mRedShapeHeight);
            mWhiteShapeRendererBottom.end();
        }

        mRedShapeRendererTop.begin(ShapeRenderer.ShapeType.Filled);
        mRedShapeRendererTop.setColor(193 / 255f, 0, 0, 1);
        mRedShapeRendererTop.rect(mHudHealthPosition.x + 3, mHudHealthPosition.y + mCurrentHealthTexture.getHeight() - mRedShapeHeight, mRedShapeWidthTop, mRedShapeHeight);
        mRedShapeRendererTop.end();

        if (mHearts > 5) {
            mRedShapeRendererBottom.begin(ShapeRenderer.ShapeType.Filled);
            mRedShapeRendererBottom.setColor(193 / 255f, 0, 0, 1);
            mRedShapeRendererBottom.rect(mHudHealthPosition.x + 3, mHudHealthPosition.y + mCurrentHealthTexture.getHeight() - mRedShapeHeight*2, mRedShapeWidthBottom, mRedShapeHeight);
            mRedShapeRendererBottom.end();
        }

        mSpriteBatch.begin();
        mSpriteBatch.draw(mCurrentLivesTexture, mHudLivesPosition.x, mHudLivesPosition.y);
        mSpriteBatch.draw(mCurrentHealthTexture, mHudHealthPosition.x, mHudHealthPosition.y);
        mSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (mShowTransition) {
            dimTheStageLights(delta);
        }
    }

    private void dimTheStageLights(float delta) {
        // fade out
        if (mFadeStatus.equalsIgnoreCase(FADE_OUT)) {
            mTimeAccumulated += delta;
            mNewAlpha = 1 - (mTimeAccumulated / TIME_TO_FADE);
            if (mNewAlpha < 0) {
                mNewAlpha = 0;
            }
            drawOverlay(0, 0, 0, mNewAlpha);
            if (mNewAlpha <= 0) {
                mTimeAccumulated = 0;
                mFadeStatus = FADE_COMPLETE;
            }
        }

        // fade in
        else if (mFadeStatus.equalsIgnoreCase(FADE_IN)) {
            mTimeAccumulated += delta;
            mNewAlpha += (mTimeAccumulated / TIME_TO_FADE);
            if (mNewAlpha > 1) {
                mNewAlpha = 1;
            }
            drawOverlay(0, 0, 0, mNewAlpha);
            if (mNewAlpha >= 1 && !isFadeInAlreadyRun) {
                isFadeInAlreadyRun = true; //this is the PROBLEM
                //fire off listener to load new room
                for(IHudStage listener : listeners){
                    listener.hudFadeInComplete(mDoorType);
                }
                startFadeDelay(delta);
            }
        }

        // fade complete
        if (mFadeStatus.equalsIgnoreCase(FADE_COMPLETE)) {
            GLog.d(TAG, "fading complete");
            mShowTransition = false;
            isFadeInAlreadyRun = false;
        }
    }

    private void startFadeDelay(float delta) {
        // this could be used to adjust
        // the fade back in
        //mTimeAccumulated += delta;
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(FADE_DELAY);
                }
                catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                finally {
                    mFadeStatus = FADE_OUT;
                }
            }
        };
        t.start();
    }

    private void drawOverlay(float r, float g, float b, float a) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        mTransitionShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mTransitionShapeRenderer.setColor(r, g, b, a);
        mTransitionShapeRenderer.rect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mTransitionShapeRenderer.end();
    }

    public void setTransition(String doorType, boolean transition) {
        mDoorType = doorType;
        mShowTransition = transition;
        mFadeStatus = FADE_IN;
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

    //TODO this is called twice for some reason
    //TODO when the level loads
    @Override
    public void setHudHealth(int health) {
        GLog.d(TAG, "setHudHealth");

        float fullHealth = mHearts * PlayerModel.HEART_HEALTH_AMOUNT;
        //float fullHealthTop = mHearts * PlayerModel.HEART_HEALTH_AMOUNT;
        //float fullHealthBottom = (mHearts - 5) * PlayerModel.HEART_HEALTH_AMOUNT;

        if (health > 50) {
            float percentFullBottom = (health - 50) / fullHealth;
            float newWidthBottom = percentFullBottom * mInitialWidth;
            mRedShapeWidthTop = mCurrentHealthTexture.getWidth() - 34;
            mRedShapeWidthBottom = getHealthBottom(health, newWidthBottom);
        }
        else {
            float percentFull = health / fullHealth;
            float newWidth = percentFull * mInitialWidth;
            mRedShapeWidthTop = getHealthTop(health, newWidth);
            mRedShapeWidthBottom = 0;
        }
    }

    private float getHealthTop(int health, float newWidth) {
        if ((mHearts >= 5) || mHearts == 5 && health > 29) {
            newWidth = newWidth + 3;
        }
        else if (mHearts == 4 && health > 19) {
            newWidth = newWidth + 2;
        }
        else if (mHearts == 3 && health > 9) {
            newWidth = newWidth + 1;
        }
        return newWidth;
    }

    private float getHealthBottom(int health, float newWidth) {
        if (mHearts == 10 && health > 58) {
            newWidth = newWidth + 3;
        }
        else if (mHearts == 8 && health > 38) {
            newWidth = newWidth + 2;
        }
        else if (mHearts == 6 && health > 18) {
            newWidth = newWidth + 1;
        }
        return newWidth;
    }

    @Override
    public void resetHudShapes() {
        GLog.d(TAG, "resetHudShapes");
        mInitialWidth = mHearts * 18;
        mRedShapeWidthTop = mInitialWidth;
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