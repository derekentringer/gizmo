package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.settings.Constants;

public class BaseStage extends Stage {

    public World mWorld;

    public int centerScreenX = Constants.GAME_WIDTH / 2;
    public int centerScreenY = Constants.GAME_HEIGHT / 2;

    public int screenWidth = Constants.GAME_WIDTH;

    public SpriteBatch mSpriteBatch;

    public OrthographicCamera mOrthographicCamera;

    public BitmapFont mBitmapFont;

    public BaseStage() {
    }

}