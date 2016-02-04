package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.screen.GameScreen;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;

public class PauseStage extends BaseStage {

    private static final String TAG = PauseStage.class.getSimpleName();

    private GameScreen mGameScreen;
    private World mWorld;
    private OrthographicCamera mStartStageCamera;
    private SpriteBatch mSpriteBatch;

    private ShapeRenderer mBackground;

    private Vector2 gameWidthHeight = new Vector2();

    private String mPaused = getI18NBundle().get("pausedStage_paused");

    private BitmapFont mBitmapFont;
    private GlyphLayout layoutPause;
    private String pauseStringDisplay;

    private int centerScreenX = Constants.GAME_WIDTH / 2;
    private int centerScreenY = Constants.GAME_HEIGHT / 2;

    private static final float TIME_TO_FADE = 1;
    private float mTimeAccumulated;
    private float mNewAlpha = 1;

    private enum FadeState {
        FADE_IN,
        FADE_OUT
    }

    FadeState mFadeState = FadeState.FADE_OUT;

    public PauseStage(GameScreen gameScreen) {
        mGameScreen = gameScreen;
        mBackground = new ShapeRenderer();
        mStartStageCamera = new OrthographicCamera();
        mStartStageCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mStartStageCamera.update();

        mWorld = WorldUtils.createWorld();
        mSpriteBatch = new SpriteBatch();
        mBitmapFont = Gizmo.assetManager.get("res/font/gizmo.fnt", BitmapFont.class);
        mBitmapFont.getData().setScale(0.3f, 0.3f);

        gameWidthHeight.x = Constants.GAME_WIDTH;
        gameWidthHeight.y = Constants.GAME_HEIGHT;

        loadBitmap();
    }

    private void loadBitmap() {
        layoutPause = new GlyphLayout(mBitmapFont, mPaused);
        pauseStringDisplay = mPaused;
    }

    @Override
    public void draw() {
        super.draw();

        UserInput.update();
        handleInput();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        mBackground.begin(ShapeRenderer.ShapeType.Filled);
            mBackground.setColor(0, 0, 0, 1);
            mBackground.rect(0, 0, gameWidthHeight.x, gameWidthHeight.y);
        mBackground.end();

        mSpriteBatch.setProjectionMatrix(mStartStageCamera.combined);

        mSpriteBatch.enableBlending();
        mSpriteBatch.begin();
            mBitmapFont.draw(mSpriteBatch, pauseStringDisplay, centerScreenX - layoutPause.width / 2, centerScreenY);
        mSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (mFadeState == FadeState.FADE_IN) {
            fadeIn(delta);
        }
        else {
            fadeOut(delta);
        }
    }

    private void fadeOut(float delta) {
        mTimeAccumulated += delta;
        mNewAlpha = 1 - (mTimeAccumulated / TIME_TO_FADE);
        if (mNewAlpha < 0) {
            mNewAlpha = 0;
        }
        mBitmapFont.setColor(1, 1, 1, mNewAlpha);
        if (mNewAlpha <= 0) {
            mTimeAccumulated = 0;
            mFadeState = FadeState.FADE_IN;
        }
    }

    private void fadeIn(float delta) {
        mTimeAccumulated += delta;
        mNewAlpha += (mTimeAccumulated / TIME_TO_FADE);
        if (mNewAlpha == 1) {
            mNewAlpha = 1;
        }
        mBitmapFont.setColor(1, 1, 1, mNewAlpha);
        if (mNewAlpha >= 1) {
            mTimeAccumulated = 0;
            mFadeState = FadeState.FADE_OUT;
        }
    }

    private void handleInput() {
        if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
            mGameScreen.pauseGame();
        }
    }

    public void updateLayout(float gameHeight, float gameWidth) {
        GLog.d(TAG, "updateLayout");
        gameWidthHeight.x = gameWidth;
        gameWidthHeight.y = gameHeight;
        mStartStageCamera.update();
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
    }

}