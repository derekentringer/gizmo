package com.derekentringer.gizmo.component.stage;

import com.derekentringer.gizmo.component.screen.ControlsScreen;
import com.derekentringer.gizmo.settings.Constants;

public class ControlsStage extends BaseStage {

    private ControlsScreen mControlsScreen;

    public ControlsStage(ControlsScreen controlsScreen) {
        mControlsScreen = controlsScreen;
        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();
        mBitmapFont.getData().setScale(0.3f, 0.3f);
    }

}