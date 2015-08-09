package com.derekentringer.gizmo.components.stage;

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
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBlackActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBloodActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBronzeActor;
import com.derekentringer.gizmo.model.structure.DoorType;
import com.derekentringer.gizmo.model.enemy.PhantomModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.model.structure.DoorModel;
import com.derekentringer.gizmo.components.actor.enemy.PhantomActor;
import com.derekentringer.gizmo.components.actor.player.PlayerActor;
import com.derekentringer.gizmo.components.actor.player.interfaces.IPlayerDelegate;
import com.derekentringer.gizmo.components.actor.structure.door.DoorGoldActor;
import com.derekentringer.gizmo.model.level.LevelModel;
import com.derekentringer.gizmo.util.map.MapParser;
import com.derekentringer.gizmo.util.map.interfaces.IMapParserDelegate;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.model.body.DeleteBody;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.components.stage.interfaces.IHudStageDelegate;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.FixtureUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.input.UserInput;

import java.util.ArrayList;

public class GameStage extends Stage implements ContactListener, IPlayerDelegate, IMapParserDelegate {

    //TODO create flag for debugging in new camera class
    //private OrthographicCamera box2dDebugCamera;
    //private Box2DDebugRenderer box2dDebugRenderer;

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
    private PlayerModel playerData;
    private boolean isPlayerDead = false;
    private ArrayList<DeleteBody> deleteBodies = new ArrayList<DeleteBody>();

    private LevelModel levelModel;
    private LevelModel loadedLevelModel;

    private DoorGoldActor doorGoldActor;

    //TODO this flag not working correctly
    private boolean alreadyEntered = false;

    public GameStage() {
    }

    public void init(LevelModel level) {
        levelModel = level;
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

    /*private void setupDebugRendererCamera() {
        box2dDebugRenderer = new Box2DDebugRenderer();
        box2dDebugCamera = new OrthographicCamera();
        box2dDebugCamera.setToOrtho(false, WorldUtils.ppmCalc(Constants.GAME_WIDTH), WorldUtils.ppmCalc(Constants.GAME_HEIGHT));
        box2dDebugCamera.update();
    }*/

    public void loadLevel(LevelModel level, String whichDoor) {
        System.out.println("loading level: " + level.getLevelInt());
        if (LocalDataManager.loadLevelData(level) != null) {
            loadedLevelModel = LocalDataManager.loadLevelData(level);
        }
        else {
            loadedLevelModel = level;
        }
        mapParser = new MapParser(loadedLevelModel, level.getLevelMap(), level.getsLevelMidMap(), level.getsLevelBackMap());
        mapParser.delegate = this;
        mapParser.createTileMapLayers(world);
        mapParser.createTileMapObjects(world, whichDoor);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // player at door
        if (BodyUtils.bodyIsPlayer(a.getBody()) && BodyUtils.bodyIsDoor(b.getBody())) {
            playerActor.setIsAtDoor(true);
            playerActor.setIsAtDoorUserData((DoorModel) b.getBody().getUserData());
        }
        else if (BodyUtils.bodyIsPlayer(b.getBody()) && BodyUtils.bodyIsDoor(a.getBody())) {
            playerActor.setIsAtDoor(true);
            playerActor.setIsAtDoorUserData((DoorModel) a.getBody().getUserData());
        }

        if (BodyUtils.bodyIsPlayer(a.getBody()) && BodyUtils.bodyIsDoorOff(b.getBody())) {
            playerActor.setIsAtDoor(false);
        }
        else if (BodyUtils.bodyIsPlayer(b.getBody()) && BodyUtils.bodyIsDoorOff(a.getBody())) {
            playerActor.setIsAtDoor(false);
        }

        // player fixture and ground detection
        if (FixtureUtils.fixtureIsPlayerHitArea(a) && FixtureUtils.fixtureIsGround(b)) {
            playerActor.setIsOnGround(true);
        }
        else if (FixtureUtils.fixtureIsPlayerHitArea(b) && FixtureUtils.fixtureIsGround(a)) {
            playerActor.setIsOnGround(true);
        }

        // player/enemy collisions
        if (BodyUtils.bodyIsEnemy(a.getBody()) && BodyUtils.bodyIsPlayer(b.getBody())) {
            playerActor.setHitEnemy(BodyUtils.getEnemyBodyDamageAmount(a.getBody()));
            playerActor.setIsFlinching(true);
            playerActor.startFlinchingTimer(playerActor);
        }
        else if (BodyUtils.bodyIsEnemy(b.getBody()) && BodyUtils.bodyIsPlayer(a.getBody())) {
            playerActor.setHitEnemy(BodyUtils.getEnemyBodyDamageAmount(b.getBody()));
            playerActor.setIsFlinching(true);
            playerActor.startFlinchingTimer(playerActor);
        }

        //pickup a key
        if (BodyUtils.bodyIsKey(a.getBody()) && BodyUtils.bodyIsPlayer(b.getBody())) {
            playerActor.addKey((KeyModel) a.getBody().getUserData());
            loadedLevelModel.addPickedUpKey((KeyModel) a.getBody().getUserData());
            deleteBodies.add(new DeleteBody((KeyModel) a.getBody().getUserData(), a.getBody()));
        }
        else if (BodyUtils.bodyIsKey(b.getBody()) && BodyUtils.bodyIsPlayer(a.getBody())) {
            playerActor.addKey((KeyModel) b.getBody().getUserData());
            loadedLevelModel.addPickedUpKey((KeyModel) b.getBody().getUserData());
            deleteBodies.add(new DeleteBody((KeyModel) b.getBody().getUserData(), b.getBody()));
        }

        //pickup a heart
        if (BodyUtils.bodyIsHeart(a.getBody()) && BodyUtils.bodyIsPlayer(b.getBody())) {
            playerActor.addHealthHeart((HeartModel) a.getBody().getUserData());
            loadedLevelModel.addPickedUpHeart((HeartModel) a.getBody().getUserData());
            hudStageDelegate.setHudHealthHearts(playerActor.getHealthHearts());
            deleteBodies.add(new DeleteBody((HeartModel) a.getBody().getUserData(), a.getBody()));
        }
        else if (BodyUtils.bodyIsHeart(b.getBody()) && BodyUtils.bodyIsPlayer(a.getBody())) {
            playerActor.addHealthHeart((HeartModel) b.getBody().getUserData());
            loadedLevelModel.addPickedUpHeart((HeartModel) b.getBody().getUserData());
            hudStageDelegate.setHudHealthHearts(playerActor.getHealthHearts());
            deleteBodies.add(new DeleteBody((HeartModel) b.getBody().getUserData(), b.getBody()));
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

        deleteObsoleteActors();

        //tiled maps render camera
        mapParser.getTiledMapBackgroundRenderer().setView(backgroundCamera);
        mapParser.getTiledMapBackgroundRenderer().render();

        mapParser.getTiledMapMidBackgroundRenderer().setView(midBackgroundCamera);
        mapParser.getTiledMapMidBackgroundRenderer().render();

        mapParser.getTiledMapRenderer().setView(mainCamera);
        mapParser.getTiledMapRenderer().render();

        //world debugRenderer camera
        //box2dDebugRenderer.render(world, box2dDebugCamera.combined);

        spriteBatch.setProjectionMatrix(mainCamera.combined);

        for (BaseActor actor : mapParser.actorsArray) {
            actor.render(spriteBatch);
            if (actor.getName().equalsIgnoreCase(PhantomModel.PHANTOM)) {
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

    private void deleteObsoleteActors() {
        for (int i = 0; i < deleteBodies.size(); i++) {
            //delete the actor from our actorsArray
            //look thru deleteBodies arraylist
            //delete the associated body
            for (int e=0; e < mapParser.actorsArray.size(); e++) {
                BaseActor actorToDelete = mapParser.actorsArray.get(e);
                if (actorToDelete.userData.equals(deleteBodies.get(i).getBaseModel())) {
                    mapParser.actorsArray.remove(e);
                    actorToDelete.remove();
                    //delete the body
                    WorldUtils.destroyBody(world, deleteBodies.get(i).getBody());
                    deleteBodies.remove(i);
                    break;
                }
            }
        }
        //remove any actor that falls off the stage
        for (int j = 0; j < mapParser.actorsArray.size(); j++) {
            if (mapParser.actorsArray.get(j).getPosition().y * Constants.PPM < 0) {
                mapParser.actorsArray.get(j).remove();
                mapParser.actorsArray.remove(j);
            }
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
            if (playerActor.getUserData().getPlayerLives() == PlayerModel.DEFAULT_LIVES) {
                loadLevel(Constants.gameLevels.get(0),  DoorType.PREVIOUS);
            }
            else {
                loadLevel(levelModel, DoorType.PREVIOUS);
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
                if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_GOLD)) {
                    if (playerActor.hasCorrectKey(KeyModel.KEY_GOLD)
                            || !playerActor.getIsAtDoorUserData().getIsLocked()) {

                        //TODO animate locked doors?
                        //doorGoldActor.startAnimation();

                        loadedLevelModel.addOpenedDoor(playerActor.getIsAtDoorUserData());
                        loadNewLevel(playerActor.getIsAtDoorUserData().getLevelNumber(), playerActor.getIsAtDoorUserData().getDestinationDoor());
                    }
                }
                else if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_BRONZE)) {
                    if (playerActor.hasCorrectKey(KeyModel.KEY_BRONZE)
                            || !playerActor.getIsAtDoorUserData().getIsLocked()) {
                        loadedLevelModel.addOpenedDoor(playerActor.getIsAtDoorUserData());
                        loadNewLevel(playerActor.getIsAtDoorUserData().getLevelNumber(), playerActor.getIsAtDoorUserData().getDestinationDoor());
                    }
                }
                else if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_BLOOD)) {
                    if (playerActor.hasCorrectKey(KeyModel.KEY_BLOOD)
                            || !playerActor.getIsAtDoorUserData().getIsLocked()) {
                        loadedLevelModel.addOpenedDoor(playerActor.getIsAtDoorUserData());
                        loadNewLevel(playerActor.getIsAtDoorUserData().getLevelNumber(), playerActor.getIsAtDoorUserData().getDestinationDoor());
                    }
                }
                else if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_BLACK)) {
                    if (playerActor.hasCorrectKey(KeyModel.KEY_BLACK)
                            || !playerActor.getIsAtDoorUserData().getIsLocked()) {
                        loadedLevelModel.addOpenedDoor(playerActor.getIsAtDoorUserData());
                        loadNewLevel(playerActor.getIsAtDoorUserData().getLevelNumber(), playerActor.getIsAtDoorUserData().getDestinationDoor());
                    }
                }
                else if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.OTHER)) {
                    loadNewLevel(playerActor.getIsAtDoorUserData().getLevelNumber(), playerActor.getIsAtDoorUserData().getDestinationDoor());
                }
                else if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.PREVIOUS)) {
                    if (levelModel.getLevelInt() > 0) {
                        loadNewLevel(levelModel.getLevelInt() - 1, DoorType.NEXT);
                    }
                }
                else if (playerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.NEXT)) {
                    if (levelModel.getLevelInt() < Constants.gameLevels.size() - 1) {
                        loadNewLevel(levelModel.getLevelInt() + 1, DoorType.PREVIOUS);
                    }
                }
            }
        }
    }

    private void loadNewLevel(int newLevel, String whichDoor) {
        alreadyEntered = true;
        levelModel = Constants.gameLevels.get(newLevel);

        mapParser.destroyTiledMap();
        WorldUtils.destroyBodies(world);

        playerActor.setCurrentLevel(newLevel);
        LocalDataManager.savePlayerActorData(playerActor.getUserData());

        LocalDataManager.saveLevelData(loadedLevelModel);

        loadLevel(Constants.gameLevels.get(newLevel), whichDoor);
    }

    @Override
    public void setPlayerActor(PlayerActor playerActor) {
        this.playerActor = playerActor;
        this.playerActor.delegate = this;
        if (LocalDataManager.loadPlayerActorData() != null) {
            playerData = LocalDataManager.loadPlayerActorData();
            playerActor.initPlayerData(playerData);
        }
        else {
            playerData = new PlayerModel();
            playerData.setPlayerHearts(PlayerModel.DEFAULT_HEARTS);
            playerData.setPlayerHealth(PlayerModel.DEFAULT_HEALTH);
            playerData.setPlayerLives(PlayerModel.DEFAULT_LIVES);
            playerData.setCurrentLevel(PlayerModel.DEFAULT_LEVEL);
            playerActor.initPlayerData(playerData);
            LocalDataManager.savePlayerActorData(playerData);
        }

        hudStageDelegate.setHudHealthHearts(playerData.getPlayerHearts());
        hudStageDelegate.resetHudShapes();
        hudStageDelegate.setHudHealth(playerData.getPlayerHealth());
        hudStageDelegate.setHudLives(playerData.getPlayerLives());
    }

    @Override
    public void setLockedGoldDoor(DoorGoldActor doorGoldActor) {
        this.doorGoldActor = doorGoldActor;
    }

    @Override
    public void setLockedBronzeDoor(DoorBronzeActor bronzeDoorActor) {
    }

    @Override
    public void setLockedBloodDoor(DoorBloodActor bloodDoorActor) {
    }

    @Override
    public void setLockedBlackDoor(DoorBlackActor blackDoorActor) {
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
    public void playerZeroLives() {
        //show died screen
        playerActor.resetLives();
    }

    private void killPlayer() {
        playerActor.resetHealth();
        playerActor.deIncrementLives();
        hudStageDelegate.setHudLives(playerData.getPlayerLives());
        LocalDataManager.savePlayerActorData(playerActor.getUserData());
        isPlayerDead = true;
    }

    public void quitGame() {
        LocalDataManager.savePlayerActorData(playerActor.getUserData());
        LocalDataManager.saveLevelData(loadedLevelModel);
    }

    /*private void startBackgroundMusic() {
        Music backgroundMusic = Gizmo.assetManager.get("res/music/background.ogg", Music.class);
        if(!Constants.DEBUGGING) {
            backgroundMusic.play();
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
        }
    }*/

}