package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.object.HeartActor;
import com.derekentringer.gizmo.component.screen.StartScreen;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.ObjectUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class StartStage extends Stage implements ControllerListener {

    public static final String TAG = StartStage.class.getSimpleName();

    private final ArrayList<BaseActor> mStartStageActorsArray = new ArrayList<BaseActor>();

    private StartScreen mStartScreen;
    private World mWorld;
    private OrthographicCamera mStartStageCamera;
    private SpriteBatch mSpriteBatch;

    private String mStartScreenString = "press any key";
    private String mStartScreenStringController = "press any button";
    private BitmapFont mBitmapFont;
    private GlyphLayout layout;
    private String startStringDisplay;

    private int centerScreenX = Constants.GAME_WIDTH / 2;
    private int centerScreenY = Constants.GAME_HEIGHT / 2;

    private int screenWidth = Constants.GAME_WIDTH;

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

        loadPlayerHearts();
        addStartText();
    }

    private void loadPlayerHearts() {
        if (LocalDataManager.loadPlayerActorData() != null) {
            mPlayerModel = LocalDataManager.loadPlayerActorData();
            int totalHearts = mPlayerModel.getHearts();
            displayHearts(totalHearts);
        }
        else {
            displayHearts(PlayerModel.DEFAULT_HEARTS);
        }
    }

    private void displayHearts(int hearts) {
        int heartsTotalWidth = hearts * 15;
        int heartsPositionX = screenWidth / 2 - heartsTotalWidth / 2;
        for (int i=0; i < hearts; i++) {
            HeartActor heartActor = new HeartActor(ObjectUtils.createHeart(new HeartModel(), mWorld, new Vector2(heartsPositionX + (i * 20), centerScreenY)));
            heartActor.setName(HeartModel.HEART);
            addActor(heartActor);
            mStartStageActorsArray.add(heartActor);
        }
    }

    private void addStartText() {
        if (isControllerConnected()) {
            layout = new GlyphLayout(mBitmapFont, mStartScreenStringController);
            fontX = centerScreenX - layout.width / 2;
            startStringDisplay = mStartScreenStringController;
        }
        else {
            layout = new GlyphLayout(mBitmapFont, mStartScreenString);
            fontX = centerScreenX - layout.width / 2;
            startStringDisplay = mStartScreenString;
        }
    }

    private boolean isControllerConnected() {
        return Controllers.getControllers().size > 0;
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
            mBitmapFont.draw(mSpriteBatch, startStringDisplay, fontX, 25);
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
        if (isControllerConnected()) {
            if (UserInput.isDown(UserInput.ANY_BUTTON)) {
                mStartScreen.startGame();
            }
        }
        else {
            if (UserInput.isDown(UserInput.ANY_KEY)) {
                mStartScreen.startGame();
            }
        }
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
    }

    @Override
    public void connected(Controller controller) {
        GLog.d(TAG, "CONNECTED: "+controller.getName());
    }

    @Override
    public void disconnected(Controller controller) {
        GLog.d(TAG, "DISCONNECTED: "+controller.getName());
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }

}