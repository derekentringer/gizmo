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
import com.derekentringer.gizmo.components.actor.boss.PhantomBossActor;
import com.derekentringer.gizmo.components.actor.boss.interfaces.IPhantomBoss;
import com.derekentringer.gizmo.components.actor.boss.interfaces.IPhantomBossAttack;
import com.derekentringer.gizmo.components.actor.enemy.PhantomActor;
import com.derekentringer.gizmo.components.actor.item.IItems;
import com.derekentringer.gizmo.components.actor.player.PlayerActor;
import com.derekentringer.gizmo.components.actor.player.interfaces.IPlayer;
import com.derekentringer.gizmo.components.actor.item.BoomerangWoodActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBlackActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBloodActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBronzeActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorGoldActor;
import com.derekentringer.gizmo.components.actor.structure.door.interfaces.IDoor;
import com.derekentringer.gizmo.components.stage.interfaces.IGameStage;
import com.derekentringer.gizmo.components.stage.interfaces.IHudStage;
import com.derekentringer.gizmo.manager.CameraManager;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.body.DeleteBody;
import com.derekentringer.gizmo.model.enemy.PhantomLargeModel;
import com.derekentringer.gizmo.model.enemy.PhantomModel;
import com.derekentringer.gizmo.model.level.LevelModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.LifeModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.model.player.item.BoomerangWoodModel;
import com.derekentringer.gizmo.model.structure.DoorModel;
import com.derekentringer.gizmo.model.structure.DoorType;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.FixtureUtils;
import com.derekentringer.gizmo.util.GameLevelUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;
import com.derekentringer.gizmo.util.map.ItemUtils;
import com.derekentringer.gizmo.util.map.MapParser;
import com.derekentringer.gizmo.util.map.interfaces.IMapParser;

import java.util.ArrayList;

public class GameStage extends Stage implements IMapParser, IPlayer, IItems, IHudStage, IPhantomBoss, IPhantomBossAttack, IDoor, ContactListener {

    private static final String TAG = GameStage.class.getSimpleName();

    private ArrayList<IGameStage> listeners = new ArrayList<IGameStage>();
    private ArrayList<DeleteBody> mDeleteBodies = new ArrayList<DeleteBody>();

    private CameraManager mCameraManager = new CameraManager();

    private World mWorld;
    private MapParser mMapParser;
    private SpriteBatch mSpriteBatch;

    private PlayerActor mPlayerActor;
    private PlayerModel mPlayerModel;
    private boolean mIsPlayerDead = false;

    private LevelModel mLevelModel;
    private LevelModel mLoadedLevelModel;

    private DoorGoldActor mDoorGoldActor;
    private DoorBronzeActor mDoorBronzeActor;
    private DoorBloodActor mDoorBloodActor;
    private DoorBlackActor mDoorBlackActor;
    private boolean alreadyEntered = false;

    private boolean alreadyActivated = false;

    private boolean isItemActive;

    public GameStage() {
    }

    public void addListener(IGameStage listener) {
        listeners.add(listener);
    }

    public void init(LevelModel level) {
        mLevelModel = level;
        setupWorld();
        loadLevel(level, DoorType.DOOR_PREVIOUS);
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



        // player attack collisions
        if (BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER_ITEM) && BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.ENEMY)) {
            GLog.d(TAG, "HIT ENEMY");
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.PLAYER_ITEM) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.ENEMY)) {
            GLog.d(TAG, "HIT ENEMY");
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

            for (IGameStage listener : listeners) {
                listener.setHudHealthHearts(mPlayerActor.getHealthHearts());
            }

            mDeleteBodies.add(new DeleteBody((HeartModel) a.getBody().getUserData(), a.getBody()));
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.HEART) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.addHealthHeart((HeartModel) b.getBody().getUserData());
            mLoadedLevelModel.addPickedUpHeart((HeartModel) b.getBody().getUserData());

            for (IGameStage listener : listeners) {
                listener.setHudHealthHearts(mPlayerActor.getHealthHearts());
            }

            mDeleteBodies.add(new DeleteBody((HeartModel) b.getBody().getUserData(), b.getBody()));
        }

        // pickup a life
        if (BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.LIFE) && BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.incrementLives();
            mLoadedLevelModel.addPickedUpLife((LifeModel) a.getBody().getUserData());

            for (IGameStage listener : listeners) {
                listener.setHudLives(mPlayerActor.getPlayerLives());
            }

            mDeleteBodies.add(new DeleteBody((LifeModel) a.getBody().getUserData(), a.getBody()));
        }
        else if (BodyUtils.bodyTypeCheck(b.getBody(), BaseModelType.LIFE) && BodyUtils.bodyTypeCheck(a.getBody(), BaseModelType.PLAYER)) {
            mPlayerActor.incrementLives();
            mLoadedLevelModel.addPickedUpLife((LifeModel) b.getBody().getUserData());

            for (IGameStage listener : listeners) {
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

        // handle removed actors
        updateMapParserArrays();
        deleteObsoleteActors();

        //tiled maps render camera updates
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

        // actor render loop
        for (BaseActor actor : mMapParser.getActorsArray()) {
            actor.render(mSpriteBatch);
            if (actor.getName().equalsIgnoreCase(PhantomModel.PHANTOM)) {
                ((PhantomActor) actor).setPlayerPosition(mPlayerActor.getPosition());
            }
            else if (actor.getName().equalsIgnoreCase(PhantomLargeModel.PHANTOM_LARGE)) {
                ((PhantomBossActor) actor).setPlayerPosition(mPlayerActor.getPosition());
            }
            else if (actor.getName().equalsIgnoreCase(BoomerangWoodModel.BOOMERANG_WOOD)) {
                ((BoomerangWoodActor) actor).setPlayerPosition(mPlayerActor.getPosition());
            }
        }

        // checks for the player position
        handlePlayerOffMap(mPlayerActor.getPosition().y);
        handlePlayerDied();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // input
        UserInput.update();
        handleInput();

        //add check to shake camera here
        if (!mCameraManager.getShakeCamera()) {
            mCameraManager.updateCameraPlayerMovement(mPlayerActor.getPosition().x, mPlayerActor.getPosition().y, mMapParser);
        }
        else {
            mCameraManager.updateCameraPlayerMovement(WorldUtils.generatRandomPositiveNegativeValue(mPlayerActor.getPosition().x, -mPlayerActor.getPosition().x), WorldUtils.generatRandomPositiveNegativeValue(mPlayerActor.getPosition().y, -mPlayerActor.getPosition().y), mMapParser);
        }

        // actor loops
        for (BaseActor actor : mMapParser.getActorsArray()) {
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

    @Override
    public void dispose() {
        mPlayerActor.remove();
        mMapParser.dispose();
    }

    public void quitGame() {
        LocalDataManager.savePlayerActorData(mPlayerActor.getBaseModel());
        LocalDataManager.saveLevelData(mLoadedLevelModel);
    }

    private void deleteObsoleteActors() {
        for (int i = 0; i < mDeleteBodies.size(); i++) {
            //look thru delete Bodies arraylist
            //delete the associated mBody
            //delete the actor from our actorsArray
            for (int e = 0; e < mMapParser.getActorsArray().size(); e++) {
                BaseActor actorToDelete = mMapParser.getActorsArray().get(e);
                if (actorToDelete.mBaseModel.equals(mDeleteBodies.get(i).getBaseModel())) {

                    GLog.d(TAG, "DELETING OBSOLETE ACTOR: " + mMapParser.getActorsArray().get(e));

                    mMapParser.getActorsArray().remove(e);
                    actorToDelete.remove();
                    //delete the mBody
                    WorldUtils.destroyBody(mWorld, mDeleteBodies.get(i).getBody());
                    mDeleteBodies.remove(i);
                    break;
                }
            }
        }

        // remove any actor from actor array that
        // falls off the stage or goes out of bounds
        for (int j = 0; j < mMapParser.getActorsArray().size(); j++) {
            if (mMapParser.getActorsArray().get(j).getPosition().y * Constants.PPM < 0
                    || mMapParser.getActorsArray().get(j).getPosition().x * Constants.PPM < 0) {

                GLog.d(TAG, "ACTOR OFF STAGE " + mMapParser.getActorsArray().get(j));

                mMapParser.getActorsArray().get(j).remove();
                mMapParser.getActorsArray().remove(j);
            }
        }
    }

    private void updateMapParserArrays() {
        for (int i = 0; i < mMapParser.getTempActorsArray().size(); i++) {
            mMapParser.addToActorsArray(mMapParser.getTempActorsArray().get(i));
        }
        mMapParser.getTempActorsArray().clear();
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
                loadLevel(GameLevelUtils.gameLevels.get(0), DoorType.DOOR_PREVIOUS);
            }
            else {
                loadLevel(mLevelModel, DoorType.DOOR_PREVIOUS);
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
                if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.DOOR_LOCKED_GOLD)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_GOLD)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {
                        mDoorGoldActor.startAnimation();
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.DOOR_LOCKED_BRONZE)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_BRONZE)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {
                        mDoorBronzeActor.startAnimation();
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.DOOR_LOCKED_BLOOD)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_BLOOD)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {
                        mDoorBloodActor.startAnimation();
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.DOOR_LOCKED_BLACK)) {
                    if (mPlayerActor.hasCorrectKey(KeyModel.KEY_BLACK)
                            || !mPlayerActor.getIsAtDoorUserData().getIsLocked()) {
                        mDoorBlackActor.startAnimation();
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.DOOR_OTHER)) {
                    transitionLevel(DoorType.DOOR_OTHER);
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.DOOR_PREVIOUS)) {
                    if (mLevelModel.getLevelInt() > 0) {
                        transitionLevel(DoorType.DOOR_PREVIOUS);
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.DOOR_NEXT)) {
                    if (mLevelModel.getLevelInt() < GameLevelUtils.gameLevels.size() - 1) {
                        transitionLevel(DoorType.DOOR_NEXT);
                    }
                }
            }
        }

        if (UserInput.isDown(UserInput.ATTACK)) {
            if (!isItemActive) {
                isItemActive = true;
                BoomerangWoodActor boomerangWoodActor = new BoomerangWoodActor(ItemUtils.createBoomerang(new BoomerangWoodModel(), mWorld, mPlayerActor.getPosition()), mPlayerActor.getFacingDirection());
                boomerangWoodActor.setName(BoomerangWoodModel.BOOMERANG_WOOD);
                mMapParser.addToTempActorsArray(boomerangWoodActor);
                boomerangWoodActor.addListener(this);
            }
        }
    }

    private void transitionLevel(String doorType) {
        for (IGameStage listener : listeners) {
            listener.setTransition(doorType, true);
        }
    }

    private void loadNewLevel(int newLevel, String whichDoor) {
        alreadyEntered = true;
        mLevelModel = GameLevelUtils.gameLevels.get(newLevel);

        mMapParser.destroyTiledMap();
        WorldUtils.destroyBodies(mWorld);

        mPlayerActor.setCurrentLevel(newLevel);
        LocalDataManager.savePlayerActorData(mPlayerActor.getBaseModel());

        LocalDataManager.saveLevelData(mLoadedLevelModel);

        loadLevel(GameLevelUtils.gameLevels.get(newLevel), whichDoor);
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

        for (IGameStage listener : listeners) {
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
        for (IGameStage listener : listeners) {
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
        for (IGameStage listener : listeners) {
            listener.setHudLives(mPlayerActor.getBaseModel().getPlayerLives());
        }
        mCameraManager.setShakeCamera(false);
        LocalDataManager.savePlayerActorData(mPlayerActor.getBaseModel());
        mIsPlayerDead = true;
    }

    @Override
    public void phantomBossShakeCamera(boolean shake) {
        mCameraManager.setShakeCamera(shake);
    }

    @Override
    public void phantomBossAddActor(BaseActor actor) {
        mMapParser.addToTempActorsArray(actor);
    }

    @Override
    public void doorAnimationComplete(BaseActor actor) {
        actor.setIsPlayingAnimation(false);
        transitionLevel(DoorType.DOOR_LOCKED);
    }

    @Override
    public void hudFadeInComplete(String doorType) {
        if (doorType.equals(DoorType.DOOR_OTHER)) {
            loadNewLevel(mPlayerActor.getIsAtDoorUserData().getLevelNumber(), mPlayerActor.getIsAtDoorUserData().getDestinationDoor());
        }
        else if (doorType.equals(DoorType.DOOR_PREVIOUS)) {
            loadNewLevel(mLevelModel.getLevelInt() - 1, DoorType.DOOR_NEXT);
        }
        else if (doorType.equals(DoorType.DOOR_NEXT)) {
            loadNewLevel(mLevelModel.getLevelInt() + 1, DoorType.DOOR_PREVIOUS);
        }
        else {
            mLoadedLevelModel.addOpenedDoor(mPlayerActor.getIsAtDoorUserData());
            loadNewLevel(mPlayerActor.getIsAtDoorUserData().getLevelNumber(), mPlayerActor.getIsAtDoorUserData().getDestinationDoor());
        }
    }

    @Override
    public void removePlayerItemFromStage(BaseActor actor) {
        isItemActive = false;
        mDeleteBodies.add(new DeleteBody((BaseModel) actor.getBody().getUserData(), actor.getBody()));
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