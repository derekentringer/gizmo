package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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

    private Vector2 gameWidthHeight = new Vector2();

    private String mPaused = "paused";

    private BitmapFont mBitmapFont;
    private GlyphLayout layoutPause;
    private String pauseStringDisplay;

    private int centerScreenX = Constants.GAME_WIDTH / 2;
    private int centerScreenY = Constants.GAME_HEIGHT / 2;

    public PauseStage(GameScreen gameScreen) {
        mGameScreen = gameScreen;
        //mBackground = new ShapeRenderer();
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
        gameWidthHeight.x = centerScreenX - layoutPause.width / 2;
        gameWidthHeight.y = centerScreenY;
        pauseStringDisplay = mPaused;
    }

    //private ShapeRenderer mBackground;

    @Override
    public void draw() {
        super.draw();

        UserInput.update();
        handleInput();

        /*Gdx.gl.glEnable(GL20.GL_BLEND);
        mBackground.begin(ShapeRenderer.ShapeType.Filled);
        mBackground.setColor(0, 0, 0, 1);
        mBackground.rect(0, 0, gameWidthHeight.x, gameWidthHeight.y);
        mBackground.end();*/

        mSpriteBatch.setProjectionMatrix(mStartStageCamera.combined);

        mSpriteBatch.enableBlending();
        mSpriteBatch.begin();
            mBitmapFont.draw(mSpriteBatch, pauseStringDisplay, gameWidthHeight.x, gameWidthHeight.y);
        mSpriteBatch.end();
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

}