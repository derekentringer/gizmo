package com.derekentringer.gizmo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.derekentringer.gizmo.analytics.Analytics;
import com.derekentringer.gizmo.analytics.AnalyticsSettings;
import com.derekentringer.gizmo.analytics.EventFieldsDictionary;
import com.derekentringer.gizmo.analytics.util.AnalyticsInfo;
import com.derekentringer.gizmo.component.screen.LoadingScreen;
import com.derekentringer.gizmo.network.RetroFitClient;
import com.derekentringer.gizmo.network.request.EventRequest;
import com.derekentringer.gizmo.network.request.InitRequest;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.input.InputProcessor;
import com.derekentringer.gizmo.util.log.GLog;


public class Gizmo extends Game {

    private static final String TAG = Gizmo.class.getSimpleName();

    public static AssetManager assetManager;

    private FileHandle baseFileHandle;
    private static I18NBundle i18NBundleDebug;
    private static I18NBundle i18NBundle;

    private static RetroFitClient mRetrofitClient;

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

        assetManager = new AssetManager();

        baseFileHandle = Gdx.files.internal("i18n/I18NBundle");
        i18NBundleDebug = I18NBundle.createBundle(baseFileHandle, Constants.debugLocale);
        i18NBundle = I18NBundle.createBundle(baseFileHandle);

        Analytics.initialize(new InitRequest(AnalyticsInfo.getPlatform(),
                AnalyticsInfo.getOsVersion(),
                AnalyticsSettings.REST_API_VERSION));

        EventFieldsDictionary.initiate();

        Analytics.sendEvent(new EventRequest("user", EventFieldsDictionary.getDictionary()));

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

    public static RetroFitClient getRetrofitClient() {
        if (mRetrofitClient == null) {
            mRetrofitClient = RetroFitClient.Factory.create();
        }
        return mRetrofitClient;
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