package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.analytics.Analytics;
import com.derekentringer.gizmo.analytics.util.AnalyticsUtils;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.enemy.boss.phantom.interfaces.IPhantomBoss;
import com.derekentringer.gizmo.component.actor.enemy.boss.phantom.interfaces.IPhantomBossAttack;
import com.derekentringer.gizmo.component.actor.item.interfaces.IItems;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;
import com.derekentringer.gizmo.component.actor.player.interfaces.IPlayer;
import com.derekentringer.gizmo.component.actor.structure.door.DoorBlackActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorBloodActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorBronzeActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorGoldActor;
import com.derekentringer.gizmo.component.actor.structure.door.interfaces.IDoor;
import com.derekentringer.gizmo.component.screen.GameScreen;
import com.derekentringer.gizmo.component.stage.interfaces.IGameStage;
import com.derekentringer.gizmo.component.stage.interfaces.IHudStage;
import com.derekentringer.gizmo.manager.AchievementManager;
import com.derekentringer.gizmo.manager.CameraManager;
import com.derekentringer.gizmo.manager.ContactManager;
import com.derekentringer.gizmo.manager.DropManager;
import com.derekentringer.gizmo.manager.LocalDataManager;
import com.derekentringer.gizmo.manager.interfaces.IAchievement;
import com.derekentringer.gizmo.manager.interfaces.IDropManager;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.body.DeleteBody;
import com.derekentringer.gizmo.model.enemy.BaseEnemyModel;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangAmethystModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangBloodStoneModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangEmeraldModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangWoodModel;
import com.derekentringer.gizmo.model.object.BombModel;
import com.derekentringer.gizmo.model.object.BoomerangModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.PotionLifeModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.model.room.RoomModel;
import com.derekentringer.gizmo.model.structure.destroyable.BaseDestroyableModel;
import com.derekentringer.gizmo.model.structure.destroyable.interfaces.IDestroyable;
import com.derekentringer.gizmo.model.structure.door.DoorType;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.AnimUtils;
import com.derekentringer.gizmo.util.BlockUtils;
import com.derekentringer.gizmo.util.ItemUtils;
import com.derekentringer.gizmo.util.RoomUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;
import com.derekentringer.gizmo.util.map.MapParser;
import com.derekentringer.gizmo.util.map.interfaces.IMapParser;

import java.util.ArrayList;

public class GameStage extends BaseStage implements IMapParser, IPlayer, IDropManager, IItems, IAchievement, IHudStage, IPhantomBoss, IPhantomBossAttack, IDoor, IDestroyable, ContactListener {

    private static final String TAG = GameStage.class.getSimpleName();

    private ArrayList<IGameStage> gameStageListeners = new ArrayList<IGameStage>();
    private ArrayList<DeleteBody> mDeleteBodies = new ArrayList<DeleteBody>();

    private GameScreen mGameScreen;

    private CameraManager mCameraManager = new CameraManager();
    private DropManager mDropManager = new DropManager();
    private ContactManager mContactManager = new ContactManager();

    private MapParser mMapParser;

    private PlayerActor mPlayerActor;
    private PlayerModel mPlayerModel;
    private boolean mIsPlayerDead = false;

    private RoomModel mRoomModel;
    private RoomModel mLoadedRoomModelData;

    private DoorGoldActor mDoorGoldActor;
    private DoorBronzeActor mDoorBronzeActor;
    private DoorBloodActor mDoorBloodActor;
    private DoorBlackActor mDoorBlackActor;
    private boolean mAlreadyEntered = false;

    private boolean mStartKeyDown = false;
    private boolean mKeyDown;

    private int mAttempts = 0;

    public GameStage(GameScreen gameScreen) {
        mGameScreen = gameScreen;
    }

    public void addListener(IGameStage listener) {
        gameStageListeners.add(listener);
    }

    public void init(RoomModel room) {
        mRoomModel = room;
        mWorld.setContactListener(this);
        loadRoom(room, DoorType.DOOR_PREVIOUS);
        mCameraManager.createGameCameras();
        mDropManager.addListener(this);
        mContactManager.addListener(this);
    }

    public void loadRoom(RoomModel room, String whichDoor) {
        GLog.d(TAG, "loading room: " + room.getRoomInt());
        if (LocalDataManager.loadRoomData(room) != null) {
            mLoadedRoomModelData = LocalDataManager.loadRoomData(room);
        }
        else {
            mLoadedRoomModelData = room;
        }

        mMapParser = new MapParser(this, mLoadedRoomModelData, room.getRoomMap(), room.getRoomMidMap(), room.getRoomBackMap());
        mMapParser.addListener(this);
        mMapParser.createTileMapLayers(mWorld);
        mMapParser.createTileMapObjects(mWorld, whichDoor);

        Analytics.sendEvent("progression", "Start:" + mRoomModel.getRoomInt(), mAttempts);

        if (Gizmo.getGooglePlayServices() != null
                && room.getRoomInt() == 0) {
            Gizmo.getGooglePlayServices().unlockAchievement(AchievementManager.getAchievementsPlayServices(AchievementManager.LET_THE_ADVENTURE_BEGIN));
        }

        if (Gizmo.getGooglePlayServices() != null
                && Integer.valueOf(AnalyticsUtils.getSessionNum()) > 3) {
            Gizmo.getGooglePlayServices().unlockAchievement(AchievementManager.getAchievementsPlayServices(AchievementManager.YA_LIKE_DAGS));
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        mContactManager.setPlayerOnGround(mPlayerActor, fixtureA, fixtureB);
        mContactManager.setPlayerAtDoor(mPlayerActor, bodyA, bodyB);
        mContactManager.setPlayerTouchingDestroyable(mPlayerActor, fixtureA, fixtureB);
        mContactManager.setPlayerAttacking(mMapParser, this, mWorld, mLoadedRoomModelData, mDeleteBodies, bodyA, bodyB);
        mContactManager.setPlayerEnemyCollision(mPlayerActor, bodyA, bodyB);
        mContactManager.setPlayerPickupKey(mPlayerActor, mLoadedRoomModelData, mDeleteBodies, mMapParser, bodyA, bodyB);
        mContactManager.setPlayerPickupHeart(mPlayerActor, mLoadedRoomModelData, mDeleteBodies, mMapParser, gameStageListeners, bodyA, bodyB);
        mContactManager.setPlayerPickupLife(mPlayerActor, mLoadedRoomModelData, mDeleteBodies, mMapParser, gameStageListeners, bodyA, bodyB);
        mContactManager.setPlayerPickupItem(mPlayerActor, mLoadedRoomModelData, mDeleteBodies, bodyA, bodyB);
        mContactManager.setPlayerPickupSmallHeart(this, mPlayerActor, mDeleteBodies, bodyA, bodyB);
        mContactManager.setPlayerPickupSmallCrystalBlue(this, mPlayerActor, mDeleteBodies, bodyA, bodyB);
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

        //create dropped item actors
        addDroppedItems();

        //add animations for pickup, explode, break blocka
        addNeededAnimations();

        //handle removed actors
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

        for (BaseActor actor : mMapParser.getActorsArray()) {
            actor.render(mSpriteBatch);
            actor.setPlayerPosition(mPlayerActor.getPosition());
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

        // check to shake camera
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
        GLog.d(TAG, "dispose");
    }

    public void quitGame() {
        LocalDataManager.savePlayerActorData(mPlayerActor.getBaseModel());
        LocalDataManager.saveRoomData(mLoadedRoomModelData);
    }

    private void addDroppedItems() {
        for (int i = 0; i < mMapParser.getDroppedItemPositionArray().size(); i++) {
            mDropManager.randomDrop(mWorld, mMapParser.getDroppedItemPositionArray().get(i));
        }
        for (int i = 0; i < mMapParser.getBossDroppedItemPositionArray().size(); i++) {
            mDropManager.bossDropCrystals(mWorld, mMapParser.getBossDroppedItemPositionArray().get(i));
        }
        mMapParser.resetDroppedItemPositionArray();
        mMapParser.resetBossDroppedItemPositionArray();
    }

    private void addNeededAnimations() {
        for (int i = 0; i < mMapParser.getPickedUpHeartPositionArray().size(); i++) {
            AnimUtils.pickupHeart(mMapParser.getPickedUpHeartPositionArray().get(i), this, mMapParser, mWorld);
        }
        mMapParser.resetPickedUpHeartPositionArray();
        for (int i = 0; i < mMapParser.getPickedUpLifePositionArray().size(); i++) {
            AnimUtils.pickupLife(mMapParser.getPickedUpLifePositionArray().get(i), this, mMapParser, mWorld);
        }
        mMapParser.resetPickedUpLifePositionArray();
        for (int i = 0; i < mMapParser.getPickedUpKeyPositionArray().size(); i++) {
            AnimUtils.pickupKey(mMapParser.getPickedUpKeyPositionArray().get(i), this, mMapParser, mWorld);
        }
        mMapParser.resetPickedUpKeyPositionArray();
        for (int i = 0; i < mMapParser.getDestroyedEnemyPositionArray().size(); i++) {
            AnimUtils.destroyEnemy(mMapParser.getDestroyedEnemyPositionArray().get(i), this, mMapParser, mWorld);
        }
        mMapParser.resetDestroyedEnemyPositionArray();
        for (int i = 0; i < mMapParser.getDestroyedBlockPositionArray().size(); i++) {
            AnimUtils.breakBlock(mMapParser.getDestroyedBlockPositionArray().get(i), this, mMapParser, mWorld);
        }
        mMapParser.resetDestroyedBlockPositionArray();
    }

    private void deleteObsoleteActors() {
        for (int i = 0; i < mDeleteBodies.size(); i++) {
            //look thru delete Bodies arraylist
            //delete the associated mBody
            //delete the actor from our actorsArray
            for (int e = 0; e < mMapParser.getActorsArray().size(); e++) {
                BaseActor actorToDelete = mMapParser.getActorsArray().get(e);
                if (actorToDelete.getBaseModel().equals(mDeleteBodies.get(i).getBaseModel())) {

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
            //TODO could load first room of multiple rooms
            //TODO ie, need to pass which door in the room
            loadRoom(mRoomModel, DoorType.DOOR_PREVIOUS);

            mAttempts++;
            Analytics.sendEvent("progression", "Fail:" + mRoomModel.getRoomInt(), mAttempts);
        }
    }

    public void handleInput() {
        if (UserInput.isDown(UserInput.BACK_BUTTON)) {
            if (mStartKeyDown == false) {
                mStartKeyDown = true;
                if (!GameScreen.GameState.PAUSED.equals(GameScreen.GameState.PAUSED)) {
                    mGameScreen.pauseGame();
                }
            }
        }

        //pause game
        if (UserInput.isDown(UserInput.START_BUTTON)) {
            if (mStartKeyDown == false) {
                mStartKeyDown = true;
                mGameScreen.pauseGame();
            }
        }

        // walk left
        if (UserInput.isDown(UserInput.LEFT_BUTTON)
                && !UserInput.isDown(UserInput.RUN_BUTTON)) {
            mAlreadyEntered = false;
            mPlayerActor.moveLeft(false);
        }

        // run left
        if (UserInput.isDown(UserInput.LEFT_BUTTON)
                && UserInput.isDown(UserInput.RUN_BUTTON)) {
            mAlreadyEntered = false;
            mPlayerActor.moveLeft(true);
        }

        // walk right
        if (UserInput.isDown(UserInput.RIGHT_BUTTON)
                && !UserInput.isDown(UserInput.RUN_BUTTON)) {
            mAlreadyEntered = false;
            mPlayerActor.moveRight(false);
        }

        // run right
        if (UserInput.isDown(UserInput.RIGHT_BUTTON)
                && UserInput.isDown(UserInput.RUN_BUTTON)) {
            mAlreadyEntered = false;
            mPlayerActor.moveRight(true);
        }

        // stop running/walking
        if (!UserInput.isDown(UserInput.LEFT_BUTTON)
                && !UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            mPlayerActor.stoppedMoving();
        }

        // jump
        if (UserInput.isDown(UserInput.JUMP_BUTTON)) {
            if (mPlayerActor.getIsOnGround()) {
                mPlayerActor.setIsOnGround(false);
                mPlayerActor.jump();
            }
        }

        // stop jumping
        if (!UserInput.isDown(UserInput.JUMP_BUTTON)) {
            mPlayerActor.stopJumping();
        }

        // dig
        if (UserInput.isDown(UserInput.DIG_BUTTON)) {
            if (mPlayerActor.getIsOnGround()) {
                mPlayerActor.dig();
                if (UserInput.isDown(UserInput.RIGHT_BUTTON) && mPlayerActor.getTouchingBodyDestroyableRight() != null) {
                    BlockUtils.setBlockHealth(mPlayerActor.getTouchingBodyDestroyableRight(), mPlayerModel.getDiggingPower());
                    if (BlockUtils.getBlockHealth(mPlayerActor.getTouchingBodyDestroyableRight()) <= 0) {
                        if (BlockUtils.getBlockDropsLoot(mPlayerActor.getTouchingBodyDestroyableRight())) {
                            mMapParser.addToDroppedItemPositionArray(mPlayerActor.getTouchingBodyDestroyableRight().getPosition());
                        }
                        mDeleteBodies.add(new DeleteBody((BaseDestroyableModel) mPlayerActor.getTouchingBodyDestroyableRight().getUserData(), mPlayerActor.getTouchingBodyDestroyableRight()));
                        mLoadedRoomModelData.addDestroyedBlock((BaseDestroyableModel) mPlayerActor.getTouchingBodyDestroyableRight().getUserData());

                        mMapParser.addToDestroyedBlockPositionArray(mPlayerActor.getTouchingBodyDestroyableRight().getPosition());
                        mPlayerActor.setTouchingBodyDestroyableRight(null);
                    }
                }
                //TODO java.lang.ClassCastException: com.derekentringer.gizmo.model.block.BlockBreakModel cannot be cast to com.derekentringer.gizmo.model.structure.destroyable.BaseDestroyableModel
                //at com.derekentringer.gizmo.util.BlockUtils.setBlockHealth(BlockUtils.java:23)
                else if (UserInput.isDown(UserInput.LEFT_BUTTON) && mPlayerActor.getTouchingBodyDestroyableLeft() != null) {
                    BlockUtils.setBlockHealth(mPlayerActor.getTouchingBodyDestroyableLeft(), mPlayerModel.getDiggingPower());
                    if (BlockUtils.getBlockHealth(mPlayerActor.getTouchingBodyDestroyableLeft()) <= 0) {
                        if (BlockUtils.getBlockDropsLoot(mPlayerActor.getTouchingBodyDestroyableLeft())) {
                            mMapParser.addToDroppedItemPositionArray(mPlayerActor.getTouchingBodyDestroyableLeft().getPosition());
                        }
                        mDeleteBodies.add(new DeleteBody((BaseDestroyableModel) mPlayerActor.getTouchingBodyDestroyableLeft().getUserData(), mPlayerActor.getTouchingBodyDestroyableLeft()));
                        mLoadedRoomModelData.addDestroyedBlock((BaseDestroyableModel) mPlayerActor.getTouchingBodyDestroyableLeft().getUserData());

                        mMapParser.addToDestroyedBlockPositionArray(mPlayerActor.getTouchingBodyDestroyableLeft().getPosition());
                        mPlayerActor.setTouchingBodyDestroyableLeft(null);
                    }
                }
                else if (mPlayerActor.getTouchingBodyDestroyableBottom() != null) {
                    BlockUtils.setBlockHealth(mPlayerActor.getTouchingBodyDestroyableBottom(), mPlayerModel.getDiggingPower());
                    if (BlockUtils.getBlockHealth(mPlayerActor.getTouchingBodyDestroyableBottom()) <= 0) {
                        if (BlockUtils.getBlockDropsLoot(mPlayerActor.getTouchingBodyDestroyableBottom())) {
                            mMapParser.addToDroppedItemPositionArray(mPlayerActor.getTouchingBodyDestroyableBottom().getPosition());
                        }
                        mDeleteBodies.add(new DeleteBody((BaseDestroyableModel) mPlayerActor.getTouchingBodyDestroyableBottom().getUserData(), mPlayerActor.getTouchingBodyDestroyableBottom()));
                        mLoadedRoomModelData.addDestroyedBlock((BaseDestroyableModel) mPlayerActor.getTouchingBodyDestroyableBottom().getUserData());

                        mMapParser.addToDestroyedBlockPositionArray(mPlayerActor.getTouchingBodyDestroyableBottom().getPosition());
                        mPlayerActor.setTouchingBodyDestroyableBottom(null);
                    }
                }
            }
        }

        // enter a door
        if (UserInput.isDown(UserInput.ENTER_DOOR)) {
            if (mAlreadyEntered) {
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
                    transitionRoom(DoorType.DOOR_OTHER);
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.DOOR_PREVIOUS)) {
                    if (mRoomModel.getRoomInt() > 0) {
                        transitionRoom(DoorType.DOOR_PREVIOUS);
                    }
                }
                else if (mPlayerActor.getIsAtDoorUserData().getDoorType().equals(DoorType.DOOR_NEXT)) {
                    if (mRoomModel.getRoomInt() < RoomUtils.rooms.size() - 1) {
                        transitionRoom(DoorType.DOOR_NEXT);
                    }
                }
            }
        }

        //primary items
        if (UserInput.isDown(UserInput.SWITCH_PRIMARY_BUTTON_BACKWARD)) {
            if (!mKeyDown) {
                mKeyDown = true;
                UserInput.resetKey(UserInput.SWITCH_PRIMARY_BUTTON_BACKWARD, false);
                mPlayerActor.deincrementSelectedPrimaryItem();
                for (IGameStage listener : gameStageListeners) {
                    listener.setHudSelectedPrimaryItem(mPlayerActor.getBaseModel().getCurrentlySelectedItemPrimary());
                }
            }
        }

        if (UserInput.isDown(UserInput.SWITCH_PRIMARY_BUTTON_FORWARD)) {
            if (!mKeyDown) {
                mKeyDown = true;
                UserInput.resetKey(UserInput.SWITCH_PRIMARY_BUTTON_FORWARD, false);
                mPlayerActor.incrementSelectedPrimaryItem();
                for (IGameStage listener : gameStageListeners) {
                    listener.setHudSelectedPrimaryItem(mPlayerActor.getBaseModel().getCurrentlySelectedItemPrimary());
                }
            }
        }

        //secondary items
        if (UserInput.isDown(UserInput.SWITCH_SECONDARY_BUTTON_BACKWARD)) {
            if (!mKeyDown) {
                mKeyDown = true;
                UserInput.resetKey(UserInput.SWITCH_SECONDARY_BUTTON_BACKWARD, false);
                mPlayerActor.deincrementSelectedSecondaryItem();
                for (IGameStage listener : gameStageListeners) {
                    listener.setHudSelectedSecondaryItem(mPlayerActor.getBaseModel().getCurrentlySelectedItemSecondary(),
                            mPlayerActor.getBaseModel().getItemCount(mPlayerActor.getBaseModel().getCurrentlySelectedItemSecondary()));
                }
            }
        }

        if (UserInput.isDown(UserInput.SWITCH_SECONDARY_BUTTON_FORWARD)) {
            if (!mKeyDown) {
                mKeyDown = true;
                UserInput.resetKey(UserInput.SWITCH_SECONDARY_BUTTON_FORWARD, false);
                mPlayerActor.incrementSelectedSecondaryItem();
                for (IGameStage listener : gameStageListeners) {
                    listener.setHudSelectedSecondaryItem(mPlayerActor.getBaseModel().getCurrentlySelectedItemSecondary(),
                            mPlayerActor.getBaseModel().getItemCount(mPlayerActor.getBaseModel().getCurrentlySelectedItemSecondary()));
                }
            }
        }

        // attack & kill stuff
        if (UserInput.isDown(UserInput.ATTACK_BUTTON)) {
            if (mPlayerActor.getCurrentPrimaryItem() != null) {

                //boomerangs
                if (mPlayerActor.getCurrentPrimaryItem().getItemType().contains(BoomerangModel.BOOMERANG)) {

                    //TODO should probably only show the best boomerang unless boomerangs have different effects besides damage
                    //String playerBestBoomerang = mPlayerActor.getPlayerBestBoomerang();
                    //if (playerBestBoomerang != null) {

                        if (!mPlayerActor.getIsItemActive()) {
                            mPlayerActor.setIsItemActive(true);

                            if (mPlayerActor.getCurrentPrimaryItem().getItemType().equalsIgnoreCase(BoomerangWoodModel.BOOMERANG_WOOD)) {
                                ItemUtils.createWoodBoomerang(mWorld, mPlayerActor, mMapParser, this);
                            }
                            else if (mPlayerActor.getCurrentPrimaryItem().getItemType().equalsIgnoreCase(BoomerangEmeraldModel.BOOMERANG_EMERALD)) {
                                ItemUtils.createEmeraldBoomerang(mWorld, mPlayerActor, mMapParser, this);
                            }
                            else if (mPlayerActor.getCurrentPrimaryItem().getItemType().equalsIgnoreCase(BoomerangAmethystModel.BOOMERANG_AMETHYST)) {
                                ItemUtils.createAmethystBoomerang(mWorld, mPlayerActor, mMapParser, this);
                            }
                            else if (mPlayerActor.getCurrentPrimaryItem().getItemType().equalsIgnoreCase(BoomerangBloodStoneModel.BOOMERANG_BLOODSTONE)) {
                                ItemUtils.createBloodStoneBoomerang(mWorld, mPlayerActor, mMapParser, this);
                            }
                        }

                    //}
                }

                //todo add other primary items

            }
        }

        if (UserInput.isDown(UserInput.SECONDARY_BUTTON)) {
            if (!mKeyDown) {
                mKeyDown = true;
                if (mPlayerActor.getCurrentSecondaryItem() != null) {
                    GLog.d(TAG, "using secondary item: " + mPlayerActor.getCurrentSecondaryItem().getItemType());

                    if (mPlayerActor.getCurrentSecondaryItem().getItemType().equalsIgnoreCase(PotionLifeModel.POTION_LIFE)) {
                        ItemUtils.usePotionLife(mPlayerActor);
                        mMapParser.addToPickedUpHeartPositionArray(mPlayerActor.getPlayerPosition());
                        for (IGameStage listener : gameStageListeners) {
                            listener.setHudHealthHearts(mPlayerActor.getHealthHearts());
                        }
                    }
                    else if (mPlayerActor.getCurrentSecondaryItem().getItemType().equalsIgnoreCase(BombModel.BOMB)) {
                        ItemUtils.useBomb(mWorld, mPlayerActor, mMapParser, this);
                    }

                    //todo add other secondary items

                    for (IGameStage listener : gameStageListeners) {
                        listener.setHudSelectedSecondaryItem(mPlayerActor.getCurrentSecondaryItem(),
                                mPlayerActor.getBaseModel().getItemCount(mPlayerActor.getCurrentSecondaryItem()));
                    }
                }
            }
        }

        //reset keyDown flag
        if (!UserInput.isDown(UserInput.SWITCH_PRIMARY_BUTTON_BACKWARD)
                && !UserInput.isDown(UserInput.SWITCH_PRIMARY_BUTTON_FORWARD)
                && !UserInput.isDown(UserInput.SWITCH_SECONDARY_BUTTON_BACKWARD)
                && !UserInput.isDown(UserInput.SWITCH_SECONDARY_BUTTON_FORWARD)
                && !UserInput.isDown(UserInput.SECONDARY_BUTTON)) {
            mKeyDown = false;
        }

        //reset pause toggle flag
        if (!UserInput.isDown(UserInput.START_BUTTON)) {
            mStartKeyDown = false;
        }
    }

    private void transitionRoom(String doorType) {
        for (IGameStage listener : gameStageListeners) {
            listener.setTransition(doorType, true);
        }
    }

    private void loadNewRoom(int currentRoom, int newRoom, String whichDestDoor) {
        mAlreadyEntered = true;
        mPlayerActor.setIsItemActive(false);

        mRoomModel = RoomUtils.rooms.get(newRoom);

        mMapParser.destroyTiledMap();
        WorldUtils.destroyBodies(mWorld);

        mPlayerActor.setCurrentRoom(newRoom);

        LocalDataManager.savePlayerActorData(mPlayerActor.getBaseModel());
        LocalDataManager.saveRoomData(mLoadedRoomModelData);

        loadRoom(RoomUtils.rooms.get(newRoom), whichDestDoor);

        mAttempts = 0;
        Analytics.sendEvent("progression", "Complete:" + currentRoom, mAttempts);
    }

    @Override
    public void setPlayerActor(PlayerActor playerActor) {
        mPlayerActor = playerActor;
        mPlayerActor.addListener(this);
        mPlayerActor.setIsItemActive(false);
        if (LocalDataManager.loadPlayerActorData() != null) {
            mPlayerModel = LocalDataManager.loadPlayerActorData();
            playerActor.initPlayerData(mPlayerModel);
        }
        else {
            mPlayerModel = new PlayerModel();
            mPlayerModel.setHearts(PlayerModel.DEFAULT_HEARTS);
            mPlayerModel.setHealth(PlayerModel.DEFAULT_HEALTH);
            mPlayerModel.setLives(PlayerModel.DEFAULT_LIVES);
            mPlayerModel.setCurrentRoom(PlayerModel.DEFAULT_ROOM);
            mPlayerModel.setDiggingPower(PlayerModel.DEFAULT_DIGGING_POWER);
            mPlayerModel.setCrystalBlueAmount(PlayerModel.DEFAULT_CRYSTAL_BLUE);
            playerActor.initPlayerData(mPlayerModel);
            LocalDataManager.savePlayerActorData(mPlayerModel);
        }

        updateHud();
    }

    public void updateHud() {
        for (IGameStage listener : gameStageListeners) {
            listener.setHudHealthHearts(mPlayerActor.getBaseModel().getHearts());
            listener.resetHudShapes();
            listener.setHudHealth(mPlayerActor.getBaseModel().getHealth());
            listener.setHudLives(mPlayerActor.getBaseModel().getLives());
            listener.setCrystalCount(mPlayerActor.getBaseModel().getCrystalBlueAmount());
            listener.setHudSelectedPrimaryItem(mPlayerActor.getBaseModel().getCurrentlySelectedItemPrimary());
            listener.setHudSelectedSecondaryItem(mPlayerActor.getBaseModel().getCurrentlySelectedItemSecondary(),
                    mPlayerActor.getBaseModel().getItemCount(mPlayerActor.getCurrentSecondaryItem()));
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
    public void setCurrentlySelectedItemPrimary(BasePlayerItemModel item) {
        for (IGameStage listener : gameStageListeners) {
            listener.setHudSelectedPrimaryItem(item);
        }
    }

    @Override
    public void setCurrentlySelectedItemSecondary(BasePlayerItemModel item) {
        for (IGameStage listener : gameStageListeners) {
            listener.setHudSelectedSecondaryItem(item,
                    mPlayerActor.getBaseModel().getItemCount(item));
        }
    }

    @Override
    public void updateSelectedSecondaryItemCount(int numItems) {
        for (IGameStage listener : gameStageListeners) {
            listener.updateSelectedSecondaryItemCount(numItems);
        }
    }

    @Override
    public void playerIsOffMap(boolean offMap) {
        killPlayer();
    }

    @Override
    public void playerGotHit(int playerHealth) {
        for (IGameStage listener : gameStageListeners) {
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
        for (IGameStage listener : gameStageListeners) {
            listener.setHudLives(mPlayerActor.getBaseModel().getLives());
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
        transitionRoom(DoorType.DOOR_LOCKED);
    }

    @Override
    public void hudFadeInComplete(String doorType) {
        if (mPlayerActor.getIsAtDoorUserData().getRoomNumber() != -1
                && mPlayerActor.getIsAtDoorUserData().getDestinationDoor() != null) {
            mLoadedRoomModelData.addOpenedDoor(mPlayerActor.getIsAtDoorUserData());
            loadNewRoom(mRoomModel.getRoomInt(), mPlayerActor.getIsAtDoorUserData().getRoomNumber(), mPlayerActor.getIsAtDoorUserData().getDestinationDoor());
        }
    }

    @Override
    public void removePlayerItemFromStage(BaseActor actor) {
        mPlayerActor.setIsItemActive(false);
        mDeleteBodies.add(new DeleteBody((BaseModel) actor.getBody().getUserData(), actor.getBody()));
    }

    @Override
    public void addDroppedItem(BaseActor actor) {
        mMapParser.addToTempActorsArray(actor);
    }

    @Override
    public void removeBlockFromMap(BaseActor baseActor) {
        mLoadedRoomModelData.addDestroyedBlock((BaseDestroyableModel) baseActor.getBody().getUserData());
    }

    @Override
    public void achievementPlayerPickedUpItem(BasePlayerItemModel item) {
        if (Gizmo.getGooglePlayServices() != null
                && item.getItemType().equalsIgnoreCase(BoomerangWoodModel.BOOMERANG_WOOD)) {
            Gizmo.getGooglePlayServices().unlockAchievement(AchievementManager.getAchievementsPlayServices(AchievementManager.BOOMERANG_WOOD));
        }
    }

    @Override
    public void achievementPlayerKilledEnemy(BaseEnemyModel enemy) {
        if (Gizmo.getGooglePlayServices() != null) {
            if (enemy.isBoss()) {
                Gizmo.getGooglePlayServices().unlockAchievement(AchievementManager.getAchievementsPlayServices(AchievementManager.PHANTOM_BE_GONE));
            }
            else {
                Gizmo.getGooglePlayServices().unlockAchievement(AchievementManager.getAchievementsPlayServices(AchievementManager.HEADSHOT));
            }
        }
    }

    /*private void startBackgroundMusic() {
        Music backgroundMusic = Gizmo.mAssetManager.get("res/music/background.ogg", Music.class);
        if(!Constants.IS_DEBUGGING) {
            backgroundMusic.play();
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
        }
    }*/

}