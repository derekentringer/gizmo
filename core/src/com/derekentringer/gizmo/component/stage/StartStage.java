package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.misc.WhiteDotActor;
import com.derekentringer.gizmo.component.actor.object.HeartActor;
import com.derekentringer.gizmo.component.screen.StartScreen;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.model.misc.WhiteDotModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.ObjectUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class StartStage extends BaseStage {

    public static final String TAG = StartStage.class.getSimpleName();

    private final ArrayList<BaseActor> mStartStageActorsArray = new ArrayList<BaseActor>();

    private static final String SCREEN_STATE_START = "SCREEN_STATE_START";
    private static final String SCREEN_STATE_CONTINUE = "SCREEN_STATE_CONTINUE";

    private StartScreen mStartScreen;

    private PlayerModel mPlayerModel;

    private String mPressAnyKey = Gizmo.getI18NBundle().get("startStage_pressAnyKey");
    private String mPressAnyButton = Gizmo.getI18NBundle().get("startStage_pressAnyButton");
    private String mContinue = Gizmo.getI18NBundle().get("startStage_continue");
    private String mRestart = Gizmo.getI18NBundle().get("startStage_restart");

    private GlyphLayout layoutStartContinue;
    private GlyphLayout layoutRestart;
    private String startStringDisplay;
    private String restartStringDisplay;

    private String SCREEN_STATE;

    private float fontStartX;
    private float fontXRestart;

    private boolean toggleSelectionFlag = false;

    private WhiteDotActor whiteDotContinue;
    private WhiteDotActor whiteDotRestart;

    public StartStage(StartScreen startScreen) {
        mStartScreen = startScreen;
        mOrthographicCamera = new OrthographicCamera();
        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();

        mWorld = WorldUtils.createWorld();
        mSpriteBatch = new SpriteBatch();
        mBitmapFont = Gizmo.assetManager.get("res/font/gizmo.fnt", BitmapFont.class);
        mBitmapFont.getData().setScale(0.3f, 0.3f);

        loadPlayerHearts();
    }

    private void loadPlayerHearts() {
        if (LocalDataManager.loadPlayerActorData() != null) {
            SCREEN_STATE = SCREEN_STATE_CONTINUE;
            mPlayerModel = LocalDataManager.loadPlayerActorData();
            int totalHearts = mPlayerModel.getHearts();
            displayHearts(totalHearts);
            addContinueText();
        }
        else {
            SCREEN_STATE = SCREEN_STATE_START;
            displayHearts(PlayerModel.DEFAULT_HEARTS);
            addStartText();
        }
    }

    private void displayHearts(int hearts) {
        int heartsTotalWidth = hearts * 18;
        int heartsPositionX = screenWidth / 2 - heartsTotalWidth / 2;
        for (int i=0; i < hearts; i++) {
            HeartActor heartActor = new HeartActor(ObjectUtils.createHeart(new HeartModel(), mWorld, new Vector2(heartsPositionX + (i * 20), centerScreenY)));
            heartActor.setName(HeartModel.HEART);
            addActor(heartActor);
            mStartStageActorsArray.add(heartActor);
        }
    }

    private void addStartText() {
        if (isControllerConnected()) {
            layoutStartContinue = new GlyphLayout(mBitmapFont, mPressAnyButton);
            fontStartX = centerScreenX - layoutStartContinue.width / 2;
            startStringDisplay = mPressAnyButton;
        }
        else {
            layoutStartContinue = new GlyphLayout(mBitmapFont, mPressAnyKey);
            fontStartX = centerScreenX - layoutStartContinue.width / 2;
            startStringDisplay = mPressAnyKey;
        }
    }

    private void addContinueText() {
        layoutStartContinue = new GlyphLayout(mBitmapFont, mContinue);
        fontStartX = centerScreenX - layoutStartContinue.width / 2;
        startStringDisplay = mContinue;

        layoutRestart = new GlyphLayout(mBitmapFont, mRestart);
        fontXRestart = centerScreenX - layoutRestart.width / 2;
        restartStringDisplay = mRestart;

        createWhiteDotContinue();
    }

    private void createWhiteDotContinue() {
        whiteDotContinue = new WhiteDotActor(ObjectUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(fontStartX - 10, 42)));
        whiteDotContinue.setName(WhiteDotModel.WHITE_DOT);
        addActor(whiteDotContinue);
        mStartStageActorsArray.add(whiteDotContinue);
    }

    private void createWhiteDotRestart() {
        whiteDotRestart = new WhiteDotActor(ObjectUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(fontXRestart - 10, 22)));
        whiteDotRestart.setName(WhiteDotModel.WHITE_DOT);
        addActor(whiteDotRestart);
        mStartStageActorsArray.add(whiteDotRestart);
    }

    private boolean isControllerConnected() {
        return Controllers.getControllers().size > 0;
    }

    @Override
    public void draw() {
        super.draw();

        mSpriteBatch.setProjectionMatrix(mOrthographicCamera.combined);

        for (BaseActor actor : mStartStageActorsArray) {
            actor.render(mSpriteBatch);
        }

        mSpriteBatch.enableBlending();
        mSpriteBatch.begin();
            if(restartStringDisplay != "" && restartStringDisplay != null) {
                mBitmapFont.draw(mSpriteBatch, startStringDisplay, fontStartX, 45);
                mBitmapFont.draw(mSpriteBatch, restartStringDisplay, fontXRestart, 25);
            }
            else {
                mBitmapFont.draw(mSpriteBatch, startStringDisplay, fontStartX, 25);
            }
        mSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // input
        UserInput.update();
        handleInput();

        // actor loops
        for (BaseActor actor : mStartStageActorsArray) {
            actor.update(delta);
            actor.act(delta);
        }

        // game loop step
        Constants.ACCUMULATOR += delta;
        while (Constants.ACCUMULATOR >= delta) {
            mWorld.step(Constants.TIME_STEP, 6, 2);
            Constants.ACCUMULATOR -= Constants.TIME_STEP;
        }
    }

    private void handleInput() {
        if (SCREEN_STATE.equalsIgnoreCase(SCREEN_STATE_CONTINUE)) {
            if (UserInput.isDown(UserInput.UP) || UserInput.isDown(UserInput.DOWN)) {
                toggleSelection();
            }
            if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
                if (mStartStageActorsArray.contains(whiteDotContinue)) {
                    mStartScreen.startGame();
                }
                else {
                    LocalDataManager.resetAllPlayerData();
                    mStartScreen.startGame();
                }
            }
        }
        else {
            if (isControllerConnected()) {
                if (UserInput.isDown(UserInput.ANY_BUTTON)) {
                    mStartScreen.startGame();
                }
            }
            else {
                if (UserInput.isDown(UserInput.ANY_KEY)) {
                    mStartScreen.startGame();
                }
            }
        }

        //reset toggle flag
        if (!UserInput.isDown(UserInput.UP)
                && !UserInput.isDown(UserInput.DOWN)) {
            toggleSelectionFlag = false;
        }
    }

    private void toggleSelection() {
        if (toggleSelectionFlag == false) {
            toggleSelectionFlag = true;
            if (mStartStageActorsArray.contains(whiteDotContinue)) {
                mStartStageActorsArray.remove(whiteDotContinue);
                whiteDotContinue.remove();
                createWhiteDotRestart();
            }
            else {
                mStartStageActorsArray.remove(whiteDotRestart);
                whiteDotRestart.remove();
                createWhiteDotContinue();
            }
        }
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
    }

}