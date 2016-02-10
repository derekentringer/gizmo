package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.Texture;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.screen.ControlsScreen;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;

public class ControlsStage extends BaseStage {

    private static final String TAG = ControlsStage.class.getSimpleName();

    private ControlsScreen mControlsScreen;
    private Texture mController;

    public ControlsStage(ControlsScreen controlsScreen) {
        mControlsScreen = controlsScreen;
        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();
        mBitmapFont.getData().setScale(0.3f, 0.3f);

        mController = Gizmo.assetManager.get("res/image/controller/controller.png", Texture.class);
    }

    @Override
    public void draw() {
        UserInput.update();
        handleInput();

        mSpriteBatch.setProjectionMatrix(mOrthographicCamera.combined);

        mSpriteBatch.enableBlending();
        mSpriteBatch.begin();
            mSpriteBatch.draw(mController, centerScreenX - (mController.getWidth() / 2), centerScreenY - (mController.getHeight() / 2));
        mSpriteBatch.end();
    }

    private void handleInput() {
        if (UserInput.isDown(UserInput.JUMP_BUTTON)
                && !UserInput.isPressed(UserInput.JUMP_BUTTON)) {
            mControlsScreen.goBackToStartScreen();
        }
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
    }

}