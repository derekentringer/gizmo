package com.derekentringer.gizmo.components.stage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.components.actor.enemy.PhantomActor;
import com.derekentringer.gizmo.components.actor.enemy.PhantomLargeActor;
import com.derekentringer.gizmo.components.actor.enemy.interfaces.IEnemy;
import com.derekentringer.gizmo.components.actor.player.PlayerActor;
import com.derekentringer.gizmo.components.actor.player.interfaces.IPlayer;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBlackActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBloodActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBronzeActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorGoldActor;
import com.derekentringer.gizmo.components.actor.structure.door.interfaces.IDoor;
import com.derekentringer.gizmo.components.stage.interfaces.IGameStage;
import com.derekentringer.gizmo.components.stage.interfaces.IHudStage;
import com.derekentringer.gizmo.manager.CameraManager;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.body.DeleteBody;
import com.derekentringer.gizmo.model.enemy.PhantomLargeModel;
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
import com.derekentringer.gizmo.util.map.interfaces.IMapParser;

import java.util.ArrayList;

public class GameStage extends Stage implements IMapParser, IPlayer, IHudStage, IEnemy, IDoor, ContactListener  {

    private static final String TAG = GameStage.class.getSimpleName();

    private ArrayList<IGameStage> listeners = new ArrayList<IGameStage>();

    private World mWorld;
    private MapParser mMapParser;
    private SpriteBatch mSpriteBatch;

    private CameraManager mCameraManager = new CameraManager();

    private PlayerActor mPlayerActor;
    private PlayerModel mPlayerModel;
    private boolean mIsPlayerDead = false;
    private ArrayList<DeleteBody> mDeleteBodies = new ArrayList<DeleteBody>();

    private LevelModel mLevelModel;
    private LevelModel mLoadedLevelModel;

    private DoorGoldActor mDoorGoldActor;
    private DoorBronzeActor mDoorBronzeActor;
    private DoorBloodActor mDoorBloodActor;
    private DoorBlackActor mDoorBlackActor;

    private boolean alreadyEntered = false;

    public GameStage() {
    }

    public void addListener(IGameStage listener) {
        listeners.add(listener);
    }

    public void init(LevelModel level) {
        mLevelModel = level;
        setupWorld();
        loadLevel(level, DoorType.PREVIOUS);
        mCameraManager.createGameCameras();
    }

    private void setupWorld() {
        mSpriteBatch = new SpriteBatch();
        mWorld = WorldUtils.createWorld();
        mWorld.setContactListener(this);
    }

    public void loadLevel(LevelModel level, String whichDoor) {
        GLog.d(TAG, "loading level: " + level.getLevelInt());
        if (LocalDataManager.loadLevelData(level) != null) {
            mLoadedLevelModel = LocalDataManager.loadLevelData(level);
        }
        else {
            mLoadedLevelModel = level;
        }
        mMapParser = new MapParser(this, mLoadedLevelModel, level.getLevelMap(), level.getLevelMidMap(), level.getLevelBackMap());
        mMapParser.addListener(this);
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

            for(IGameStage listener : listeners){
                listener.setHudHealthHearts(mPlayerActor.getHealthHearts());
            }

            mDeleteBodies.add(new DeleteBody((HeartModel) a.getBody().getUserData(), a.getBody()));
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.HEART) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.addHealthHeart((HeartModel) b.getBody().getUserData());
            mLoadedLevelModel.addPickedUpHeart((HeartModel) b.getBody().getUserData());

            for(IGameStage listener : listeners){
                listener.setHudHealthHearts(mPlayerActor.getHealthHearts());
            }

            mDeleteBodies.add(new DeleteBody((HeartModel) b.getBody().getUserData(), b.getBody()));
        }

        // pickup a life
        if (BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.LIFE) && BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.incrementLives();
            mLoadedLevelModel.addPickedUpLife((LifeModel) a.getBody().getUserData());

            for(IGameStage listener : listeners){
                listener.setHudLives(mPlayerActor.getPlayerLives());
            }

            mDeleteBodies.add(new DeleteBody((LifeModel) a.getBody().getUserData(), a.getBody()));
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.LIFE) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.incrementLives();
            mLoadedLevelModel.addPickedUpLife((LifeModel) b.getBody().getUserData());

            for(IGameStage listener : listeners){
                listener.setHudLives(mPlayerActor.getPlayerLives());
            }

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
        mMapParser.getTiledMapBackgroundRenderer().setView(mCameraManager.getBackgroundCamera());
        mMapParser.getTiledMapBackgroundRenderer().render();

        mMapParser.getTiledMapMidBackgroundRenderer().setView(mCameraManager.getMidBackgroundCamera());
        mMapParser.getTiledMapMidBackgroundRenderer().render();

        mMapParser.getTiledMapRenderer().setView(mCameraManager.getMainCamera());
        mMapParser.getTiledMapRenderer().render();

        if (Constants.IS_DEBUG_BOX2D) {
            mCameraManager.getBox2dDebugRenderer().render(mWorld, mCameraManager.getDebugRendererCamera().combined);
        }

        mSpriteBatch.setProjectionMatrix(mCameraManager.getMainCamera().combined);

        for (BaseActor actor : mMapParser.actorsArray) {
            actor.render(mSpriteBatch);
            if (actor.getName().equalsIgnoreCase(PhantomModel.PHANTOM)) {
                ((PhantomActor) actor).setPlayerPosition(mPlayerActor.getPosition().x);
            }
            else if (actor.getName().equalsIgnoreCase(PhantomLargeModel.PHANTOM_LARGE)) {
                ((PhantomLargeActor) actor).setPlayerPosition(mPlayerActor.getPosition().x);
            }
        }

        handlePlayerOffMap(mPlayerActor.getPosition().y);
        handlePlayerDied();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        UserInput.update();
        handleInput();

        //add check to shake camera here
        mCameraManager.updateCameraPlayerMovement(mPlayerActor.getPosition().x, mPlayerActor.getPosition().y, mMapParser);

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

    private void handlePlayerOffMap(float playerY) {
        if (playerY * Constants.PPM < 0) {
            playerIsOffMap(true);
        }
    }

    private void handlePlayerDied() {
        if (mIsPlayerDead) {
            mIsPlayerDead = false;
            mMapParser.destroyTiledMap();
            WorldUtils.destroyBodies(mWorld);
            if (mPlayerActor.getBaseModel().getPlayerLives() == PlayerModel.DEFAULT_LIVES) {
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
                        mDoorGoldActor.startAnimation();
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_BRONZE)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_BRONZE)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {
                        mDoorBronzeActor.startAnimation();
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_BLOOD)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_BLOOD)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {
                        mDoorBloodActor.startAnimation();
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.LOCKED_BLACK)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_BLACK)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {
                        mDoorBlackActor.startAnimation();
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.OTHER)) {
                    transitionLevel(DoorType.OTHER);
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.PREVIOUS)) {
                    if (mLevelModel.getLevelInt() > 0) {
                        transitionLevel(DoorType.PREVIOUS);
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.NEXT)) {
                    if (mLevelModel.getLevelInt() < Constants.gameLevels.size() - 1) {
                        transitionLevel(DoorType.NEXT);
                    }
                }
            }
        }
    }

    private void transitionLevel(String doorType) {
        for(IGameStage listener : listeners){
            listener.setTransition(doorType, true);
        }
    }

    private void loadNewLevel(int newLevel, String whichDoor) {
        alreadyEntered = true;
        mLevelModel = Constants.gameLevels.get(newLevel);

        mMapParser.destroyTiledMap();
        WorldUtils.destroyBodies(mWorld);

        mPlayerActor.setCurrentLevel(newLevel);
        LocalDataManager.savePlayerActorData(mPlayerActor.getBaseModel());

        LocalDataManager.saveLevelData(mLoadedLevelModel);

        loadLevel(Constants.gameLevels.get(newLevel), whichDoor);
    }

    @Override
    public void setPlayerActor(PlayerActor playerActor) {
        mPlayerActor = playerActor;
        mPlayerActor.addListener(this);
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

        for(IGameStage listener : listeners){
            listener.setHudHealthHearts(mPlayerActor.getBaseModel().getPlayerHearts());
            listener.resetHudShapes();
            listener.setHudHealth(mPlayerActor.getBaseModel().getPlayerHealth());
            listener.setHudLives(mPlayerActor.getBaseModel().getPlayerLives());
        }
    }

    @Override
    public void setLockedGoldDoor(DoorGoldActor doorGoldActor) {
        mDoorGoldActor = doorGoldActor;
        mDoorGoldActor.addListener(this);
    }

    @Override
    public void setLockedBronzeDoor(DoorBronzeActor bronzeDoorActor) {
        mDoorBronzeActor = bronzeDoorActor;
        mDoorBronzeActor.addListener(this);
    }

    @Override
    public void setLockedBloodDoor(DoorBloodActor bloodDoorActor) {
        mDoorBloodActor = bloodDoorActor;
        mDoorBloodActor.addListener(this);
    }

    @Override
    public void setLockedBlackDoor(DoorBlackActor blackDoorActor) {
        mDoorBlackActor = blackDoorActor;
        mDoorBlackActor.addListener(this);
    }

    @Override
    public void playerIsOffMap(boolean offMap) {
        killPlayer();
    }

    @Override
    public void playerGotHit(int playerHealth) {
        for(IGameStage listener : listeners) {
            listener.setHudHealth(playerHealth);
        }
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
        for(IGameStage listener : listeners) {
            listener.setHudLives(mPlayerActor.getBaseModel().getPlayerLives());
        }
        LocalDataManager.savePlayerActorData(mPlayerActor.getBaseModel());
        mIsPlayerDead = true;
    }

    public void quitGame() {
        LocalDataManager.savePlayerActorData(mPlayerActor.getBaseModel());
        LocalDataManager.saveLevelData(mLoadedLevelModel);
    }

    @Override
    public void shakeCamera(boolean shake) {
        mCameraManager.setShakeCamera(shake);
    }

    @Override
    public void doorAnimationComplete(BaseActor actor) {
        actor.setIsPlayingAnimation(false);
        transitionLevel(DoorType.LOCKED);
    }

    @Override
    public void hudFadeInComplete(String doorType) {
        if (doorType.equals(DoorType.OTHER)) {
            loadNewLevel(mPlayerActor.getIsAtDoorUserData().getLevelNumber(), mPlayerActor.getIsAtDoorUserData().getDestinationDoor());
        }
        else if (doorType.equals(DoorType.PREVIOUS)) {
            loadNewLevel(mLevelModel.getLevelInt() - 1, DoorType.NEXT);
        }
        else if (doorType.equals(DoorType.NEXT)) {
            loadNewLevel(mLevelModel.getLevelInt() + 1, DoorType.PREVIOUS);
        }
        else {
            mLoadedLevelModel.addOpenedDoor(mPlayerActor.getIsAtDoorUserData());
            loadNewLevel(mPlayerActor.getIsAtDoorUserData().getLevelNumber(), mPlayerActor.getIsAtDoorUserData().getDestinationDoor());
        }
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