package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.object.HeartActor;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.ObjectUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class StartStage extends Stage {

    public static final String TAG = StartStage.class.getSimpleName();

    private final ArrayList<BaseActor> mStartStageActorsArray = new ArrayList<BaseActor>();

    private World mWorld;
    private OrthographicCamera mStartStageCamera;
    private SpriteBatch mSpriteBatch;

    public StartStage() {
        mStartStageCamera = new OrthographicCamera();
        mStartStageCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mStartStageCamera.update();

        mWorld = WorldUtils.createWorld();

        mSpriteBatch = new SpriteBatch();

        loadStartStageActors();
    }

    private void loadStartStageActors() {


        HeartActor heartActor = new HeartActor(ObjectUtils.createHeart(new HeartModel(), mWorld, new Vector2(Constants.GAME_WIDTH / 2, Constants.GAME_HEIGHT / 2)));
        heartActor.setName(HeartModel.HEART);
        addActor(heartActor);
        mStartStageActorsArray.add(heartActor);
    }

    @Override
    public void draw() {
        super.draw();

        mSpriteBatch.setProjectionMatrix(mStartStageCamera.combined);

        for (BaseActor actor : mStartStageActorsArray) {
            actor.render(mSpriteBatch);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // input
        //UserInput.update();
        //handleInput();

        // actor loops
        for (BaseActor actor : mStartStageActorsArray) {
            actor.update(delta);
            actor.act(delta);
        }

        // game loop step
        Constants.ACCUMULATOR += delta;
        while (Constants.ACCUMULATOR >= delta) {
            mWorld.step(Constants.TIME_STEP, 6, 2);
            Constants.ACCUMULATOR -= Constants.TIME_STEP;
        }
    }

    public void updateStartScreen(Float scale, Vector2 crop, float gameWidth, float gameHeight) {
        GLog.d(TAG, "updateStartScreen");

        //mHeartTexturePosition.x = ((gameWidth / 2) - (mHeartTexture.getWidth() / 2)) / scale;
        //mHeartTexturePosition.y = (gameHeight / 2 - mHeartTexture.getHeight() / 2) / scale - Math.abs(crop.y);

        /*GLog.d(TAG, "mHeartTexture.getWidth: " + mHeartTexture.getWidth());
        GLog.d(TAG, "mHeartTexture.getHeight: " + mHeartTexture.getHeight());
        GLog.d(TAG, "mHeartTexturePosition.x: " + mHeartTexturePosition.x);
        GLog.d(TAG, "mHeartTexturePosition.y: " + mHeartTexturePosition.y);*/

        mStartStageCamera.update();
    }

}