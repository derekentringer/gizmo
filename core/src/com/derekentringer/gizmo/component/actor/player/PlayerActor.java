package com.derekentringer.gizmo.component.actor.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.player.interfaces.IPlayer;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangAmethystModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangBloodStoneModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangEmeraldModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangWoodModel;
import com.derekentringer.gizmo.model.object.BoomerangModel;
import com.derekentringer.gizmo.model.object.DropHeartModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.model.structure.door.DoorModel;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.input.UserInput;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class PlayerActor extends BaseActor {

    private static final String TAG = PlayerActor.class.getSimpleName();

    private ArrayList<IPlayer> listeners = new ArrayList<IPlayer>();

    private static final float WALKING_FORCE = 1.2f;
    private static final float RUNNING_FORCE = 1.6f;
    private static final float JUMP_FORCE = 5f;
    private static final float JUMP_FORCE_RESET = -1.5f;
    public static final int FLINCHING_LENGTH = 3;
    private static final float FLINCH_FORCE = 50f;

    private PlayerModel mPlayerModel = new PlayerModel();

    private TextureRegion[] mRunningRightSprites;
    private TextureRegion[] mRunningLeftSprites;
    private TextureRegion[] mStandingRightSprites;
    private TextureRegion[] mStandingLeftSprites;
    private TextureRegion[] mJumpUpRightSprites;
    private TextureRegion[] mJumpUpLeftSprites;
    private TextureRegion[] mJumpFallRightSprites;
    private TextureRegion[] mJumpFallLeftSprites;
    private TextureRegion[] mDiggingRightSprites;
    private TextureRegion[] mDiggingLeftSprites;

    private TextureRegion[] mFlinchingRunningLeftSprites;
    private TextureRegion[] mFlinchingRunningRightSprites;
    private TextureRegion[] mFlinchingStandingLeftSprites;
    private TextureRegion[] mFlinchingStandingRightSprites;
    private TextureRegion[] mFlinchingJumpUpLeftSprites;
    private TextureRegion[] mFlinchingJumpUpRightSprites;
    private TextureRegion[] mFlinchingJumpFallLeftSprites;
    private TextureRegion[] mFlinchingJumpFallRightSprites;

    private Texture mGizmoRunningRight;
    private Texture mGizmoRunningLeft;
    private Texture mGizmoStandingRight;
    private Texture mGizmoStandingLeft;
    private Texture mGizmoJumpUpRight;
    private Texture mGizmoJumpUpLeft;
    private Texture mGizmoFallRight;
    private Texture mGizmoFallLeft;
    private Texture mGizmoDiggingRight;
    private Texture mGizmoDiggingLeft;

    private Texture mGizmoFlinchingRunningLeft;
    private Texture mGizmoFlinchingRunningRight;
    private Texture mGizmoFlinchingStandingLeft;
    private Texture mGizmoFlinchingStandingRight;
    private Texture mGizmoFlinchingJumpUpLeft;
    private Texture mGizmoFlinchingJumpUpRight;
    private Texture mGizmoFlinchingJumpFallLeft;
    private Texture mGizmoFlinchingJumpFallRight;

    private DoorModel mIsAtDoorUserData;
    private boolean mIsOnGround;

    private Body mTouchingBodyDestroyableTop;
    private Body mTouchingBodyDestroyableRight;
    private Body mTouchingBodyDestroyableBottom;
    private Body mTouchingBodyDestroyableLeft;
    private Body mTouchingBodyDestroyableFall;

    private boolean mIsAtDoor;
    private static boolean mIsFlinching;

    private KeyModel mLastKeyAdded;
    private HeartModel mLastHeartAdded;
    private BasePlayerItemModel mLastItemAdded;

    private boolean mIsItemActive = false;

    public PlayerActor(Body body) {
        super(body);

        mGizmoRunningRight = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_running_right.png", Texture.class);
        mGizmoRunningLeft = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_running_left.png", Texture.class);
        mGizmoStandingRight = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_standing_right_large.png", Texture.class);
        mGizmoStandingLeft = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_standing_left_large.png", Texture.class);

        mGizmoJumpUpRight = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_jump_up_right_large.png", Texture.class);
        mGizmoJumpUpLeft = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_jump_up_left_large.png", Texture.class);
        mGizmoFallRight = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_jump_fall_right_large.png", Texture.class);
        mGizmoFallLeft = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_jump_fall_left_large.png", Texture.class);

        mGizmoFlinchingRunningLeft = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_running_flinching_left.png", Texture.class);
        mGizmoFlinchingRunningRight = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_running_flinching_right.png", Texture.class);
        mGizmoFlinchingStandingLeft = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_standing_left_large_flinching.png", Texture.class);
        mGizmoFlinchingStandingRight = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_standing_right_large_flinching.png", Texture.class);
        mGizmoFlinchingJumpUpRight = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_jump_up_right_large_flinching.png", Texture.class);
        mGizmoFlinchingJumpUpLeft = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_jump_up_left_large_flinching.png", Texture.class);
        mGizmoFlinchingJumpFallRight = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_jump_fall_right_large_flinching.png", Texture.class);
        mGizmoFlinchingJumpFallLeft = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_jump_fall_left_large_flinching.png", Texture.class);

        mGizmoDiggingRight = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_digging_right.png", Texture.class);
        mGizmoDiggingLeft = Gizmo.assetManager.get("res/image/character/gizmo/gizmo_digging_left.png", Texture.class);

        mRunningRightSprites = TextureRegion.split(mGizmoRunningRight, 32, 32)[0];
        mRunningLeftSprites = TextureRegion.split(mGizmoRunningLeft, 32, 32)[0];
        mStandingRightSprites = TextureRegion.split(mGizmoStandingRight, 32, 32)[0];
        mStandingLeftSprites = TextureRegion.split(mGizmoStandingLeft, 32, 32)[0];

        mJumpUpRightSprites = TextureRegion.split(mGizmoJumpUpRight, 32, 32)[0];
        mJumpUpLeftSprites = TextureRegion.split(mGizmoJumpUpLeft, 32, 32)[0];
        mJumpFallRightSprites = TextureRegion.split(mGizmoFallRight, 32, 32)[0];
        mJumpFallLeftSprites = TextureRegion.split(mGizmoFallLeft, 32, 32)[0];

        mFlinchingRunningLeftSprites = TextureRegion.split(mGizmoFlinchingRunningLeft, 32, 32)[0];
        mFlinchingRunningRightSprites = TextureRegion.split(mGizmoFlinchingRunningRight, 32, 32)[0];
        mFlinchingStandingLeftSprites = TextureRegion.split(mGizmoFlinchingStandingLeft, 32, 32)[0];
        mFlinchingStandingRightSprites = TextureRegion.split(mGizmoFlinchingStandingRight, 32, 32)[0];
        mFlinchingJumpUpRightSprites = TextureRegion.split(mGizmoFlinchingJumpUpRight, 32, 32)[0];
        mFlinchingJumpUpLeftSprites = TextureRegion.split(mGizmoFlinchingJumpUpLeft, 32, 32)[0];
        mFlinchingJumpFallRightSprites = TextureRegion.split(mGizmoFlinchingJumpFallRight, 32, 32)[0];
        mFlinchingJumpFallLeftSprites = TextureRegion.split(mGizmoFlinchingJumpFallLeft, 32, 32)[0];

        mDiggingRightSprites = TextureRegion.split(mGizmoDiggingRight, 32, 32)[0];
        mDiggingLeftSprites = TextureRegion.split(mGizmoDiggingLeft, 32, 32)[0];

        setAnimation(mRunningRightSprites, 1 / 12f);
        setFacingDirection(FACING_RIGHT);
    }

    @Override
    public PlayerModel getBaseModel() {
        return mPlayerModel;
    }

    public void addListener(IPlayer listener) {
        listeners.add(listener);
    }

    public void initPlayerData(PlayerModel playerModel) {
        mPlayerModel.setHearts(playerModel.getHearts());
        mPlayerModel.setHealth(playerModel.getHealth());
        mPlayerModel.setLives(playerModel.getLives());
        mPlayerModel.setDiggingPower(playerModel.getDiggingPower());
        mPlayerModel.setCurrentRoom(playerModel.getCurrentRoom());
        mPlayerModel.setCrystalBlueAmount(playerModel.getCrystalBlueAmount());
        if (playerModel.getKeys().size() > 0) {
            for (int i = 0; i < playerModel.getKeys().size(); i++) {
                mPlayerModel.addKey(playerModel.getKeys().get(i));
            }
        }
        if (playerModel.getItems().size() > 0) {
            for (int i = 0; i < playerModel.getItems().size(); i++) {
                mPlayerModel.addItem(playerModel.getItems().get(i));
            }
        }
    }

    public void setHitEnemy(int healthDamage) {
        if (!mIsFlinching) {
            mPlayerModel.setHealth(mPlayerModel.getHealth() - healthDamage);
            applyFlinchForce();
            for (IPlayer listener : listeners) {
                listener.playerGotHit(mPlayerModel.getHealth());
            }
        }
    }

    public void resetHealth() {
        mPlayerModel.setHealth(mPlayerModel.getHearts() * PlayerModel.HEART_HEALTH_AMOUNT);
    }

    public void resetLives() {
        mPlayerModel.setLives(mPlayerModel.DEFAULT_LIVES);
    }

    public void incrementLives() {
        if (mPlayerModel.getLives() < mPlayerModel.DEFAULT_MAX_LIVES) {
            mPlayerModel.setLives(mPlayerModel.getLives() + 1);
        }
    }

    public void deIncrementLives() {
        mPlayerModel.setLives(mPlayerModel.getLives() - 1);
        if (mPlayerModel.getLives() <= 0) {
            for (IPlayer listener : listeners) {
                listener.playerZeroLives();
            }
        }
    }

    public int getPlayerLives() {
        return mPlayerModel.getLives();
    }

    public void addKey(KeyModel keyModel) {
        if (mLastKeyAdded == null || !mLastKeyAdded.equals(keyModel)) {
            mPlayerModel.addKey(keyModel);
            mLastKeyAdded = keyModel;
        }
    }

    public boolean hasCorrectKey(String keyType) {
        ArrayList<KeyModel> playerKeys = mPlayerModel.getKeys();
        for (int i = 0; i < playerKeys.size(); i++) {
            if (playerKeys.get(i).getKeyType().equalsIgnoreCase(keyType)) {
                mPlayerModel.removeKey(keyType);
                return true;
            }
        }
        return false;
    }

    public void addHealthHeart(HeartModel heartModel) {
        if (mLastHeartAdded == null || !mLastHeartAdded.equals(heartModel)) {
            if (mPlayerModel.getHearts() < mPlayerModel.DEFAULT_MAX_HEARTS) {
                mPlayerModel.setHearts(mPlayerModel.getHearts() + 1);
                mPlayerModel.setHealth(mPlayerModel.getHearts() * PlayerModel.HEART_HEALTH_AMOUNT);
            }
            mPlayerModel.setHealth(mPlayerModel.getHearts() * PlayerModel.HEART_HEALTH_AMOUNT);
            mLastHeartAdded = heartModel;
        }
    }

    public void addHealth(DropHeartModel itemModel) {
        GLog.d(TAG, "addHealth: " + itemModel.getHealthAmount());
        mPlayerModel.setHealth(mPlayerModel.getHealth() + itemModel.getHealthAmount());
        GLog.d(TAG, "mPlayerModel.getHealth: " + mPlayerModel.getHealth());
    }

    public int getHealthHearts() {
        return mPlayerModel.getHearts();
    }

    public void addItem(BasePlayerItemModel itemModel) {
        if (mLastItemAdded == null || !mLastItemAdded.equals(itemModel)) {
            mPlayerModel.addItem(itemModel);
            mLastItemAdded = itemModel;
        }
    }

    public boolean hasCorrectItem(String itemType) {
        ArrayList<BasePlayerItemModel> playerItems = mPlayerModel.getItems();
        for (int i = 0; i < playerItems.size(); i++) {
            if (playerItems.get(i).getItemType().equalsIgnoreCase(itemType)) {
                return true;
            }
        }
        return false;
    }

    public String getPlayerBestBoomerang() {
        if (hasCorrectItem(BoomerangModel.BOOMERANG_BLOODSTONE)) {
            return BoomerangBloodStoneModel.BOOMERANG_BLOODSTONE;
        }
        else if (hasCorrectItem(BoomerangModel.BOOMERANG_AMETHYST)) {
            return BoomerangAmethystModel.BOOMERANG_AMETHYST;
        }
        else if (hasCorrectItem(BoomerangModel.BOOMERANG_EMERALD)) {
            return BoomerangEmeraldModel.BOOMERANG_EMERALD;
        }
        else if (hasCorrectItem(BoomerangModel.BOOMERANG_WOOD)) {
            return BoomerangWoodModel.BOOMERANG_WOOD;
        }
        else {
            return null;
        }
    }

    public void setCurrentRoom(int room) {
        mPlayerModel.setCurrentRoom(room);
    }

    public int getCrystalBlueAmount() {
        GLog.d(TAG, "getCrystalBlueAmount: " + mPlayerModel.getCrystalBlueAmount());
        return mPlayerModel.getCrystalBlueAmount();
    }

    public void incrementCrystalBlueAmount() {
        mPlayerModel.setCrystalBlueAmount(mPlayerModel.getCrystalBlueAmount() + 1);
        GLog.d(TAG, "total blue crystals: " + mPlayerModel.getCrystalBlueAmount());
    }

    public void jump() {
        if (mIsFlinching) {
            if (getFacingDirection() == FACING_RIGHT) {
                if (!getCurrentTextureRegion().equals(mFlinchingJumpUpRightSprites)) {
                    setAnimation(mFlinchingJumpUpRightSprites, 1 / 12f);
                }
            }
            else if (getFacingDirection() == FACING_LEFT) {
                if (!getCurrentTextureRegion().equals(mFlinchingJumpUpLeftSprites)) {
                    setAnimation(mFlinchingJumpUpLeftSprites, 1 / 12f);
                }
            }
        }
        else {
            if (getFacingDirection() == FACING_RIGHT) {
                if (!getCurrentTextureRegion().equals(mJumpUpRightSprites)) {
                    setAnimation(mJumpUpRightSprites, 1 / 12f);
                }
            }
            else if (getFacingDirection() == FACING_LEFT) {
                if (!getCurrentTextureRegion().equals(mJumpUpLeftSprites)) {
                    setAnimation(mJumpUpLeftSprites, 1 / 12f);
                }
            }
        }
        mBody.setLinearVelocity(0, JUMP_FORCE);
        playJumpSfx();
    }

    public void stopJumping() {
        if (mIsFlinching) {
            if (getFacingDirection() == FACING_RIGHT) {
                if (!getIsOnGround() && !getCurrentTextureRegion().equals(mFlinchingJumpFallRightSprites)) {
                    setAnimation(mFlinchingJumpFallRightSprites, 1 / 12f);
                }
            }
            else if (getFacingDirection() == FACING_LEFT) {
                if (!getIsOnGround() && !getCurrentTextureRegion().equals(mFlinchingJumpFallLeftSprites)) {
                    setAnimation(mFlinchingJumpFallLeftSprites, 1 / 12f);
                }
            }
        }
        else {
            if (getFacingDirection() == FACING_RIGHT) {
                if (!getIsOnGround() && !getCurrentTextureRegion().equals(mJumpFallRightSprites)) {
                    setAnimation(mJumpFallRightSprites, 1 / 12f);
                }
            }
            else if (getFacingDirection() == FACING_LEFT) {
                if (!getIsOnGround() && !getCurrentTextureRegion().equals(mJumpFallLeftSprites)) {
                    setAnimation(mJumpFallLeftSprites, 1 / 12f);
                }
            }
        }
        BodyUtils.applyLinearImpulseToBody(mBody, JUMP_FORCE_RESET, "y");
    }

    public void dig() {
        if (getFacingDirection() == FACING_RIGHT) {
            if (!getCurrentTextureRegion().equals(mDiggingRightSprites)) {
                GLog.d(TAG, "dig right");
                GLog.d(TAG, getCurrentTextureRegion().toString());
                setAnimation(mDiggingRightSprites, 1 / 12f);
            }
        }
        else {
            if (!getCurrentTextureRegion().equals(mDiggingLeftSprites)) {
                GLog.d(TAG, "dig left");
                GLog.d(TAG, getCurrentTextureRegion().toString());
                setAnimation(mDiggingLeftSprites, 1 / 12f);
            }
        }
    }

    public void moveLeft(boolean isRunning) {
        if (mIsFlinching) {
            if (getIsOnGround()
                    && !getCurrentTextureRegion().equals(mFlinchingRunningLeftSprites)) {
                setAnimation(mFlinchingRunningLeftSprites, 1 / 12f);
            }
            if (!getIsOnGround()
                    && !getCurrentTextureRegion().equals(mFlinchingJumpUpLeftSprites)) {
                setAnimation(mFlinchingJumpUpLeftSprites, 1 / 12f);
            }
        }
        else {
            if (getIsOnGround()
                    && !getCurrentTextureRegion().equals(mRunningLeftSprites)
                    && !getCurrentTextureRegion().equals(mDiggingLeftSprites)) {
                setAnimation(mRunningLeftSprites, 1 / 12f);
            }
            if (!getIsOnGround()
                    && !getCurrentTextureRegion().equals(mJumpUpLeftSprites)) {
                setAnimation(mJumpUpLeftSprites, 1 / 12f);
            }
        }

        if (isRunning) {
            BodyUtils.applyLinearImpulseToBody(mBody, -WALKING_FORCE * RUNNING_FORCE, "x");
        }
        else {
            BodyUtils.applyLinearImpulseToBody(mBody, -WALKING_FORCE, "x");
        }

        setFacingDirection(FACING_LEFT);
    }

    public void moveRight(boolean isRunning) {
        if (mIsFlinching) {
            if (getIsOnGround()
                    && !getCurrentTextureRegion().equals(mFlinchingRunningRightSprites)) {
                setAnimation(mFlinchingRunningRightSprites, 1 / 12f);
            }
            if (!getIsOnGround()
                    && !getCurrentTextureRegion().equals(mFlinchingJumpUpRightSprites)) {
                setAnimation(mFlinchingJumpUpRightSprites, 1 / 12f);
            }
        }
        else {
            if (getIsOnGround()
                    && !getCurrentTextureRegion().equals(mRunningRightSprites)
                    && !getCurrentTextureRegion().equals(mDiggingRightSprites)) {
                setAnimation(mRunningRightSprites, 1 / 12f);
            }
            if (!getIsOnGround()
                    && !getCurrentTextureRegion().equals(mJumpUpRightSprites)) {
                setAnimation(mJumpUpRightSprites, 1 / 12f);
            }
        }

        if (isRunning) {
            BodyUtils.applyLinearImpulseToBody(mBody, WALKING_FORCE * RUNNING_FORCE, "x");
        }
        else {
            BodyUtils.applyLinearImpulseToBody(mBody, WALKING_FORCE, "x");
        }

        setFacingDirection(FACING_RIGHT);
    }

    public void stoppedMoving() {
        if (mIsFlinching) {
            if (getFacingDirection() == FACING_LEFT) {
                if (getIsOnGround() && !getCurrentTextureRegion().equals(mFlinchingStandingLeftSprites)) {
                    setAnimation(mFlinchingStandingLeftSprites, 1 / 12f);
                }
            }
            else {
                if (getIsOnGround() && !getCurrentTextureRegion().equals(mFlinchingStandingRightSprites)) {
                    setAnimation(mFlinchingStandingRightSprites, 1 / 12f);
                }
            }
        }
        else {
            if (getFacingDirection() == FACING_LEFT) {
                if (getIsOnGround()
                        && !getCurrentTextureRegion().equals(mStandingLeftSprites)
                        && !UserInput.isDown(UserInput.DIG_BUTTON)) {
                    setAnimation(mStandingLeftSprites, 1 / 12f);
                }
            }
            else {
                if (getIsOnGround()
                        && !getCurrentTextureRegion().equals(mStandingRightSprites)
                        && !UserInput.isDown(UserInput.DIG_BUTTON)) {
                    setAnimation(mStandingRightSprites, 1 / 12f);
                }
            }
        }
        BodyUtils.applyLinearImpulseToBody(mBody, 0, "x");
    }

    private void applyFlinchForce() {
        if (getFacingDirection() == FACING_RIGHT) {
            BodyUtils.applyLinearImpulseToBody(mBody, -FLINCH_FORCE, "x");
        }
        else {
            BodyUtils.applyLinearImpulseToBody(mBody, FLINCH_FORCE, "x");
        }
    }

    private void playJumpSfx() {
        /*Sound jumpSfx = Gizmo.assetManager.get("res/sfx/jump.ogg", Sound.class);
        if (!Constants.IS_DEBUG) {
            jumpSfx.play();
        }*/
    }

    public static void startFlinchingTimer(final PlayerActor playerActor) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                playerActor.setIsFlinching(false);
            }

        }, PlayerActor.FLINCHING_LENGTH);
    }

    public void setIsOnGround(boolean onGround) {
        mIsOnGround = onGround;
    }

    public boolean getIsOnGround() {
        return mIsOnGround;
    }

    public Body getTouchingBodyDestroyableTop() {
        return mTouchingBodyDestroyableTop;
    }

    public void setTouchingBodyDestroyableTop(Body bodyDestroyable) {
        mTouchingBodyDestroyableTop = bodyDestroyable;
    }

    public Body getTouchingBodyDestroyableRight() {
        return mTouchingBodyDestroyableRight;
    }

    public void setTouchingBodyDestroyableRight(Body bodyDestroyable) {
        mTouchingBodyDestroyableRight = bodyDestroyable;
    }

    public Body getTouchingBodyDestroyableBottom() {
        return mTouchingBodyDestroyableBottom;
    }

    public void setTouchingBodyDestroyableBottom(Body bodyDestroyable) {
        mTouchingBodyDestroyableBottom = bodyDestroyable;
    }

    public Body getTouchingBodyDestroyableLeft() {
        return mTouchingBodyDestroyableLeft;
    }

    public void setTouchingBodyDestroyableLeft(Body bodyDestroyable) {
        mTouchingBodyDestroyableLeft = bodyDestroyable;
    }

    public void setIsAtDoor(boolean atDoor) {
        mIsAtDoor = atDoor;
    }

    public boolean getIsAtDoor() {
        return mIsAtDoor;
    }

    public void setIsAtDoorUserData(DoorModel doorUserData) {
        mIsAtDoorUserData = doorUserData;
    }

    public DoorModel getIsAtDoorUserData() {
        return mIsAtDoorUserData;
    }

    public void setIsFlinching(boolean flinching) {
        mIsFlinching = flinching;
    }

    public boolean getIsFlinching() {
        return mIsFlinching;
    }

    public boolean getIsItemActive() {
        return mIsItemActive;
    }

    public void setIsItemActive(boolean isItemActive) {
        mIsItemActive = isItemActive;
    }

}