package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.derekentringer.gizmo.settings.Constants;

public class BaseStage extends Stage {

    private FileHandle baseFileHandle;
    private I18NBundle i18NBundleDebug;
    private I18NBundle i18NBundle;

    public BaseStage() {
        baseFileHandle = Gdx.files.internal("i18n/I18NBundle");
        i18NBundleDebug = I18NBundle.createBundle(baseFileHandle, Constants.testLocale);
        i18NBundle = I18NBundle.createBundle(baseFileHandle);
    }

    public I18NBundle getI18NBundle() {
        if (Constants.IS_DEBUG) {
            return i18NBundleDebug;
        }
        else {
            return i18NBundle;
        }
    }

}