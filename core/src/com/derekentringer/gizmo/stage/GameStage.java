package com.derekentringer.gizmo.stage;

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
import com.derekentringer.gizmo.actor.data.DoorType;
import com.derekentringer.gizmo.actor.data.structure.DoorUserData;
import com.derekentringer.gizmo.actor.player.IPlayerDelegate;
import com.derekentringer.gizmo.actor.player.PlayerActor;
import com.derekentringer.gizmo.level.Level;
import com.derekentringer.gizmo.manager.TileMapManager;
import com.derekentringer.gizmo.util.FixtureUtils;
import com.derekentringer.gizmo.util.PlayerUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.constant.Constants;
import com.derekentringer.gizmo.util.input.UserInput;

public class GameStage extends Stage implements ContactListener, IPlayerDelegate {

    //private OrthographicCamera camera;
    //private Box2DDebugRenderer renderer;

    private OrthographicCamera mainCamera;
    private OrthographicCamera midBackgroundCamera;
    private OrthographicCamera backgroundCamera;

    private World world;
    private TileMapManager tileMapManager;

    private float effectiveViewportWidth;
    private float effectiveViewportHeight;

    private PlayerActor playerActor;
    private SpriteBatch spriteBatch;

    private Level currentLevel;
    private boolean alreadyEntered = false;

    public GameStage(Level level) {
        currentLevel = level;
        setupWorld();
        loadLevel(level);
        createPlayer((Integer) currentLevel.getXpos(), (Integer) currentLevel.getYpos());
        //setupDebugRendererCamera();
        setupMainCamera();
        setupMidBackgroundCamera();
        setupBackgroundCamera();
    }

    private void setupWorld() {
        spriteBatch = new SpriteBatch();
        world = WorldUtils.createWorld();
        world.setContactListener(this);
    }

    //TODO create new camera class
    private void setupMainCamera() {
        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mainCamera.update();
    }

    private void setupMidBackgroundCamera() {
        midBackgroundCamera = new OrthographicCamera();
        midBackgroundCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        midBackgroundCamera.zoom = 1.3f;
        midBackgroundCamera.update();
    }

    private void setupBackgroundCamera() {
        backgroundCamera = new OrthographicCamera();
        backgroundCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        backgroundCamera.zoom = 1.7f;
        backgroundCamera.update();
    }

    public void loadLevel(Level level) {
        System.out.print("loading level: " + level.getLevelInt());
        tileMapManager = new TileMapManager(level.getLevelMap(), level.getsLevelMidMap(), level.getsLevelBackMap());
        tileMapManager.createTileMapLayers(world);
    }

    private void createPlayer(int xPos, int yPos) {
        playerActor = new PlayerActor(PlayerUtils.createPlayer(world, xPos, yPos));
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
        else {
            playerActor.setIsAtDoor(false);
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
        tileMapManager.getTiledMapBackgroundRenderer().setView(backgroundCamera);
        tileMapManager.getTiledMapBackgroundRenderer().render();

        tileMapManager.getTiledMapMidBackgroundRenderer().setView(midBackgroundCamera);
        tileMapManager.getTiledMapMidBackgroundRenderer().render();

        tileMapManager.getTiledMapRenderer().setView(mainCamera);
        tileMapManager.getTiledMapRenderer().render();

        //world debugRenderer camera
        //renderer.render(world, camera.combined);

        spriteBatch.setProjectionMatrix(mainCamera.combined);
        playerActor.render(spriteBatch);

        updateCameraPlayerMovement(playerActor.getPosition().x, playerActor.getPosition().y);

        handlePlayerPosition(playerActor.getPosition().y);
    }

    private void updateCameraPlayerMovement(float playerX, float playerY) {
        MapProperties prop = tileMapManager.getTiledMap().getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        effectiveViewportWidth = mainCamera.viewportWidth * mainCamera.zoom;
        effectiveViewportHeight = mainCamera.viewportHeight * mainCamera.zoom;

        float minWidth = effectiveViewportWidth / 2f;
        float minHeight = effectiveViewportHeight / 2f;
        float maxWidth = (mapWidth * tileMapManager.getTileSize()) - (effectiveViewportWidth / 2f);
        float maxHeight = (mapHeight * tileMapManager.getTileSize()) - (effectiveViewportHeight / 2f);

        mainCamera.position.x = Math.round(MathUtils.clamp(mainCamera.position.x + (playerX * Constants.PPM - mainCamera.position.x) * 0.1f, minWidth, maxWidth));
        mainCamera.position.y = Math.round(MathUtils.clamp(mainCamera.position.y + (playerY * Constants.PPM - mainCamera.position.y) * 0.1f, minHeight, maxHeight));

        mainCamera.update();

        midBackgroundCamera.position.x = Math.round(MathUtils.clamp(midBackgroundCamera.position.x + (playerX * Constants.PPM - midBackgroundCamera.position.x) * 0.1f, minWidth, maxWidth));
        midBackgroundCamera.position.y = Math.round(MathUtils.clamp(midBackgroundCamera.position.y + (playerY * Constants.PPM - midBackgroundCamera.position.y) * 0.1f, minHeight, maxHeight));

        midBackgroundCamera.update();

        backgroundCamera.position.x = Math.round(MathUtils.clamp(backgroundCamera.position.x + (playerX * Constants.PPM - backgroundCamera.position.x) * 0.1f, minWidth, maxWidth));
        backgroundCamera.position.y = Math.round(MathUtils.clamp(backgroundCamera.position.y + (playerY * Constants.PPM - backgroundCamera.position.y) * 0.1f, minHeight, maxHeight));

        backgroundCamera.update();
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
            alreadyEntered = false;
            playerActor.moveLeft();
        }

        if(UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            alreadyEntered = false;
            playerActor.moveRight();
        }

        if(!UserInput.isDown(UserInput.LEFT_BUTTON) && !UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            playerActor.stoppedMoving();
        }

        if(!UserInput.isDown(UserInput.JUMP_BUTTON)) {
            playerActor.stopJumping();
        }

        if (UserInput.isDown(UserInput.ENTER_DOOR)) {
            if(alreadyEntered) {
                return;
            }
            if (playerActor.getIsAtDoor()) {
                if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.PREVIOUS)) {
                    if (currentLevel.getLevelInt() > 0) {
                        alreadyEntered = true;
                        tileMapManager.destroyTiledMap();
                        WorldUtils.destroyBodies(world);
                        int newLevel = currentLevel.getLevelInt() - 1;
                        currentLevel = Constants.gameLevels.get(newLevel);
                        loadLevel(Constants.gameLevels.get(newLevel));
                        createPlayer(Constants.gameLevels.get(newLevel).getXpos(),
                                Constants.gameLevels.get(newLevel).getYpos());
                    }
                }
                else if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.NEXT)) {
                    if (currentLevel.getLevelInt() < Constants.gameLevels.size() - 1) {
                        alreadyEntered = true;
                        tileMapManager.destroyTiledMap();
                        WorldUtils.destroyBodies(world);
                        int newLevel = currentLevel.getLevelInt() + 1;
                        currentLevel = Constants.gameLevels.get(newLevel);
                        loadLevel(Constants.gameLevels.get(newLevel));
                        createPlayer(Constants.gameLevels.get(newLevel).getXpos(),
                                Constants.gameLevels.get(newLevel).getYpos());
                    }
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
        createPlayer((Integer) currentLevel.getXpos(), (Integer) currentLevel.getYpos());
    }

    /*private void setupDebugRendererCamera() {
        renderer = new  zBox2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WorldUtils.ppmCalc(Constants.GAME_WIDTH), WorldUtils.ppmCalc(Constants.GAME_HEIGHT));
        camera.update();
    }*/

    /*private void startBackgroundMusic() {
        Music backgroundMusic = Gizmo.assetManager.get("res/music/background.ogg", Music.class);
        if(!Constants.DEBUGGING) {
            backgroundMusic.play();
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
        }
    }*/

}