package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.WorldUtils;

public class BaseStage extends Stage {

    public World mWorld = WorldUtils.createWorld();

    public int gameWidth = Constants.GAME_WIDTH;
    public int gameHeight = Constants.GAME_HEIGHT;

    public int centerScreenX = Constants.GAME_WIDTH / 2;
    public int centerScreenY = Constants.GAME_HEIGHT / 2;
    public int screenWidth = Constants.GAME_WIDTH;

    public Vector2 gameWidthHeight = new Vector2();

    public SpriteBatch mSpriteBatch = new SpriteBatch();
    public OrthographicCamera mOrthographicCamera = new OrthographicCamera();
    public BitmapFont mBitmapFont = Gizmo.getAssetManager().get("res/font/gizmo.fnt", BitmapFont.class);

    public BaseStage() {
        gameWidthHeight.x = gameWidth;
        gameWidthHeight.y = gameHeight;
    }

    public boolean isControllerConnected() {
        return Controllers.getControllers().size > 0;
    }

}