package com.derekentringer.gizmo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.controllers.Controllers;
import com.derekentringer.gizmo.component.screen.LoadingScreen;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.input.InputProcessor;
import com.derekentringer.gizmo.util.log.GLog;

public class Gizmo extends Game {

    private static final String TAG = Gizmo.class.getSimpleName();

    public static AssetManager assetManager;
    public static I18NBundleLoader bundleLoader;

    @Override
    public void create() {
        Controllers.addListener(new InputProcessor());
        Gdx.input.setInputProcessor(new InputProcessor());
        Gdx.input.setCatchBackKey(true);
        Gdx.app.setLogLevel(Constants.LOG_LEVEL);
        Gdx.graphics.setVSync(true);

        //fullscreen causes issues with debugging
        if (!Constants.IS_DEBUG) {
            Gdx.graphics.setDisplayMode(
                    Gdx.graphics.getDesktopDisplayMode().width,
                    Gdx.graphics.getDesktopDisplayMode().height,
                    true
            );
        }

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
        GLog.d(TAG, "pause");
    }

    @Override
    public void resume() {
        GLog.d(TAG, "resume");
    }

}