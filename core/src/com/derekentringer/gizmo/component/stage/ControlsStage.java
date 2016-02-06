package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.screen.ControlsScreen;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.WorldUtils;

public class ControlsStage extends BaseStage {

    private ControlsScreen mControlsScreen;

    public ControlsStage(ControlsScreen controlsScreen) {
        mControlsScreen = controlsScreen;
        mOrthographicCamera = new OrthographicCamera();
        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();

        mWorld = WorldUtils.createWorld();
        mSpriteBatch = new SpriteBatch();
        mBitmapFont = Gizmo.assetManager.get("res/font/gizmo.fnt", BitmapFont.class);
        mBitmapFont.getData().setScale(0.3f, 0.3f);
    }

}