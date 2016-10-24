package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.derekentringer.gizmo.component.screen.CutScreen;
import com.derekentringer.gizmo.settings.Constants;

public class CutStage extends BaseStage {

    private CutScreen mCutScreen;

    private GlyphLayout mLayoutCutStageContinue;

    public CutStage(CutScreen cutScreen) {
        mCutScreen = cutScreen;

        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();

        mBitmapFont.getData().setScale(0.3f, 0.3f);

        /*mLayoutCutStageContinue = new GlyphLayout(mBitmapFont, mVersion);
        versionStringDisplay = mVersion;*/
    }


}