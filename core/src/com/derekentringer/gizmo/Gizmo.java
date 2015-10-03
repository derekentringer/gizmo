package com.derekentringer.gizmo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controllers;
import com.derekentringer.gizmo.component.screen.LoadingScreen;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.input.InputProcessor;

public class Gizmo extends Game {

    public static AssetManager assetManager = new AssetManager();

    public IGameService mGameService;

    public Gizmo(IGameService gameService) {
        mGameService = gameService;
    }

    @Override
    public void create() {
        Controllers.addListener(new InputProcessor());
        Gdx.input.setInputProcessor(new InputProcessor());
        Gdx.app.setLogLevel(Constants.LOG_LEVEL);
        setScreen(new LoadingScreen(this));
    }

}