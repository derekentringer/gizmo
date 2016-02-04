package com.derekentringer.gizmo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.derekentringer.gizmo.component.screen.LoadingScreen;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.input.InputProcessor;
import com.derekentringer.gizmo.util.log.GLog;

public class Gizmo extends Game {

    private static final String TAG = Gizmo.class.getSimpleName();

    public static AssetManager assetManager;

    private FileHandle baseFileHandle;
    private static I18NBundle i18NBundleDebug;
    private static I18NBundle i18NBundle;

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

        baseFileHandle = Gdx.files.internal("i18n/I18NBundle");
        i18NBundleDebug = I18NBundle.createBundle(baseFileHandle, Constants.debugLocale);
        i18NBundle = I18NBundle.createBundle(baseFileHandle);

        setScreen(new LoadingScreen(this));
    }

    public static I18NBundle getI18NBundle() {
        if (Constants.IS_DEBUG) {
            return i18NBundleDebug;
        }
        else {
            return i18NBundle;
        }
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