package com.derekentringer.gizmo.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.DoorType;
import com.derekentringer.gizmo.actor.data.enemy.PhantomData;
import com.derekentringer.gizmo.actor.data.object.KeyData;
import com.derekentringer.gizmo.actor.data.player.PlayerData;
import com.derekentringer.gizmo.actor.data.structure.DoorData;
import com.derekentringer.gizmo.actor.enemy.PhantomActor;
import com.derekentringer.gizmo.actor.object.KeyActor;
import com.derekentringer.gizmo.actor.player.IPlayerDelegate;
import com.derekentringer.gizmo.actor.player.PlayerActor;
import com.derekentringer.gizmo.level.IMapParserDelegate;
import com.derekentringer.gizmo.level.Level;
import com.derekentringer.gizmo.level.MapParser;
import com.derekentringer.gizmo.manager.DataManager;
import com.derekentringer.gizmo.stage.interfaces.IHudStageDelegate;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.FixtureUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.constant.Constants;
import com.derekentringer.gizmo.util.input.UserInput;

public class GameStage extends Stage implements ContactListener, IPlayerDelegate, IMapParserDelegate {

    //private OrthographicCamera camera;
    //private Box2DDebugRenderer renderer;

    public IHudStageDelegate hudStageDelegate = null;

    private OrthographicCamera mainCamera;
    private OrthographicCamera midBackgroundCamera;
    private OrthographicCamera backgroundCamera;

    private World world;
    private MapParser mapParser;
    private SpriteBatch spriteBatch;

    private float effectiveViewportWidth;
    private float effectiveViewportHeight;

    private PlayerActor playerActor;
    private PlayerData playerData;
    private boolean isPlayerDead = false;
    private Array<Body> deleteBodies = new Array<Body>();

    private Level currentLevel;

    //TODO this flag not working correctly
    private boolean alreadyEntered = false;

    public GameStage() {
    }

    public void init(Level level) {
        currentLevel = level;
        setupWorld();
        loadLevel(level, DoorType.PREVIOUS);
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

    public void loadLevel(Level level, String whichDoor) {
        System.out.println("loading level: " + level.getLevelInt());
        mapParser = new MapParser(level.getLevelMap(), level.getsLevelMidMap(), level.getsLevelBackMap());
        mapParser.delegate = this;
        mapParser.createTileMapLayers(world);
        mapParser.createTileMapObjects(world, whichDoor);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        //player
        if (FixtureUtils.fixtureIsPlayerHitArea(a) && FixtureUtils.fixtureIsGround(b)) {
            playerActor.setIsOnGround(true);
        }
        else if (FixtureUtils.fixtureIsPlayerHitArea(b) && FixtureUtils.fixtureIsGround(a)) {
            playerActor.setIsOnGround(true);
        }

        //player at door
        if (BodyUtils.bodyIsPlayer(a.getBody()) && BodyUtils.bodyIsDoor(b.getBody())) {
            playerActor.setIsAtDoor(true);
            playerActor.setIsAtDoorUserData((DoorData) b.getBody().getUserData());
        }
        else if (BodyUtils.bodyIsPlayer(b.getBody()) && BodyUtils.bodyIsDoor(a.getBody())) {
            playerActor.setIsAtDoor(true);
            playerActor.setIsAtDoorUserData((DoorData) a.getBody().getUserData());
        }
        else {
            playerActor.setIsAtDoor(false);
        }

        //player/enemy collisions
        if (BodyUtils.bodyIsEnemy(a.getBody()) && BodyUtils.bodyIsPlayer(b.getBody())) {
            playerActor.setHitEnemy(BodyUtils.getBodyDamageAmount(a.getBody()));
            playerActor.setIsFlinching(true);
            playerActor.startFlinchingTimer(playerActor);
        }
        else if (BodyUtils.bodyIsEnemy(b.getBody()) && BodyUtils.bodyIsPlayer(a.getBody())) {
            playerActor.setHitEnemy(BodyUtils.getBodyDamageAmount(b.getBody()));
            playerActor.setIsFlinching(true);
            playerActor.startFlinchingTimer(playerActor);
        }

        //pickup a key
        if (BodyUtils.bodyIsKey(a.getBody()) && BodyUtils.bodyIsPlayer(b.getBody())) {
            playerActor.addKey((KeyData) a.getBody().getUserData());
            deleteBodies.add(a.getBody());
        }
        else if (BodyUtils.bodyIsKey(b.getBody()) && BodyUtils.bodyIsPlayer(a.getBody())) {
            playerActor.addKey((KeyData) b.getBody().getUserData());
            deleteBodies.add(b.getBody());
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

        deleteQueuedActorBodies();

        //tiled maps render camera
        mapParser.getTiledMapBackgroundRenderer().setView(backgroundCamera);
        mapParser.getTiledMapBackgroundRenderer().render();

        mapParser.getTiledMapMidBackgroundRenderer().setView(midBackgroundCamera);
        mapParser.getTiledMapMidBackgroundRenderer().render();

        mapParser.getTiledMapRenderer().setView(mainCamera);
        mapParser.getTiledMapRenderer().render();

        //world debugRenderer camera
        //renderer.render(world, camera.combined);

        spriteBatch.setProjectionMatrix(mainCamera.combined);

        for (BaseActor actor : mapParser.actorsArray) {
            actor.render(spriteBatch);
            if (actor.getName().equalsIgnoreCase(PhantomData.PHANTOM)) {
                ((PhantomActor) actor).setPlayerPosition(playerActor.getPosition().x);
            }
        }

        updateCameraPlayerMovement(playerActor.getPosition().x, playerActor.getPosition().y);
        handlePlayerPosition(playerActor.getPosition().y);
        handlePlayerDied();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        UserInput.update();
        handleInput();

        for (BaseActor actor : mapParser.actorsArray) {
            actor.update(delta);
            actor.act(delta);
        }

        Constants.ACCUMULATOR += delta;
        while (Constants.ACCUMULATOR >= delta) {
            world.step(Constants.TIME_STEP, 6, 2);
            Constants.ACCUMULATOR -= Constants.TIME_STEP;
        }
    }

    //TODO THIS IS KEYS ONLY
    private void deleteQueuedActorBodies() {
        //delete actors/bodies as needed
        for (int i = 0; i < deleteBodies.size; i++) {
            //delete the body
            WorldUtils.destroyBody(world, deleteBodies.get(i));
            deleteBodies.removeIndex(i);
            //remove the actor
            KeyActor keyActor = mapParser.keyArray.get(i);
            keyActor.remove();
            mapParser.keyArray.remove(i);
            //remove key from actors array in MapParser
            int keyIndex = mapParser.actorsArray.indexOf(keyActor);
            mapParser.actorsArray.remove(keyIndex);
        }
    }

    private void updateCameraPlayerMovement(float playerX, float playerY) {
        MapProperties prop = mapParser.getTiledMap().getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        effectiveViewportWidth = mainCamera.viewportWidth * mainCamera.zoom;
        effectiveViewportHeight = mainCamera.viewportHeight * mainCamera.zoom;

        float minWidth = effectiveViewportWidth / 2f;
        float minHeight = effectiveViewportHeight / 2f;
        float maxWidth = (mapWidth * mapParser.getTileSize()) - (effectiveViewportWidth / 2f);
        float maxHeight = (mapHeight * mapParser.getTileSize()) - (effectiveViewportHeight / 2f);

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
        if (playerY * Constants.PPM < 0) {
            playerIsOffMap(true);
        }
    }

    private void handlePlayerDied() {
        if (isPlayerDead) {
            isPlayerDead = false;
            mapParser.destroyTiledMap();
            WorldUtils.destroyBodies(world);
            if (playerActor.getUserData().getPlayerLives() == PlayerData.DEFAULT_LIVES) {
                loadLevel(Constants.gameLevels.get(0), DoorType.PREVIOUS);
            }
            else {
                loadLevel(currentLevel, DoorType.PREVIOUS);
            }
        }
    }

    private void handleInput() {
        if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
            if (playerActor.getIsOnGround()) {
                playerActor.setIsOnGround(false);
                playerActor.jump();
            }
        }

        if (UserInput.isDown(UserInput.LEFT_BUTTON)) {
            alreadyEntered = false;
            playerActor.moveLeft();
        }

        if (UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            alreadyEntered = false;
            playerActor.moveRight();
        }

        if (!UserInput.isDown(UserInput.LEFT_BUTTON) && !UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            playerActor.stoppedMoving();
        }

        if (!UserInput.isDown(UserInput.JUMP_BUTTON)) {
            playerActor.stopJumping();
        }

        if (UserInput.isDown(UserInput.ENTER_DOOR)) {
            if (alreadyEntered) {
                return;
            }
            if (playerActor.getIsAtDoor()) {
                if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.PREVIOUS)) {
                    if (currentLevel.getLevelInt() > 0) {
                        alreadyEntered = true;
                        mapParser.destroyTiledMap();
                        WorldUtils.destroyBodies(world);
                        int newLevel = currentLevel.getLevelInt() - 1;
                        currentLevel = Constants.gameLevels.get(newLevel);

                        playerActor.setCurrentLevel(newLevel);

                        DataManager.savePlayerActorData(playerActor.getUserData());

                        loadLevel(Constants.gameLevels.get(newLevel), DoorType.NEXT);
                    }
                }
                else if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.NEXT)) {
                    if (currentLevel.getLevelInt() < Constants.gameLevels.size() - 1) {
                        alreadyEntered = true;
                        mapParser.destroyTiledMap();
                        WorldUtils.destroyBodies(world);
                        int newLevel = currentLevel.getLevelInt() + 1;
                        currentLevel = Constants.gameLevels.get(newLevel);

                        playerActor.setCurrentLevel(newLevel);

                        DataManager.savePlayerActorData(playerActor.getUserData());

                        loadLevel(Constants.gameLevels.get(newLevel), DoorType.PREVIOUS);
                    }
                }
            }
        }
    }

    @Override
    public void setPlayerActor(PlayerActor playerActor) {
        this.playerActor = playerActor;
        this.playerActor.delegate = this;
        if (DataManager.loadPlayerActorData() != null) {
            playerData = DataManager.loadPlayerActorData();
            playerActor.initPlayerData(playerData);
        }
        else {
            playerData = new PlayerData();
            playerData.setPlayerHearts(PlayerData.DEFAULT_HEARTS);
            playerData.setPlayerHealth(PlayerData.DEFAULT_HEALTH);
            playerData.setPlayerLives(PlayerData.DEFAULT_LIVES);
            playerData.setCurrentLevel(PlayerData.DEFAULT_LEVEL);
            playerActor.initPlayerData(playerData);
            DataManager.savePlayerActorData(playerData);
        }

        hudStageDelegate.setHudHealthHearts(playerData.getPlayerHearts());
        hudStageDelegate.resetHudShapes();
        hudStageDelegate.setHudHealth(playerData.getPlayerHealth());
    }

    @Override
    public void playerIsOffMap(boolean offMap) {
        killPlayer();
    }

    @Override
    public void playerGotHit(int playerHealth) {
        hudStageDelegate.setHudHealth(playerHealth);
        if (playerHealth <= 0) {
            killPlayer();
        }
    }

    @Override
    public void playerDied() {
        killPlayer();
    }

    @Override
    public void playerZeroLives() {
        //show died screen
        playerActor.resetLives();
    }

    private void killPlayer() {
        playerActor.resetHealth();
        playerActor.deIncrementLives();
        DataManager.savePlayerActorData(playerActor.getUserData());
        isPlayerDead = true;
    }

    public void quitGame() {
        DataManager.savePlayerActorData(playerActor.getUserData());
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