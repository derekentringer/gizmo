package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.derekentringer.gizmo.component.screen.CutScreen;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.input.UserInput;

public class CutStage extends BaseStage {

    private CutScreen mCutScreen;

    private ShapeRenderer mBackground;
    private boolean mToggleSelectionFlag;

    public CutStage(CutScreen cutScreen) {
        mCutScreen = cutScreen;

        mBackground = new ShapeRenderer();
        
        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();
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
    }

    private void handleInput() {
        if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
            if (mToggleSelectionFlag) {
                mCutScreen.onContinueToGame();
            }
        }

        //reset toggle flag
        if (!UserInput.isDown(UserInput.JUMP_BUTTON)) {
            mToggleSelectionFlag = true;
        }
    }

}