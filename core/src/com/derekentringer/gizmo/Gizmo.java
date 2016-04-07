package com.derekentringer.gizmo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.derekentringer.gizmo.analytics.Analytics;
import com.derekentringer.gizmo.analytics.util.AnalyticsUtils;
import com.derekentringer.gizmo.component.screen.LoadingScreen;
import com.derekentringer.gizmo.network.RetroFitClient;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.input.InputProcessor;
import com.derekentringer.gizmo.util.log.GLog;

public class Gizmo extends Game {

    private static final String TAG = Gizmo.class.getSimpleName();

    private static I18NBundle mI18NBundleDebug;
    private static I18NBundle mI18NBundle;
    private static RetroFitClient mRetrofitClient;
    private FileHandle mBaseFileHandle;
    private static AssetManager mAssetManager;

    @Override
    public void create() {
        Controllers.addListener(new InputProcessor());
        Gdx.input.setInputProcessor(new InputProcessor());
        Gdx.input.setCatchBackKey(true);
        Gdx.app.setLogLevel(Constants.LOG_LEVEL);
        Gdx.graphics.setVSync(true);

        if (!Constants.IS_DEBUG) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

        mAssetManager = new AssetManager();

        mBaseFileHandle = Gdx.files.internal("i18n/I18NBundle");
        mI18NBundleDebug = I18NBundle.createBundle(mBaseFileHandle, Constants.debugLocale);
        mI18NBundle = I18NBundle.createBundle(mBaseFileHandle);

        AnalyticsUtils.incrementSessionNum();
        Analytics.initialize();

        setScreen(new LoadingScreen(this));
    }

    public static I18NBundle getmI18NBundle() {
        if (Constants.IS_DEBUG) {
            return mI18NBundleDebug;
        }
        else {
            return mI18NBundle;
        }
    }

    public static RetroFitClient getRetrofitClient() {
        if (mRetrofitClient == null) {
            mRetrofitClient = RetroFitClient.Factory.create();
        }
        return mRetrofitClient;
    }

    public static AssetManager getAssetManager() {
        return mAssetManager;
    }

    @Override
    public void dispose () {
        GLog.d(TAG, "dispose");

        Analytics.sendEvent("session_end", null, 0);

        super.dispose();
        this.getScreen().dispose();
        mAssetManager.dispose();
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