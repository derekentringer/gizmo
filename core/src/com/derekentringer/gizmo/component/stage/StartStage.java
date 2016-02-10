package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class StartStage extends BaseStage {

    public static final String TAG = StartStage.class.getSimpleName();

    private final ArrayList<BaseActor> mStartStageActorsArray = new ArrayList<BaseActor>();

    private static final String SCREEN_STATE_START = "SCREEN_STATE_START";
    private static final String SCREEN_STATE_CONTINUE = "SCREEN_STATE_CONTINUE";

    private static final String SELECTION_STATE_CONTINUE = "SELECTION_STATE_CONTINUE";
    private static final String SELECTION_STATE_RESTART = "SELECTION_STATE_RESTART";
    private static final String SELECTION_STATE_CONTROLS = "SELECTION_STATE_CONTROLS";

    private StartScreen mStartScreen;

    private PlayerModel mPlayerModel;

    private String mStart = Gizmo.getI18NBundle().get("startStage_start");
    private String mContinue = Gizmo.getI18NBundle().get("startStage_continue");
    private String mControls = Gizmo.getI18NBundle().get("startStage_controls");
    private String mRestart = Gizmo.getI18NBundle().get("startStage_restart");

    private GlyphLayout layoutStart;
    private GlyphLayout layoutRestart;
    private GlyphLayout layoutControls;

    private String startStringDisplay;
    private String restartStringDisplay;
    private String controlsStringDisplay;

    private String SCREEN_STATE;
    private String SELECTION_STATE;

    private float fontXStart;
    private float fontXRestart;
    private float fontXControls;

    private boolean toggleSelectionFlag = false;

    private WhiteDotActor whiteDotStart;
    private WhiteDotActor whiteDotContinue;
    private WhiteDotActor whiteDotRestart;
    private WhiteDotActor whiteDotControls;

    public StartStage(StartScreen startScreen) {
        mStartScreen = startScreen;
        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();

        mBitmapFont.getData().setScale(0.3f, 0.3f);

        loadPlayerHearts();
    }

    private void loadPlayerHearts() {
        if (LocalDataManager.loadPlayerActorData() != null) {
            SCREEN_STATE = SCREEN_STATE_CONTINUE;
            SELECTION_STATE = SELECTION_STATE_CONTINUE;
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
        layoutStart = new GlyphLayout(mBitmapFont, mStart);
        fontXStart = centerScreenX - layoutStart.width / 2;
        startStringDisplay = mStart;

        layoutControls = new GlyphLayout(mBitmapFont, mControls);
        fontXControls = centerScreenX - layoutControls.width / 2;
        controlsStringDisplay = mControls;

        createWhiteDotStart();
    }

    private void addContinueText() {
        layoutStart = new GlyphLayout(mBitmapFont, mContinue);
        fontXStart = centerScreenX - layoutStart.width / 2;
        startStringDisplay = mContinue;

        layoutRestart = new GlyphLayout(mBitmapFont, mRestart);
        fontXRestart = centerScreenX - layoutRestart.width / 2;
        restartStringDisplay = mRestart;

        layoutControls = new GlyphLayout(mBitmapFont, mControls);
        fontXControls = centerScreenX - layoutControls.width / 2;
        controlsStringDisplay = mControls;

        createWhiteDotContinue();
    }

    private void createWhiteDotStart() {
        whiteDotStart = new WhiteDotActor(ObjectUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(fontXStart - 10, 41)));
        whiteDotStart.setName(WhiteDotModel.WHITE_DOT);
        addActor(whiteDotStart);
        mStartStageActorsArray.add(whiteDotStart);
    }

    private void createWhiteDotContinue() {
        whiteDotContinue = new WhiteDotActor(ObjectUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(fontXStart - 10, 56)));
        whiteDotContinue.setName(WhiteDotModel.WHITE_DOT);
        addActor(whiteDotContinue);
        mStartStageActorsArray.add(whiteDotContinue);
    }

    private void createWhiteDotRestart() {
        whiteDotRestart = new WhiteDotActor(ObjectUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(fontXRestart - 10, 41)));
        whiteDotRestart.setName(WhiteDotModel.WHITE_DOT);
        addActor(whiteDotRestart);
        mStartStageActorsArray.add(whiteDotRestart);
    }

    private void createWhiteDotControls() {
        whiteDotControls = new WhiteDotActor(ObjectUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(fontXControls - 10, 26)));
        whiteDotControls.setName(WhiteDotModel.WHITE_DOT);
        addActor(whiteDotControls);
        mStartStageActorsArray.add(whiteDotControls);
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
                mBitmapFont.draw(mSpriteBatch, startStringDisplay, fontXStart, 60);
                mBitmapFont.draw(mSpriteBatch, restartStringDisplay, fontXRestart, 45);
            }
            else {
                mBitmapFont.draw(mSpriteBatch, startStringDisplay, fontXStart, 45);
            }
        mBitmapFont.draw(mSpriteBatch, controlsStringDisplay, fontXControls, 30);
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

    //TODO this can be put into BaseStage
    //use an interface to make the callbacks
    private void handleInput() {
        if (SCREEN_STATE.equalsIgnoreCase(SCREEN_STATE_CONTINUE)) {
            if (UserInput.isDown(UserInput.UP) || UserInput.isDown(UserInput.DOWN)) {
                setSelection(UserInput.isDown(UserInput.UP));
            }
            if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
                if (mStartStageActorsArray.contains(whiteDotContinue)) {
                    mStartScreen.startGame();
                }
                else if (mStartStageActorsArray.contains(whiteDotRestart)) {
                    LocalDataManager.resetAllPlayerData();
                    mStartScreen.startGame();
                }
                else if (mStartStageActorsArray.contains(whiteDotControls)) {
                    mStartScreen.viewGameControls();
                }
            }
        }
        else {
            if (UserInput.isDown(UserInput.UP) || UserInput.isDown(UserInput.DOWN)) {
                toggleSelection();
            }
            if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
                if (mStartStageActorsArray.contains(whiteDotStart)) {
                    mStartScreen.startGame();
                }
                else {
                    mStartScreen.viewGameControls();
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
            if (mStartStageActorsArray.contains(whiteDotStart)) {
                mStartStageActorsArray.remove(whiteDotStart);
                whiteDotStart.remove();
                createWhiteDotControls();
            }
            else {
                mStartStageActorsArray.remove(whiteDotControls);
                whiteDotControls.remove();
                createWhiteDotStart();
            }
        }
    }

    private void setSelection(boolean upPressed) {
        if (toggleSelectionFlag == false) {
            toggleSelectionFlag = true;
            if (SELECTION_STATE.equals(SELECTION_STATE_CONTINUE)) {
                if (mStartStageActorsArray.contains(whiteDotContinue)) {
                    mStartStageActorsArray.remove(whiteDotContinue);
                    whiteDotContinue.remove();
                }
                if (upPressed) {
                    SELECTION_STATE = SELECTION_STATE_CONTROLS;
                    createWhiteDotControls();
                }
                else {
                    SELECTION_STATE = SELECTION_STATE_RESTART;
                    createWhiteDotRestart();
                }
            }
            else if (SELECTION_STATE.equals(SELECTION_STATE_RESTART)) {
                if (mStartStageActorsArray.contains(whiteDotRestart)) {
                    mStartStageActorsArray.remove(whiteDotRestart);
                    whiteDotRestart.remove();
                }
                if (upPressed) {
                    SELECTION_STATE = SELECTION_STATE_CONTINUE;
                    createWhiteDotContinue();
                }
                else {
                    SELECTION_STATE = SELECTION_STATE_CONTROLS;
                    createWhiteDotControls();
                }
            }
            else if (SELECTION_STATE.equals(SELECTION_STATE_CONTROLS)) {
                if (mStartStageActorsArray.contains(whiteDotControls)) {
                    mStartStageActorsArray.remove(whiteDotControls);
                    whiteDotControls.remove();
                }
                if (upPressed) {
                    SELECTION_STATE = SELECTION_STATE_RESTART;
                    createWhiteDotRestart();
                }
                else {
                    SELECTION_STATE = SELECTION_STATE_CONTINUE;
                    createWhiteDotContinue();
                }
            }
        }
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
    }

}