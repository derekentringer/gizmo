package com.derekentringer.gizmo.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.util.constant.Constants;

public class HudStage extends Stage {

    private OrthographicCamera mainCamera;

    public HudStage() {
        setupCamera();
    }

    private void setupCamera() {
        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mainCamera.update();
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


}