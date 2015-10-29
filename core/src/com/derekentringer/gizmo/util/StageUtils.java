package com.derekentringer.gizmo.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.derekentringer.gizmo.component.stage.interfaces.IHudStage;
import com.derekentringer.gizmo.settings.Constants;

import java.util.ArrayList;

public class StageUtils {

    private static final String TAG = StageUtils.class.getSimpleName();

    public static final String FADE_STATUS_OUT = "FADE_STATUS_OUT";
    public static final String FADE_STATUS_IN = "FADE_STATUS_IN";
    public static final String FADE_STATUS_COMPLETE = "FADE_STATUS_COMPLETE";

    private static final float TIME_TO_FADE = 1;
    private static final int FADE_DELAY = 900;

    private ShapeRenderer transitionShapeRenderer;

    private float timeAccumulated;
    private float newAlpha;
    private boolean isFadeInAlreadyRun;
    private boolean showFadeTransition;

    private String currentFadeStatus = FADE_STATUS_IN;

    public void setProjectionMatrix(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
    }

    public void fadeStageLights(float delta, ShapeRenderer shapeRenderer, ArrayList<IHudStage> listeners, String doorType) {
        transitionShapeRenderer = shapeRenderer;

        // fade out
        if (currentFadeStatus.equalsIgnoreCase(FADE_STATUS_OUT)) {
            timeAccumulated += delta;
            newAlpha = 1 - (timeAccumulated / TIME_TO_FADE);
            if (newAlpha < 0) {
                newAlpha = 0;
            }
            drawOverlay(0, 0, 0, newAlpha, transitionShapeRenderer);
            if (newAlpha <= 0) {
                timeAccumulated = 0;
                currentFadeStatus = FADE_STATUS_COMPLETE;
            }
        }

        // fade in
        else if (currentFadeStatus.equalsIgnoreCase(FADE_STATUS_IN)) {
            timeAccumulated += delta;
            newAlpha += (timeAccumulated / TIME_TO_FADE);
            if (newAlpha > 1) {
                newAlpha = 1;
            }
            drawOverlay(0, 0, 0, newAlpha, transitionShapeRenderer);
            if (newAlpha >= 1 && !isFadeInAlreadyRun) {
                isFadeInAlreadyRun = true; //this is the PROBLEM
                //fire off listener to load new level
                for(IHudStage listener : listeners){
                    listener.hudFadeInComplete(doorType);
                }
                startFadeDelay();
            }
        }

        // fade complete
        if (currentFadeStatus.equalsIgnoreCase(FADE_STATUS_COMPLETE)) {
            //GLog.d(TAG, "fading complete");
            showFadeTransition = false;
            isFadeInAlreadyRun = false;
        }
    }

    private void drawOverlay(float r, float g, float b, float a, ShapeRenderer shapeRenderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(r, g, b, a);
        shapeRenderer.rect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        shapeRenderer.end();
    }

    private void startFadeDelay() {
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(FADE_DELAY);
                }
                catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                finally {
                    currentFadeStatus = FADE_STATUS_OUT;
                }
            }
        };
        t.start();
    }

    public boolean getShowTransition() {
        return showFadeTransition;
    }

    public void setShowTransitions(boolean fadeTransitions) {
        showFadeTransition = fadeTransitions;
    }

    public String getFadeStatus() {
        return currentFadeStatus;
    }

    public void setFadeStatus(String fadeStatus) {
        currentFadeStatus = fadeStatus;
    }

}