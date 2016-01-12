package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.screen.GameScreen;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;

public class PauseStage extends Stage {

    private static final String TAG = PauseStage.class.getSimpleName();

    private GameScreen mGameScreen;
    private World mWorld;
    private OrthographicCamera mStartStageCamera;
    private SpriteBatch mSpriteBatch;

    private String mPaused = "paused";

    private BitmapFont mBitmapFont;
    private GlyphLayout layoutPause;
    private String pauseStringDisplay;

    private int centerScreenX = Constants.GAME_WIDTH / 2;
    private int centerScreenY = Constants.GAME_HEIGHT / 2;

    private float fontX;
    private float fontY;

    public PauseStage(GameScreen gameScreen) {
        mGameScreen = gameScreen;
        mStartStageCamera = new OrthographicCamera();
        mStartStageCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mStartStageCamera.update();

        mWorld = WorldUtils.createWorld();
        mSpriteBatch = new SpriteBatch();
        mBitmapFont = Gizmo.assetManager.get("res/font/gizmo.fnt", BitmapFont.class);
        mBitmapFont.getData().setScale(0.3f, 0.3f);

        loadBitmap();
    }

    private void loadBitmap() {
        layoutPause = new GlyphLayout(mBitmapFont, mPaused);
        fontX = centerScreenX - layoutPause.width / 2;
        fontY = centerScreenY;
        pauseStringDisplay = mPaused;
    }

    @Override
    public void draw() {
        super.draw();

        // input
        UserInput.update();
        handleInput();

        mSpriteBatch.setProjectionMatrix(mStartStageCamera.combined);

        mSpriteBatch.enableBlending();
        mSpriteBatch.begin();
            mBitmapFont.draw(mSpriteBatch, pauseStringDisplay, fontX, fontY);
        mSpriteBatch.end();

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // game loop step
        /*Constants.ACCUMULATOR += delta;
        while (Constants.ACCUMULATOR >= delta) {
            mWorld.step(Constants.TIME_STEP, 6, 2);
            Constants.ACCUMULATOR -= Constants.TIME_STEP;
        }*/
    }

    private void handleInput() {
        if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
            mGameScreen.pauseGame();
        }
    }

    public void updateLayout(float gameHeight, float gameWidth) {
        GLog.d(TAG, "updateLayout");

        fontX = gameWidth / 2 - layoutPause.width / 2;
        fontY = gameHeight / 2;

        mStartStageCamera.update();

        /*mHudLivesPosition.x = Math.abs(crop.x) / scale;
        mHudLivesPosition.y = Math.abs(gameHeight - mCurrentLivesTexture.getHeight() * scale - HUD_PADDING * scale) / scale;

        mHudHealthPosition.x = Math.abs(crop.x) / scale;
        mHudHealthPosition.y = Math.abs(gameHeight - mCurrentLivesTexture.getHeight() * scale - mCurrentHealthTexture.getHeight() * scale - HUD_PADDING * scale) / scale;

        GLog.d(TAG, "hudPosition.x: " + mHudHealthPosition.x);
        GLog.d(TAG, "hudPosition.y: " + mHudHealthPosition.y);

        mHudCamera.update();*/
    }

}