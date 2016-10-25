package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.menu.TitleActor;
import com.derekentringer.gizmo.component.actor.menu.WhiteDotActor;
import com.derekentringer.gizmo.component.actor.object.HeartActor;
import com.derekentringer.gizmo.component.screen.StartScreen;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.model.menu.TitleModel;
import com.derekentringer.gizmo.model.menu.WhiteDotModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.HudUtils;
import com.derekentringer.gizmo.util.ObjectUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class StartStage extends BaseStage {

    public static final String TAG = StartStage.class.getSimpleName();

    private final ArrayList<BaseActor> mStartStageActorsArray = new ArrayList<BaseActor>();

    private static final String SCREEN_STATE_START = "SCREEN_STATE_START";
    private static final String SCREEN_STATE_CONTINUE = "SCREEN_STATE_CONTINUE";

    private static final String SELECTION_STATE_START = "SELECTION_STATE_START";
    private static final String SELECTION_STATE_CONTINUE = "SELECTION_STATE_CONTINUE";
    private static final String SELECTION_STATE_RESTART = "SELECTION_STATE_RESTART";
    private static final String SELECTION_STATE_CONTROLS = "SELECTION_STATE_CONTROLS";
    private static final String SELECTION_STATE_ABOUT = "SELECTION_STATE_ABOUT";

    private String SCREEN_STATE;
    private String SELECTION_STATE;

    private StartScreen mStartScreen;

    private PlayerModel mPlayerModel;

    private String mStart = Gizmo.getmI18NBundle().get("startStage_start");
    private String mContinue = Gizmo.getmI18NBundle().get("startStage_continue");
    private String mControls = Gizmo.getmI18NBundle().get("startStage_controls");
    private String mRestart = Gizmo.getmI18NBundle().get("startStage_restart");
    private String mAbout = Gizmo.getmI18NBundle().get("startStage_about");

    private GlyphLayout mLayoutStart;
    private GlyphLayout mLayoutRestart;
    private GlyphLayout mLayoutControls;
    private GlyphLayout mLayoutAbout;

    private String mStartStringDisplay;
    private String mRestartStringDisplay;
    private String mControlsStringDisplay;
    private String mAboutStringDisplay;

    private float mFontXStart;
    private float mFontXRestart;
    private float mFontXControls;
    private float mFontXAbout;

    private boolean mToggleSelectionFlag = false;

    private WhiteDotActor mWhiteDotStart;
    private WhiteDotActor mWhiteDotContinue;
    private WhiteDotActor mWhiteDotRestart;
    private WhiteDotActor mWhiteDotControls;
    private WhiteDotActor mWhiteDotAbout;

    public StartStage(StartScreen startScreen) {
        mStartScreen = startScreen;
        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();

        mBitmapFont.getData().setScale(0.3f, 0.3f);

        if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
            UserInput.resetKey(UserInput.JUMP_BUTTON, false);
        }

        loadTitleImage();
        loadPlayerHearts();
    }

    private void loadTitleImage() {
        TitleActor titleActor = new TitleActor(HudUtils.createTitle(new TitleModel(), mWorld, new Vector2(centerScreenX, centerScreenY + 20)));
        titleActor.setName(TitleModel.TITLE_IMAGE);
        addActor(titleActor);
        mStartStageActorsArray.add(titleActor);
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
            SELECTION_STATE = SELECTION_STATE_START;
            displayHearts(PlayerModel.DEFAULT_HEARTS);
            addStartText();
        }
    }

    private void displayHearts(int hearts) {
        int heartsTotalWidth = hearts * 10;
        int heartsPositionX = screenWidth / 2 - heartsTotalWidth / 2;
        for (int i=0; i < hearts; i++) {
            HeartActor heartActor = new HeartActor(ObjectUtils.createHeart(new HeartModel(), mWorld, new Vector2(heartsPositionX + (i * 20), centerScreenY - 70)));
            heartActor.setName(HeartModel.HEART);
            addActor(heartActor);
            mStartStageActorsArray.add(heartActor);
        }
    }

    private void addStartText() {
        mLayoutStart = new GlyphLayout(mBitmapFont, mStart);
        mFontXStart = centerScreenX - mLayoutStart.width / 2;
        mStartStringDisplay = mStart;

        mLayoutControls = new GlyphLayout(mBitmapFont, mControls);
        mFontXControls = centerScreenX - mLayoutControls.width / 2;
        mControlsStringDisplay = mControls;

        mLayoutAbout = new GlyphLayout(mBitmapFont, mAbout);
        mFontXAbout = centerScreenX - mLayoutAbout.width / 2;
        mAboutStringDisplay = mAbout;

        createWhiteDotStart();
    }

    private void addContinueText() {
        mLayoutStart = new GlyphLayout(mBitmapFont, mContinue);
        mFontXStart = centerScreenX - mLayoutStart.width / 2;
        mStartStringDisplay = mContinue;

        mLayoutRestart = new GlyphLayout(mBitmapFont, mRestart);
        mFontXRestart = centerScreenX - mLayoutRestart.width / 2;
        mRestartStringDisplay = mRestart;

        mLayoutControls = new GlyphLayout(mBitmapFont, mControls);
        mFontXControls = centerScreenX - mLayoutControls.width / 2;
        mControlsStringDisplay = mControls;

        mLayoutAbout = new GlyphLayout(mBitmapFont, mAbout);
        mFontXAbout = centerScreenX - mLayoutAbout.width / 2;
        mAboutStringDisplay = mAbout;

        createWhiteDotContinue();
    }

    private void createWhiteDotContinue() {
        mWhiteDotContinue = new WhiteDotActor(HudUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(mFontXStart - 10, 72)));
        mWhiteDotContinue.setName(WhiteDotModel.WHITE_DOT);
        addActor(mWhiteDotContinue);
        mStartStageActorsArray.add(mWhiteDotContinue);
    }

    private void createWhiteDotStart() {
        mWhiteDotStart = new WhiteDotActor(HudUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(mFontXStart - 10, 56)));
        mWhiteDotStart.setName(WhiteDotModel.WHITE_DOT);
        addActor(mWhiteDotStart);
        mStartStageActorsArray.add(mWhiteDotStart);
    }

    private void createWhiteDotRestart() {
        mWhiteDotRestart = new WhiteDotActor(HudUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(mFontXRestart - 10, 56)));
        mWhiteDotRestart.setName(WhiteDotModel.WHITE_DOT);
        addActor(mWhiteDotRestart);
        mStartStageActorsArray.add(mWhiteDotRestart);
    }

    private void createWhiteDotControls() {
        mWhiteDotControls = new WhiteDotActor(HudUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(mFontXControls - 10, 41)));
        mWhiteDotControls.setName(WhiteDotModel.WHITE_DOT);
        addActor(mWhiteDotControls);
        mStartStageActorsArray.add(mWhiteDotControls);
    }

    private void createWhiteDotAbout() {
        mWhiteDotAbout = new WhiteDotActor(HudUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(mFontXAbout - 10, 26)));
        mWhiteDotAbout.setName(WhiteDotModel.WHITE_DOT);
        addActor(mWhiteDotAbout);
        mStartStageActorsArray.add(mWhiteDotAbout);
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
            if(mRestartStringDisplay != "" && mRestartStringDisplay != null) {
                mBitmapFont.draw(mSpriteBatch, mStartStringDisplay, mFontXStart, 75);
                mBitmapFont.draw(mSpriteBatch, mRestartStringDisplay, mFontXRestart, 60);
            }
            else {
                mBitmapFont.draw(mSpriteBatch, mStartStringDisplay, mFontXStart, 60);
            }
            mBitmapFont.draw(mSpriteBatch, mControlsStringDisplay, mFontXControls, 45);
            mBitmapFont.draw(mSpriteBatch, mAboutStringDisplay, mFontXAbout, 30);
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
                if (mStartStageActorsArray.contains(mWhiteDotContinue)) {
                    mStartScreen.startGame();
                }
                else if (mStartStageActorsArray.contains(mWhiteDotRestart)) {
                    LocalDataManager.resetAllPlayerData();
                    mStartScreen.startGame();
                }
                else if (mStartStageActorsArray.contains(mWhiteDotControls)) {
                    mStartScreen.viewGameControls();
                }
                else if (mStartStageActorsArray.contains(mWhiteDotAbout)) {
                    mStartScreen.viewAbout();
                }
            }
        }
        else {
            if (UserInput.isDown(UserInput.UP) || UserInput.isDown(UserInput.DOWN)) {
                setSelection(UserInput.isDown(UserInput.UP));
            }
            if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
                if (mStartStageActorsArray.contains(mWhiteDotStart)) {

                    //TODO show cutscreen/stage
                    mStartScreen.startCutScreen();
                    //mStartScreen.startGame();

                }
                else if (mStartStageActorsArray.contains(mWhiteDotAbout)) {
                    mStartScreen.viewAbout();
                }
                else {
                    mStartScreen.viewGameControls();
                }
            }
        }

        //reset toggle flag
        if (!UserInput.isDown(UserInput.UP)
                && !UserInput.isDown(UserInput.DOWN)) {
            mToggleSelectionFlag = false;
        }
    }

    private void setSelection(boolean upPressed) {
        if (mToggleSelectionFlag == false) {
            mToggleSelectionFlag = true;
            if (SELECTION_STATE.equals(SELECTION_STATE_START)) {
                if (mStartStageActorsArray.contains(mWhiteDotStart)) {
                    mStartStageActorsArray.remove(mWhiteDotStart);
                    mWhiteDotStart.remove();
                }
                if (upPressed) {
                    SELECTION_STATE = SELECTION_STATE_ABOUT;
                    createWhiteDotAbout();
                }
                else {
                    SELECTION_STATE = SELECTION_STATE_CONTROLS;
                    createWhiteDotControls();
                }
            }
            else if (SELECTION_STATE.equals(SELECTION_STATE_CONTINUE)) {
                if (mStartStageActorsArray.contains(mWhiteDotContinue)) {
                    mStartStageActorsArray.remove(mWhiteDotContinue);
                    mWhiteDotContinue.remove();
                }
                if (upPressed) {
                    SELECTION_STATE = SELECTION_STATE_ABOUT;
                    createWhiteDotAbout();
                }
                else {
                    SELECTION_STATE = SELECTION_STATE_RESTART;
                    createWhiteDotRestart();
                }
            }
            else if (SELECTION_STATE.equals(SELECTION_STATE_RESTART)) {
                if (mStartStageActorsArray.contains(mWhiteDotRestart)) {
                    mStartStageActorsArray.remove(mWhiteDotRestart);
                    mWhiteDotRestart.remove();
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
                if (mStartStageActorsArray.contains(mWhiteDotControls)) {
                    mStartStageActorsArray.remove(mWhiteDotControls);
                    mWhiteDotControls.remove();
                }
                if (upPressed) {
                    if (SCREEN_STATE == SCREEN_STATE_START) {
                        SELECTION_STATE = SELECTION_STATE_START;
                        createWhiteDotStart();
                    }
                    else {
                        SELECTION_STATE = SELECTION_STATE_RESTART;
                        createWhiteDotRestart();
                    }
                }
                else {
                    SELECTION_STATE = SELECTION_STATE_ABOUT;
                    createWhiteDotAbout();
                }
            }
            else if (SELECTION_STATE.equals(SELECTION_STATE_ABOUT)) {
                if (mStartStageActorsArray.contains(mWhiteDotAbout)) {
                    mStartStageActorsArray.remove(mWhiteDotAbout);
                    mWhiteDotAbout.remove();
                }
                if (upPressed) {
                    SELECTION_STATE = SELECTION_STATE_CONTROLS;
                    createWhiteDotControls();
                }
                else {
                    if (SCREEN_STATE == SCREEN_STATE_START) {
                        SELECTION_STATE = SELECTION_STATE_START;
                        createWhiteDotStart();
                    }
                    else {
                        SELECTION_STATE = SELECTION_STATE_CONTINUE;
                        createWhiteDotContinue();
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
    }

}