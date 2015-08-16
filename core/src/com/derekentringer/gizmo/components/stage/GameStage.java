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
import com.derekentringer.gizmo.components.actor.enemy.PhantomActor;
import com.derekentringer.gizmo.components.actor.player.PlayerActor;
import com.derekentringer.gizmo.components.actor.player.interfaces.IPlayerDelegate;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBlackActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBloodActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBronzeActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorGoldActor;
import com.derekentringer.gizmo.components.stage.interfaces.IHudStageDelegate;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.body.DeleteBody;
import com.derekentringer.gizmo.model.enemy.PhantomModel;
import com.derekentringer.gizmo.model.level.LevelModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.LifeModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.model.structure.DoorModel;
import com.derekentringer.gizmo.model.structure.DoorType;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.FixtureUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;
import com.derekentringer.gizmo.util.map.MapParser;
import com.derekentringer.gizmo.util.map.interfaces.IMapParserDelegate;

import java.util.ArrayList;

public class GameStage extends Stage implements ContactListener, IPlayerDelegate, IMapParserDelegate {

    private static final String TAG = GameStage.class.getSimpleName();

    // TODO create flag for debugging in new camera class
    //private OrthographicCamera box2dDebugCamera;
    //private Box2DDebugRenderer box2dDebugRenderer;

    public IHudStageDelegate hudStageDelegate = null;

    private OrthographicCamera mMainCamera;
    private OrthographicCamera mMidBackgroundCamera;
    private OrthographicCamera mBackgroundCamera;

    private World mWorld;
    private MapParser mMapParser;
    private SpriteBatch mSpriteBatch;

    private float mEffectiveViewportWidth;
    private float mEffectiveViewportHeight;

    private PlayerActor mPlayerActor;
    private PlayerModel mPlayerModel;
    private boolean mIsPlayerDead = false;
    private ArrayList<DeleteBody> mDeleteBodies = new ArrayList<DeleteBody>();

    private LevelModel mLevelModel;
    private LevelModel mLoadedLevelModel;

    private DoorGoldActor mDoorGoldActor;

    private boolean alreadyEntered = false;

    public GameStage() {
    }

    public void init(LevelModel level) {
        mLevelModel = level;
        setupWorld();
        loadLevel(level, DoorType.PREVIOUS);
        //setupDebugRendererCamera();
        setupMainCamera();
        setupMidBackgroundCamera();
        setupBackgroundCamera();
    }

    private void setupWorld() {
        mSpriteBatch = new SpriteBatch();
        mWorld = WorldUtils.createWorld();
        mWorld.setContactListener(this);
    }

    //TODO create new camera class
    private void setupMainCamera() {
        mMainCamera = new OrthographicCamera();
        mMainCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mMainCamera.update();
    }

    private void setupMidBackgroundCamera() {
        mMidBackgroundCamera = new OrthographicCamera();
        mMidBackgroundCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mMidBackgroundCamera.zoom = 1.3f;
        mMidBackgroundCamera.update();
    }

    private void setupBackgroundCamera() {
        mBackgroundCamera = new OrthographicCamera();
        mBackgroundCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mBackgroundCamera.zoom = 1.7f;
        mBackgroundCamera.update();
    }

    /*private void setupDebugRendererCamera() {
        box2dDebugRenderer = new Box2DDebugRenderer();
        box2dDebugCamera = new OrthographicCamera();
        box2dDebugCamera.setToOrtho(false, WorldUtils.ppmCalc(Constants.GAME_WIDTH), WorldUtils.ppmCalc(Constants.GAME_HEIGHT));
        box2dDebugCamera.update();
    }*/

    public void loadLevel(LevelModel level, String whichDoor) {
        GLog.d(TAG, "loading level: " + level.getLevelInt());
        if (LocalDataManager.loadLevelData(level) != null) {
            mLoadedLevelModel = LocalDataManager.loadLevelData(level);
        }
        else {
            mLoadedLevelModel = level;
        }
        mMapParser = new MapParser(mLoadedLevelModel, level.getLevelMap(), level.getLevelMidMap(), level.getLevelBackMap());
        mMapParser.delegate = this;
        mMapParser.createTileMapLayers(mWorld);
        mMapParser.createTileMapObjects(mWorld, whichDoor);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // player at door
        if (BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.DOOR)) {
            mPlayerActor.setIsAtDoor(true);
            mPlayerActor.setIsAtDoorUserData((DoorModel) b.getBody().getUserData());
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.DOOR)) {
            mPlayerActor.setIsAtDoor(true);
            mPlayerActor.setIsAtDoorUserData((DoorModel) a.getBody().getUserData());
        }

        if (BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.DOOR_OFF)) {
            mPlayerActor.setIsAtDoor(false);
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.DOOR_OFF)) {
            mPlayerActor.setIsAtDoor(false);
        }

        // player fixture and ground detection
        if (FixtureUtils.fixtureIsPlayerHitArea(a) && FixtureUtils.fixtureIsGround(b)) {
            mPlayerActor.setIsOnGround(true);
        }
        else if (FixtureUtils.fixtureIsPlayerHitArea(b) && FixtureUtils.fixtureIsGround(a)) {
            mPlayerActor.setIsOnGround(true);
        }

        // player/enemy collisions
        if (BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.ENEMY) && BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.setHitEnemy(BodyUtils.getEnemyBodyDamageAmount(a.getBody()));
            mPlayerActor.setIsFlinching(true);
            mPlayerActor.startFlinchingTimer(mPlayerActor);
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.ENEMY) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.setHitEnemy(BodyUtils.getEnemyBodyDamageAmount(b.getBody()));
            mPlayerActor.setIsFlinching(true);
            mPlayerActor.startFlinchingTimer(mPlayerActor);
        }

        // pickup a key
        if (BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.KEY) && BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.addKey((KeyModel) a.getBody().getUserData());
            mLoadedLevelModel.addPickedUpKey((KeyModel) a.getBody().getUserData());
            mDeleteBodies.add(new DeleteBody((KeyModel) a.getBody().getUserData(), a.getBody()));
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.KEY) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.addKey((KeyModel) b.getBody().getUserData());
            mLoadedLevelModel.addPickedUpKey((KeyModel) b.getBody().getUserData());
            mDeleteBodies.add(new DeleteBody((KeyModel) b.getBody().getUserData(), b.getBody()));
        }

        // pickup a heart
        if (BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.HEART) && BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.addHealthHeart((HeartModel) a.getBody().getUserData());
            mLoadedLevelModel.addPickedUpHeart((HeartModel) a.getBody().getUserData());
            hudStageDelegate.setHudHealthHearts(mPlayerActor.getHealthHearts());
            mDeleteBodies.add(new DeleteBody((HeartModel) a.getBody().getUserData(), a.getBody()));
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.HEART) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.addHealthHeart((HeartModel) b.getBody().getUserData());
            mLoadedLevelModel.addPickedUpHeart((HeartModel) b.getBody().getUserData());
            hudStageDelegate.setHudHealthHearts(mPlayerActor.getHealthHearts());
            mDeleteBodies.add(new DeleteBody((HeartModel) b.getBody().getUserData(), b.getBody()));
        }

        // pickup a life
        if (BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.LIFE) && BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.incrementLives();
            mLoadedLevelModel.addPickedUpLife((LifeModel) a.getBody().getUserData());
            hudStageDelegate.setHudLives(mPlayerActor.getPlayerLives());
            mDeleteBodies.add(new DeleteBody((LifeModel) a.getBody().getUserData(), a.getBody()));
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.LIFE) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.incrementLives();
            mLoadedLevelModel.addPickedUpLife((LifeModel) b.getBody().getUserData());
            hudStageDelegate.setHudLives(mPlayerActor.getPlayerLives());
            mDeleteBodies.add(new DeleteBody((LifeModel) b.getBody().getUserData(), b.getBody()));
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
        mMapParser.getTiledMapBackgroundRenderer().setView(mBackgroundCamera);
        mMapParser.getTiledMapBackgroundRenderer().render();

        mMapParser.getTiledMapMidBackgroundRenderer().setView(mMidBackgroundCamera);
        mMapParser.getTiledMapMidBackgroundRenderer().render();

        mMapParser.getTiledMapRenderer().setView(mMainCamera);
        mMapParser.getTiledMapRenderer().render();

        // mWorld debugRenderer camera
        // box2dDebugRenderer.render(mWorld, box2dDebugCamera.combined);

        mSpriteBatch.setProjectionMatrix(mMainCamera.combined);

        for (BaseActor actor : mMapParser.actorsArray) {
            actor.render(mSpriteBatch);
            if (actor.getName().equalsIgnoreCase(PhantomModel.PHANTOM)) {
                ((PhantomActor) actor).setPlayerPosition(mPlayerActor.getPosition().x);
            }
        }

        updateCameraPlayerMovement(mPlayerActor.getPosition().x, mPlayerActor.getPosition().y);
        handlePlayerPosition(mPlayerActor.getPosition().y);
        handlePlayerDied();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        UserInput.update();
        handleInput();

        for (BaseActor actor : mMapParser.actorsArray) {
            actor.update(delta);
            actor.act(delta);
        }

        Constants.ACCUMULATOR += delta;
        while (Constants.ACCUMULATOR >= delta) {
            mWorld.step(Constants.TIME_STEP, 6, 2);
            Constants.ACCUMULATOR -= Constants.TIME_STEP;
        }
    }

    private void deleteObsoleteActors() {
        for (int i = 0; i < mDeleteBodies.size(); i++) {
            //delete the actor from our actorsArray
            //look thru delete Bodies arraylist
            //delete the associated mBody
            for (int e=0; e < mMapParser.actorsArray.size(); e++) {
                BaseActor actorToDelete = mMapParser.actorsArray.get(e);
                if (actorToDelete.mBaseModel.equals(mDeleteBodies.get(i).getBaseModel())) {
                    mMapParser.actorsArray.remove(e);
                    actorToDelete.remove();
                    //delete the mBody
                    WorldUtils.destroyBody(mWorld, mDeleteBodies.get(i).getBody());
                    mDeleteBodies.remove(i);
                    break;
                }
            }
        }
        //remove any actor that falls off the stage
        for (int j = 0; j < mMapParser.actorsArray.size(); j++) {
            if (mMapParser.actorsArray.get(j).getPosition().y * Constants.PPM < 0) {
                mMapParser.actorsArray.get(j).remove();
                mMapParser.actorsArray.remove(j);
            }
        }
    }

    private void updateCameraPlayerMovement(float playerX, float playerY) {
        MapProperties prop = mMapParser.getTiledMap().getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        mEffectiveViewportWidth = mMainCamera.viewportWidth * mMainCamera.zoom;
        mEffectiveViewportHeight = mMainCamera.viewportHeight * mMainCamera.zoom;

        float minWidth = mEffectiveViewportWidth / 2f;
        float minHeight = mEffectiveViewportHeight / 2f;
        float maxWidth = (mapWidth * mMapParser.getTileSize()) - (mEffectiveViewportWidth / 2f);
        float maxHeight = (mapHeight * mMapParser.getTileSize()) - (mEffectiveViewportHeight / 2f);

        mMainCamera.position.x = Math.round(MathUtils.clamp(mMainCamera.position.x + (playerX * Constants.PPM - mMainCamera.position.x) * 0.1f, minWidth, maxWidth));
        mMainCamera.position.y = Math.round(MathUtils.clamp(mMainCamera.position.y + (playerY * Constants.PPM - mMainCamera.position.y) * 0.1f, minHeight, maxHeight));

        mMainCamera.update();

        mMidBackgroundCamera.position.x = Math.round(MathUtils.clamp(mMidBackgroundCamera.position.x + (playerX * Constants.PPM - mMidBackgroundCamera.position.x) * 0.1f, minWidth, maxWidth));
        mMidBackgroundCamera.position.y = Math.round(MathUtils.clamp(mMidBackgroundCamera.position.y + (playerY * Constants.PPM - mMidBackgroundCamera.position.y) * 0.1f, minHeight, maxHeight));

        mMidBackgroundCamera.update();

        mBackgroundCamera.position.x = Math.round(MathUtils.clamp(mBackgroundCamera.position.x + (playerX * Constants.PPM - mBackgroundCamera.position.x) * 0.1f, minWidth, maxWidth));
        mBackgroundCamera.position.y = Math.round(MathUtils.clamp(mBackgroundCamera.position.y + (playerY * Constants.PPM - mBackgroundCamera.position.y) * 0.1f, minHeight, maxHeight));

        mBackgroundCamera.update();
    }

    private void handlePlayerPosition(float playerY) {
        if (playerY * Constants.PPM < 0) {
            playerIsOffMap(true);
        }
    }

    private void handlePlayerDied() {
        if (mIsPlayerDead) {
            mIsPlayerDead = false;
            mMapParser.destroyTiledMap();
            WorldUtils.destroyBodies(mWorld);
            if (mPlayerActor.getPlayerModel().getPlayerLives() == PlayerModel.DEFAULT_LIVES) {
                loadLevel(Constants.gameLevels.get(0),  DoorType.PREVIOUS);
            }
            else {
                loadLevel(mLevelModel, DoorType.PREVIOUS);
            }
        }
    }

    private void handleInput() {
        if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
            if (mPlayerActor.getIsOnGround()) {
                mPlayerActor.setIsOnGround(false);
                mPlayerActor.jump();
            }
        }

        if (UserInput.isDown(UserInput.LEFT_BUTTON)) {
            alreadyEntered = false;
            mPlayerActor.moveLeft();
        }

        if (UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            alreadyEntered = false;
            mPlayerActor.moveRight();
        }

        if (!UserInput.isDown(UserInput.LEFT_BUTTON) && !UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            mPlayerActor.stoppedMoving();
        }

        if (!UserInput.isDown(UserInput.JUMP_BUTTON)) {
            mPlayerActor.stopJumping();
        }

        if (UserInput.isDown(UserInput.ENTER_DOOR)) {
            if (alreadyEntered) {
                return;
            }
            if (mPlayerActor.getIsAtDoor()) {
                if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_GOLD)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_GOLD)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {

                        // TODO animate locked doors
                        // mDoorGoldActor.startAnimation();

                        mLoadedLevelModel.addOpenedDoor(mPlayerActor.getIsAtDoorUserData());
                        loadNewLevel(mPlayerActor.getIsAtDoorUserData().getLevelNumber(), mPlayerActor.getIsAtDoorUserData().getDestinationDoor());
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_BRONZE)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_BRONZE)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {
                        mLoadedLevelModel.addOpenedDoor(mPlayerActor.getIsAtDoorUserData());
                        loadNewLevel(mPlayerActor.getIsAtDoorUserData().getLevelNumber(), mPlayerActor.getIsAtDoorUserData().getDestinationDoor());
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_BLOOD)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_BLOOD)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {
                        mLoadedLevelModel.addOpenedDoor(mPlayerActor.getIsAtDoorUserData());
                        loadNewLevel(mPlayerActor.getIsAtDoorUserData().getLevelNumber(), mPlayerActor.getIsAtDoorUserData().getDestinationDoor());
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_BLACK)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_BLACK)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {
                        mLoadedLevelModel.addOpenedDoor(mPlayerActor.getIsAtDoorUserData());
                        loadNewLevel(mPlayerActor.getIsAtDoorUserData().getLevelNumber(), mPlayerActor.getIsAtDoorUserData().getDestinationDoor());
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.OTHER)) {
                    loadNewLevel(mPlayerActor.getIsAtDoorUserData().getLevelNumber(), mPlayerActor.getIsAtDoorUserData().getDestinationDoor());
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.PREVIOUS)) {
                    if (mLevelModel.getLevelInt() > 0) {
                        loadNewLevel(mLevelModel.getLevelInt() - 1, DoorType.NEXT);
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.NEXT)) {
                    if (mLevelModel.getLevelInt() < Constants.gameLevels.size() - 1) {
                        loadNewLevel(mLevelModel.getLevelInt() + 1, DoorType.PREVIOUS);
                    }
                }
            }
        }
    }

    private void loadNewLevel(int newLevel, String whichDoor) {
        alreadyEntered = true;
        mLevelModel = Constants.gameLevels.get(newLevel);

        mMapParser.destroyTiledMap();
        WorldUtils.destroyBodies(mWorld);

        mPlayerActor.setCurrentLevel(newLevel);
        LocalDataManager.savePlayerActorData(mPlayerActor.getPlayerModel());

        LocalDataManager.saveLevelData(mLoadedLevelModel);

        loadLevel(Constants.gameLevels.get(newLevel), whichDoor);
    }

    @Override
    public void setPlayerActor(PlayerActor playerActor) {
        mPlayerActor = playerActor;
        mPlayerActor.delegate = this;
        if (LocalDataManager.loadPlayerActorData() != null) {
            mPlayerModel = LocalDataManager.loadPlayerActorData();
            playerActor.initPlayerData(mPlayerModel);
        }
        else {
            mPlayerModel = new PlayerModel();
            mPlayerModel.setPlayerHearts(PlayerModel.DEFAULT_HEARTS);
            mPlayerModel.setPlayerHealth(PlayerModel.DEFAULT_HEALTH);
            mPlayerModel.setPlayerLives(PlayerModel.DEFAULT_LIVES);
            mPlayerModel.setCurrentLevel(PlayerModel.DEFAULT_LEVEL);
            playerActor.initPlayerData(mPlayerModel);
            LocalDataManager.savePlayerActorData(mPlayerModel);
        }

        hudStageDelegate.setHudHealthHearts(mPlayerActor.getPlayerModel().getPlayerHearts());
        hudStageDelegate.resetHudShapes();
        hudStageDelegate.setHudHealth(mPlayerActor.getPlayerModel().getPlayerHealth());
        hudStageDelegate.setHudLives(mPlayerActor.getPlayerModel().getPlayerLives());
    }

    @Override
    public void setLockedGoldDoor(DoorGoldActor doorGoldActor) {
        mDoorGoldActor = doorGoldActor;
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
        mPlayerActor.resetLives();
    }

    private void killPlayer() {
        mPlayerActor.resetHealth();
        mPlayerActor.deIncrementLives();
        hudStageDelegate.setHudLives(mPlayerActor.getPlayerModel().getPlayerLives());
        LocalDataManager.savePlayerActorData(mPlayerActor.getPlayerModel());
        mIsPlayerDead = true;
    }

    public void quitGame() {
        LocalDataManager.savePlayerActorData(mPlayerActor.getPlayerModel());
        LocalDataManager.saveLevelData(mLoadedLevelModel);
    }

    /*private void startBackgroundMusic() {
        Music backgroundMusic = Gizmo.assetManager.get("res/music/background.ogg", Music.class);
        if(!Constants.IS_DEBUGGING) {
            backgroundMusic.play();
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
        }
    }*/

}