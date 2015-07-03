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
import com.derekentringer.gizmo.manager.TileMapManager;
import com.derekentringer.gizmo.util.FixtureUtils;
import com.derekentringer.gizmo.util.PlayerUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.constant.Constants;
import com.derekentringer.gizmo.util.constant.GameLevel;
import com.derekentringer.gizmo.util.input.UserInput;

public class GameStage extends Stage implements ContactListener, IPlayerDelegate {

    //private OrthographicCamera camera;
    //private Box2DDebugRenderer renderer;

    private OrthographicCamera tiledCamera;

    private World world;
    private TileMapManager tileMapManager;

    private float effectiveViewportWidth;
    private float effectiveViewportHeight;

    private PlayerActor playerActor;
    private SpriteBatch spriteBatch;

    private GameLevel currentLevel;

    public GameStage(GameLevel level) {
        currentLevel = level;
        setupWorld();
        loadLevel(level);
        createPlayer((Integer) currentLevel.getXpos(), (Integer) currentLevel.getYpos());
        //setupDebugRendererCamera();
        setupTiledCamera();
    }

    private void setupWorld() {
        spriteBatch = new SpriteBatch();
        world = WorldUtils.createWorld();
        world.setContactListener(this);
    }

    private void setupTiledCamera() {
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        tiledCamera.update();
    }

    public void loadLevel(GameLevel level) {
        System.out.print("loading level: " + level.getLevel().toString());
        tileMapManager = new TileMapManager(level.getMap().toString());
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
        tileMapManager.getTiledMapRenderer().setView(tiledCamera);
        tileMapManager.getTiledMapRenderer().render();

        //world debugRenderer camera
        //renderer.render(world, camera.combined);

        spriteBatch.setProjectionMatrix(tiledCamera.combined);
        playerActor.render(spriteBatch);

        updateCameraPlayerMovement(playerActor.getPosition().x, playerActor.getPosition().y);

        handlePlayerPosition(playerActor.getPosition().y);
    }

    private void updateCameraPlayerMovement(float playerX, float playerY) {
        MapProperties prop = tileMapManager.getTiledMap().getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        effectiveViewportWidth = tiledCamera.viewportWidth * tiledCamera.zoom;
        effectiveViewportHeight = tiledCamera.viewportHeight * tiledCamera.zoom;

        float minWidth = effectiveViewportWidth / 2f;
        float minHeight = effectiveViewportHeight / 2f;
        float maxWidth = (mapWidth * tileMapManager.getTileSize()) - (effectiveViewportWidth / 2f);
        float maxHeight = (mapHeight * tileMapManager.getTileSize()) - (effectiveViewportHeight / 2f);

        tiledCamera.position.x = Math.round(MathUtils.clamp(tiledCamera.position.x + (playerX * Constants.PPM - tiledCamera.position.x) * 0.1f, minWidth, maxWidth));
        tiledCamera.position.y = Math.round(MathUtils.clamp(tiledCamera.position.y + (playerY * Constants.PPM - tiledCamera.position.y) * 0.1f, minHeight, maxHeight));

        tiledCamera.update();
    }

    private void handlePlayerPosition(float playerY) {
        if(playerY * Constants.PPM < 0) {
            playerIsOffMap(true);
        }
    }

    private boolean alreadyEntered = false;

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
                    if ((Integer) currentLevel.getLevel() > 0) {
                        alreadyEntered = true;
                        tileMapManager.destroyTiledMap();
                        WorldUtils.destroyBodies(world);
                        int newLevel = (Integer) currentLevel.getLevel() - 1;
                        currentLevel = Constants.gameLevels.get(newLevel);
                        loadLevel(Constants.gameLevels.get(newLevel));
                        createPlayer((Integer) Constants.gameLevels.get(newLevel).getXpos(),
                                (Integer) Constants.gameLevels.get(newLevel).getYpos());
                    }
                }
                else if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.NEXT)) {
                    if ((Integer) currentLevel.getLevel() < Constants.gameLevels.size()) {
                        alreadyEntered = true;
                        tileMapManager.destroyTiledMap();
                        WorldUtils.destroyBodies(world);
                        int newLevel = (Integer) currentLevel.getLevel() + 1;
                        currentLevel = Constants.gameLevels.get(newLevel);
                        loadLevel(Constants.gameLevels.get(newLevel));
                        createPlayer((Integer) Constants.gameLevels.get(newLevel).getXpos(),
                                (Integer) Constants.gameLevels.get(newLevel).getYpos());
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