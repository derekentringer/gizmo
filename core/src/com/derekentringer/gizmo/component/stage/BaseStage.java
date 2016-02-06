package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.WorldUtils;

public class BaseStage extends Stage {

    public World mWorld = WorldUtils.createWorld();

    public int centerScreenX = Constants.GAME_WIDTH / 2;
    public int centerScreenY = Constants.GAME_HEIGHT / 2;
    public int screenWidth = Constants.GAME_WIDTH;

    public SpriteBatch mSpriteBatch = new SpriteBatch();
    public OrthographicCamera mOrthographicCamera = new OrthographicCamera();
    public BitmapFont mBitmapFont = Gizmo.assetManager.get("res/font/gizmo.fnt", BitmapFont.class);

    public BaseStage() {
    }

    public boolean isControllerConnected() {
        return Controllers.getControllers().size > 0;
    }

}