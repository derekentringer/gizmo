package com.derekentringer.gizmo.stage;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.data.DoorType;
import com.derekentringer.gizmo.actor.data.structure.DoorUserData;
import com.derekentringer.gizmo.actor.player.IPlayerDelegate;
import com.derekentringer.gizmo.actor.player.PlayerActor;
import com.derekentringer.gizmo.level.Level;
import com.derekentringer.gizmo.util.FixtureUtils;
import com.derekentringer.gizmo.util.PlayerUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.constant.Constants;
import com.derekentringer.gizmo.util.input.UserInput;

public class GameStage extends Stage implements ContactListener, IPlayerDelegate {

    //private OrthographicCamera camera;
    //private Box2DDebugRenderer renderer;

    private OrthographicCamera tiledCamera;

    private World world;
    private Level level;

    private float effectiveViewportWidth;
    private float effectiveViewportHeight;

    private PlayerActor playerActor;
    private SpriteBatch spriteBatch;

    public GameStage() {
        setupWorld();
        loadLevel();
        createPlayer();
        //setupDebugRendererCamera();
        setupTiledCamera();
        startBackgroundMusic();
    }

    private void setupWorld() {
        spriteBatch = new SpriteBatch();
        world = WorldUtils.createWorld();
        world.setContactListener(this);
    }

    /*private void setupDebugRendererCamera() {
        renderer = new  zBox2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WorldUtils.ppmCalc(Constants.GAME_WIDTH), WorldUtils.ppmCalc(Constants.GAME_HEIGHT));
        camera.update();
    }*/

    private void setupTiledCamera() {
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        tiledCamera.update();
    }

    private void loadLevel() {
        level = new Level(Constants.LEVEL_SEVEN);
        level.createTileMapLayer(world, "wall");
        level.createTileMapLayer(world, "ground");
        level.createTileMapLayer(world, "doorprevious");
        level.createTileMapLayer(world, "doornext");
    }

    private void startBackgroundMusic() {
        Music backgroundMusic = Gizmo.assetManager.get("res/music/background.ogg", Music.class);
        if(!Constants.DEBUGGING) {
            backgroundMusic.play();
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
        }
    }

    private void createPlayer() {
        playerActor = new PlayerActor(PlayerUtils.createPlayer(world));
        addActor(playerActor);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a =  contact.getFixtureA();
        Fixture b =  contact.getFixtureB();

        if(FixtureUtils.fixtureIsPlayerHitArea(a) && FixtureUtils.fixtureIsGround(b)) {
            playerActor.setIsOnGround(true);
        }
        else if(FixtureUtils.fixtureIsPlayerHitArea(b) && FixtureUtils.fixtureIsGround(a)) {
            playerActor.setIsOnGround(true);
        }

        if(FixtureUtils.fixtureIsDoor(a)) {
            playerActor.setIsAtDoor(true);
            playerActor.setIsAtDoorUserData((DoorUserData) a.getBody().getUserData());
        }
        else if(FixtureUtils.fixtureIsDoor(b)) {
            playerActor.setIsAtDoor(true);
            playerActor.setIsAtDoorUserData((DoorUserData) b.getBody().getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    @Override
    public void draw() {
        super.draw();

        //tiled maps render camera
        level.getTiledMapRenderer().setView(tiledCamera);
        level.getTiledMapRenderer().render();

        //world debugRenderer camera
        //renderer.render(world, camera.combined);

        spriteBatch.setProjectionMatrix(tiledCamera.combined);
        playerActor.render(spriteBatch);

        updateCameraPlayerMovement(playerActor.getPosition().x, playerActor.getPosition().y);

        handlePlayerPosition(playerActor.getPosition().y);
    }

    private void updateCameraPlayerMovement(float playerX, float playerY) {
        MapProperties prop = level.getTiledMap().getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        effectiveViewportWidth = tiledCamera.viewportWidth * tiledCamera.zoom;
        effectiveViewportHeight = tiledCamera.viewportHeight * tiledCamera.zoom;

        float minWidth = effectiveViewportWidth / 2f;
        float minHeight = effectiveViewportHeight / 2f;
        float maxWidth = (mapWidth * level.getTileSize()) - (effectiveViewportWidth / 2f);
        float maxHeight = (mapHeight * level.getTileSize()) - (effectiveViewportHeight / 2f);

        tiledCamera.position.x = Math.round(MathUtils.clamp(playerX * Constants.PPM, minWidth, maxWidth));
        tiledCamera.position.y = Math.round(MathUtils.clamp(playerY * Constants.PPM, minHeight, maxHeight));

        tiledCamera.update();
    }

    private void handlePlayerPosition(float playerY) {
        if(playerY * Constants.PPM < 0) {
            playerIsOffMap(true);
        }
    }

    private void handleInput() {
        if(UserInput.isDown(UserInput.JUMP_BUTTON)) {
            if(playerActor.getIsOnGround()) {
                playerActor.setIsOnGround(false);
                playerActor.jump();
            }
        }

        if(UserInput.isDown(UserInput.LEFT_BUTTON)) {
            playerActor.moveLeft();
        }

        if(UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            playerActor.moveRight();
        }

        if(!UserInput.isDown(UserInput.LEFT_BUTTON) && !UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            playerActor.stoppedMoving();
        }

        if(!UserInput.isDown(UserInput.JUMP_BUTTON)) {
            playerActor.stopJumping();
        }

        if(UserInput.isDown(UserInput.ENTER_DOOR)) {
            if(playerActor.getIsAtDoor()) {
                //TODO load the correct level
                if(playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.PREVIOUS)) {
                    //
                }
                else if(playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.NEXT)) {
                    createPlayer();
                }
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        UserInput.update();
        handleInput();

        playerActor.update(delta);

        Constants.ACCUMULATOR += delta;
        while (Constants.ACCUMULATOR >= delta) {
            world.step(Constants.TIME_STEP, 6, 2);
            Constants.ACCUMULATOR -= Constants.TIME_STEP;
        }
    }

    @Override
    public void playerIsOffMap(boolean offMap) {
        createPlayer();
    }

}