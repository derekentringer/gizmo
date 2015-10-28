package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.object.HeartActor;
import com.derekentringer.gizmo.component.screen.StartScreen;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.ObjectUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class StartStage extends Stage {

    public static final String TAG = StartStage.class.getSimpleName();

    private final ArrayList<BaseActor> mStartStageActorsArray = new ArrayList<BaseActor>();

    private StartScreen mStartScreen;
    private World mWorld;
    private OrthographicCamera mStartStageCamera;
    private SpriteBatch mSpriteBatch;

    private String mStartScreenString = "press any key";
    private BitmapFont mBitmapFont;
    private GlyphLayout layout;

    private int centerScreenX = Constants.GAME_WIDTH / 2;
    private int centerScreenY = Constants.GAME_HEIGHT / 2;

    private float fontX;

    private PlayerModel mPlayerModel;

    public StartStage(StartScreen startScreen) {
        mStartScreen = startScreen;
        mStartStageCamera = new OrthographicCamera();
        mStartStageCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mStartStageCamera.update();

        mWorld = WorldUtils.createWorld();
        mSpriteBatch = new SpriteBatch();
        mBitmapFont = Gizmo.assetManager.get("res/font/gizmo.fnt", BitmapFont.class);
        mBitmapFont.getData().setScale(0.3f, 0.3f);

        loadPlayerModel();
        addStartText();
        //loadStartStageActors();
    }

    private void loadPlayerModel() {
        /*if (LocalDataManager.loadPlayerActorData() != null) {
            mPlayerModel = LocalDataManager.loadPlayerActorData();
            int playerHearts = 15;
            for (int i=0; i < playerHearts; i++) {
                loadHeart(new Vector2(centerScreenX - (i * 18), centerScreenY));
            }
        }*/
        loadHeart(new Vector2(centerScreenX, centerScreenY));
    }

    private void addStartText() {
        layout = new GlyphLayout(mBitmapFont, mStartScreenString);
        fontX = centerScreenX - layout.width / 2;
    }

    private void loadHeart(Vector2 position) {
        HeartActor heartActor = new HeartActor(ObjectUtils.createHeart(new HeartModel(), mWorld, position));
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

        mSpriteBatch.enableBlending();
        mSpriteBatch.begin();
            mBitmapFont.draw(mSpriteBatch, mStartScreenString, fontX, 25);
        mSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // input
        UserInput.update();
        handleInput();

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

    private void handleInput() {
        if (UserInput.isDown(UserInput.ANY_KEY)) {
            mStartScreen.startGame();
        }
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
    }

}