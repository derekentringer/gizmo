package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.misc.WhiteDotActor;
import com.derekentringer.gizmo.component.screen.ControlsScreen;
import com.derekentringer.gizmo.model.misc.WhiteDotModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.ObjectUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class ControlsStage extends BaseStage {

    private static final String TAG = ControlsStage.class.getSimpleName();

    private final ArrayList<BaseActor> mControlsStageActorsArray = new ArrayList<BaseActor>();

    private static final String SELECTION_STATE_CONTROLLER = "SELECTION_STATE_CONTROLLER";
    private static final String SELECTION_STATE_KEYBOARD = "SELECTION_STATE_KEYBOARD";
    private static final String SELECTION_STATE_BACK = "SELECTION_STATE_BACK";

    private String SELECTION_STATE;

    private ControlsScreen mControlsScreen;
    private Texture mController;
    private Texture mCurrentTexture;

    private String mControllerControls = Gizmo.getI18NBundle().get("controlsStage_controller");
    private String mKeyboardControls = Gizmo.getI18NBundle().get("controlsStage_keyboard");
    private String mBack = Gizmo.getI18NBundle().get("controlsStage_back");

    private GlyphLayout mLayoutControllerControls;
    private GlyphLayout mLayoutKeyboardControls;
    private GlyphLayout mLayoutBack;

    private String mControllerControlsString;
    private String mKeyboardControlsString;
    private String mBackString;

    private float mFontXControllerControls;
    private float mFontXKeyboardControls;
    private float mFontXBack;

    private WhiteDotActor mWhiteDotControllerControls;
    private WhiteDotActor mWhiteDotKeyboardControls;
    private WhiteDotActor mWhiteDotBack;

    private boolean mToggleSelectionFlag = false;

    public ControlsStage(ControlsScreen controlsScreen) {
        mControlsScreen = controlsScreen;
        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();
        mBitmapFont.getData().setScale(0.3f, 0.3f);

        mController = Gizmo.assetManager.get("res/image/controller/controller.png", Texture.class);

        mCurrentTexture = mController;

        SELECTION_STATE = SELECTION_STATE_CONTROLLER;

        setupMenu();
    }

    private void setupMenu() {
        mLayoutControllerControls = new GlyphLayout(mBitmapFont, mControllerControls);
        mFontXControllerControls = centerScreenX - mLayoutControllerControls.width / 2;
        mControllerControlsString = mControllerControls;

        mLayoutKeyboardControls = new GlyphLayout(mBitmapFont, mKeyboardControls);
        mFontXKeyboardControls = centerScreenX - mLayoutKeyboardControls.width / 2;
        mKeyboardControlsString = mKeyboardControls;

        mLayoutBack = new GlyphLayout(mBitmapFont, mBack);
        mFontXBack = centerScreenX - mLayoutBack.width / 2;
        mBackString = mBack;

        createWhiteDotController();
    }

    @Override
    public void draw() {
        super.draw();

        mSpriteBatch.setProjectionMatrix(mOrthographicCamera.combined);

        for (BaseActor actor : mControlsStageActorsArray) {
            actor.render(mSpriteBatch);
        }

        mSpriteBatch.enableBlending();
        mSpriteBatch.begin();
            mBitmapFont.draw(mSpriteBatch, mControllerControlsString, mFontXControllerControls, 60);
            mBitmapFont.draw(mSpriteBatch, mKeyboardControlsString, mFontXKeyboardControls, 45);
            mBitmapFont.draw(mSpriteBatch, mBackString, mFontXBack, 30);
            mSpriteBatch.draw(mCurrentTexture, centerScreenX - (mController.getWidth() / 2), centerScreenY - (mController.getHeight() / 2));
        mSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // input
        UserInput.update();
        handleInput();

        // actor loops
        for (BaseActor actor : mControlsStageActorsArray) {
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

    private void createWhiteDotController() {
        mWhiteDotControllerControls = new WhiteDotActor(ObjectUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(mFontXControllerControls - 10, 56)));
        mWhiteDotControllerControls.setName(WhiteDotModel.WHITE_DOT);
        addActor(mWhiteDotControllerControls);
        mControlsStageActorsArray.add(mWhiteDotControllerControls);
    }

    private void createWhiteDotKeyboard() {
        mWhiteDotKeyboardControls = new WhiteDotActor(ObjectUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(mFontXKeyboardControls - 10, 41)));
        mWhiteDotKeyboardControls.setName(WhiteDotModel.WHITE_DOT);
        addActor(mWhiteDotKeyboardControls);
        mControlsStageActorsArray.add(mWhiteDotKeyboardControls);
    }

    private void createWhiteDotBack() {
        mWhiteDotBack = new WhiteDotActor(ObjectUtils.createWhiteDot(new WhiteDotModel(), mWorld, new Vector2(mFontXBack - 10, 26)));
        mWhiteDotBack.setName(WhiteDotModel.WHITE_DOT);
        addActor(mWhiteDotBack);
        mControlsStageActorsArray.add(mWhiteDotBack);
    }

    //TODO this can be put into BaseStage
    //use an interface to make the callbacks
    private void handleInput() {
        if (UserInput.isDown(UserInput.UP) || UserInput.isDown(UserInput.DOWN)) {
            setSelection(UserInput.isDown(UserInput.UP));
        }
        if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
            if (mControlsStageActorsArray.contains(mWhiteDotControllerControls)) {
                //TODO switch mCurrentTexture
            }
            else if (mControlsStageActorsArray.contains(mWhiteDotKeyboardControls)) {
                //TODO switch mCurrentTexture
            }
            else if (mControlsStageActorsArray.contains(mWhiteDotBack)) {
                mControlsScreen.goBackToStartScreen();
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
            if (SELECTION_STATE.equals(SELECTION_STATE_CONTROLLER)) {
                if (mControlsStageActorsArray.contains(mWhiteDotControllerControls)) {
                    mControlsStageActorsArray.remove(mWhiteDotControllerControls);
                    mWhiteDotControllerControls.remove();
                }
                if (upPressed) {
                    SELECTION_STATE = SELECTION_STATE_BACK;
                    createWhiteDotBack();
                }
                else {
                    SELECTION_STATE = SELECTION_STATE_KEYBOARD;
                    createWhiteDotKeyboard();
                }
            }
            else if (SELECTION_STATE.equals(SELECTION_STATE_KEYBOARD)) {
                if (mControlsStageActorsArray.contains(mWhiteDotKeyboardControls)) {
                    mControlsStageActorsArray.remove(mWhiteDotKeyboardControls);
                    mWhiteDotKeyboardControls.remove();
                }
                if (upPressed) {
                    SELECTION_STATE = SELECTION_STATE_CONTROLLER;
                    createWhiteDotController();
                }
                else {
                    SELECTION_STATE = SELECTION_STATE_BACK;
                    createWhiteDotBack();
                }
            }
            else if (SELECTION_STATE.equals(SELECTION_STATE_BACK)) {
                if (mControlsStageActorsArray.contains(mWhiteDotBack)) {
                    mControlsStageActorsArray.remove(mWhiteDotBack);
                    mWhiteDotBack.remove();
                }
                if (upPressed) {
                    SELECTION_STATE = SELECTION_STATE_KEYBOARD;
                    createWhiteDotKeyboard();
                }
                else {
                    SELECTION_STATE = SELECTION_STATE_CONTROLLER;
                    createWhiteDotController();
                }
            }
        }
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
    }

}