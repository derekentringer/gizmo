package com.derekentringer.gizmo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controllers;
import com.derekentringer.gizmo.component.screen.LoadingScreen;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.input.InputProcessor;
import com.derekentringer.gizmo.util.log.GLog;

public class Gizmo extends Game {

    private static final String TAG = Gizmo.class.getSimpleName();

    public static AssetManager assetManager;

    @Override
    public void create() {
        Controllers.addListener(new InputProcessor());
        Gdx.input.setInputProcessor(new InputProcessor());
        Gdx.app.setLogLevel(Constants.LOG_LEVEL);
        assetManager = new AssetManager();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose () {
        GLog.d(TAG, "dispose");
        super.dispose();
        this.getScreen().dispose();
        assetManager.dispose();
    }

    @Override
    public void pause () {
        GLog.d(TAG, "dispose");
    }

    @Override
    public void resume() {
        GLog.d(TAG, "dispose");
    }

}