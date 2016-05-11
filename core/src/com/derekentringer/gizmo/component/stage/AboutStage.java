package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.screen.AboutScreen;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.input.UserInput;

public class AboutStage extends BaseStage {

    private AboutScreen mAboutScreen;
    private ShapeRenderer mBackground;
    private Vector2 gameWidthHeight = new Vector2();

    private String mVersionNumber = Constants.VERSION;
    private String mVersion = Gizmo.getmI18NBundle().get("aboutStage_version") + " " + mVersionNumber;

    private GlyphLayout mLayoutAbout;
    private String versionStringDisplay;

    private boolean mToggleSelectionFlag;

    public AboutStage(AboutScreen aboutScreen) {
        mAboutScreen = aboutScreen;
        mBackground = new ShapeRenderer();
        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();

        mBitmapFont.getData().setScale(0.3f, 0.3f);

        gameWidthHeight.x = Constants.GAME_WIDTH;
        gameWidthHeight.y = Constants.GAME_HEIGHT;

        mLayoutAbout = new GlyphLayout(mBitmapFont, mVersion);
        versionStringDisplay = mVersion;
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

        mSpriteBatch.setProjectionMatrix(mOrthographicCamera.combined);

        mSpriteBatch.enableBlending();
        mSpriteBatch.begin();
        mBitmapFont.draw(mSpriteBatch, versionStringDisplay, centerScreenX - mLayoutAbout.width / 2, centerScreenY);
        mSpriteBatch.end();
    }

    private void handleInput() {
        if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
            if (mToggleSelectionFlag) {
                mAboutScreen.goBackToStartScreen();
            }
        }

        //reset toggle flag
        if (!UserInput.isDown(UserInput.JUMP_BUTTON)) {
            mToggleSelectionFlag = true;
        }
    }

}