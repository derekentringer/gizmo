package com.derekentringer.gizmo.components.actor.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.model.structure.DoorModel;
import com.derekentringer.gizmo.components.actor.player.interfaces.IPlayerDelegate;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.BodyUtils;

import java.util.ArrayList;

public class PlayerActor extends BaseActor {

    private static final float RUNNING_FORCE = 1f;
    private static final float JUMP_FORCE = 5f;
    private static final float JUMP_FORCE_RESET = -1.2f;
    public static final int FLINCHING_LENGTH = 3;
    private static final float FLINCH_FORCE = 50f;

    private PlayerModel mPlayerModel = new PlayerModel();

    public IPlayerDelegate delegate = null;

    private TextureRegion[] mRunningRightSprites;
    private TextureRegion[] mRunningLeftSprites;
    private TextureRegion[] mStandingRightSprites;
    private TextureRegion[] mStandingLeftSprites;
    private TextureRegion[] mJumpUpRightSprites;
    private TextureRegion[] mJumpUpLeftSprites;
    private TextureRegion[] mJumpFallRightSprites;
    private TextureRegion[] mJumpFallLeftSprites;

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
    private Texture mGizmoJumpUpRightSprites;
    private Texture mGizmoJumpUpLeftSprites;
    private Texture mGizmoFallRightSprites;
    private Texture mGizmoFallLeftSprites;

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
    private boolean mIsAtDoor;
    private static boolean mIsFlinching;

    private KeyModel mLastKeyAdded;
    private HeartModel mLastHeartAdded;

    public PlayerActor(Body body) {
        super(body);

        mGizmoRunningRight = Gizmo.assetManager.get("res/images/gizmo_running_right.png", Texture.class);
        mGizmoRunningLeft = Gizmo.assetManager.get("res/images/gizmo_running_left.png", Texture.class);
        mGizmoStandingRight = Gizmo.assetManager.get("res/images/gizmo_standing_right_large.png", Texture.class);
        mGizmoStandingLeft = Gizmo.assetManager.get("res/images/gizmo_standing_left_large.png", Texture.class);

        mGizmoJumpUpRightSprites = Gizmo.assetManager.get("res/images/gizmo_jump_up_right_large.png", Texture.class);
        mGizmoJumpUpLeftSprites = Gizmo.assetManager.get("res/images/gizmo_jump_up_left_large.png", Texture.class);
        mGizmoFallRightSprites = Gizmo.assetManager.get("res/images/gizmo_jump_fall_right_large.png", Texture.class);
        mGizmoFallLeftSprites = Gizmo.assetManager.get("res/images/gizmo_jump_fall_left_large.png", Texture.class);

        mGizmoFlinchingRunningLeft = Gizmo.assetManager.get("res/images/gizmo_running_flinching_left.png", Texture.class);
        mGizmoFlinchingRunningRight = Gizmo.assetManager.get("res/images/gizmo_running_flinching_right.png", Texture.class);
        mGizmoFlinchingStandingLeft = Gizmo.assetManager.get("res/images/gizmo_standing_left_large_flinching.png", Texture.class);
        mGizmoFlinchingStandingRight = Gizmo.assetManager.get("res/images/gizmo_standing_right_large_flinching.png", Texture.class);
        mGizmoFlinchingJumpUpRight = Gizmo.assetManager.get("res/images/gizmo_jump_up_right_large_flinching.png", Texture.class);
        mGizmoFlinchingJumpUpLeft = Gizmo.assetManager.get("res/images/gizmo_jump_up_left_large_flinching.png", Texture.class);
        mGizmoFlinchingJumpFallRight = Gizmo.assetManager.get("res/images/gizmo_jump_fall_right_large_flinching.png", Texture.class);
        mGizmoFlinchingJumpFallLeft = Gizmo.assetManager.get("res/images/gizmo_jump_fall_left_large_flinching.png", Texture.class);

        mRunningRightSprites = TextureRegion.split(mGizmoRunningRight, 32, 32)[0];
        mRunningLeftSprites = TextureRegion.split(mGizmoRunningLeft, 32, 32)[0];
        mStandingRightSprites = TextureRegion.split(mGizmoStandingRight, 32, 32)[0];
        mStandingLeftSprites = TextureRegion.split(mGizmoStandingLeft, 32, 32)[0];

        mJumpUpRightSprites = TextureRegion.split(mGizmoJumpUpRightSprites, 32, 32)[0];
        mJumpUpLeftSprites = TextureRegion.split(mGizmoJumpUpLeftSprites, 32, 32)[0];
        mJumpFallRightSprites = TextureRegion.split(mGizmoFallRightSprites, 32, 32)[0];
        mJumpFallLeftSprites = TextureRegion.split(mGizmoFallLeftSprites, 32, 32)[0];

        mFlinchingRunningLeftSprites = TextureRegion.split(mGizmoFlinchingRunningLeft, 32, 32)[0];
        mFlinchingRunningRightSprites = TextureRegion.split(mGizmoFlinchingRunningRight, 32, 32)[0];
        mFlinchingStandingLeftSprites = TextureRegion.split(mGizmoFlinchingStandingLeft, 32, 32)[0];
        mFlinchingStandingRightSprites = TextureRegion.split(mGizmoFlinchingStandingRight, 32, 32)[0];
        mFlinchingJumpUpRightSprites = TextureRegion.split(mGizmoFlinchingJumpUpRight, 32, 32)[0];
        mFlinchingJumpUpLeftSprites = TextureRegion.split(mGizmoFlinchingJumpUpLeft, 32, 32)[0];
        mFlinchingJumpFallRightSprites = TextureRegion.split(mGizmoFlinchingJumpFallRight, 32, 32)[0];
        mFlinchingJumpFallLeftSprites = TextureRegion.split(mGizmoFlinchingJumpFallLeft, 32, 32)[0];

        setAnimation(mRunningRightSprites, 1 / 12f);
        setFacingDirection(FACING_RIGHT);
    }

    @Override
    public PlayerModel getPlayerModel() {
        return mPlayerModel;
    }

    public void initPlayerData(PlayerModel playerModel) {
        mPlayerModel.setPlayerHearts(playerModel.getPlayerHearts());
        mPlayerModel.setPlayerHealth(playerModel.getPlayerHealth());
        mPlayerModel.setPlayerLives(playerModel.getPlayerLives());
        mPlayerModel.setCurrentLevel(playerModel.getCurrentLevel());
        if (playerModel.getPlayerKeys().size() > 0) {
            for (int i = 0; i < playerModel.getPlayerKeys().size(); i++) {
                mPlayerModel.addPlayerKey(playerModel.getPlayerKeys().get(i));
            }
        }
    }

    public void setHitEnemy(int healthDamage) {
        if (!mIsFlinching) {
            mPlayerModel.setPlayerHealth(mPlayerModel.getPlayerHealth() - healthDamage);
            applyFlinchForce();
            delegate.playerGotHit(mPlayerModel.getPlayerHealth());
        }
    }

    public void resetHealth() {
        mPlayerModel.setPlayerHealth(mPlayerModel.getPlayerHearts() * PlayerModel.HEART_HEALTH_AMOUNT);
    }

    public void resetLives() {
        mPlayerModel.setPlayerLives(mPlayerModel.DEFAULT_LIVES);
    }

    public void incrementLives() {
        if (mPlayerModel.getPlayerLives() < mPlayerModel.DEFAULT_MAX_LIVES) {
            mPlayerModel.setPlayerLives(mPlayerModel.getPlayerLives() + 1);
        }
    }

    public void deIncrementLives() {
        mPlayerModel.setPlayerLives(mPlayerModel.getPlayerLives() - 1);
        if (mPlayerModel.getPlayerLives() <= 0) {
            delegate.playerZeroLives();
        }
    }

    public int getPlayerLives() {
        return mPlayerModel.getPlayerLives();
    }

    public void addKey(KeyModel keyModel) {
        if (mLastKeyAdded == null || !mLastKeyAdded.equals(keyModel)) {
            mPlayerModel.addPlayerKey(keyModel);
            mLastKeyAdded = keyModel;
        }
    }

    public boolean hasCorrectKey(String keyType) {
        ArrayList<KeyModel> playerKeys = mPlayerModel.getPlayerKeys();
        for (int i = 0; i < playerKeys.size(); i++) {
            if (playerKeys.get(i).getKeyType().equalsIgnoreCase(keyType)) {
                mPlayerModel.removePlayerKey(keyType);
                return true;
            }
        }
        return false;
    }

    public void addHealthHeart(HeartModel heartModel) {
        if (mLastHeartAdded == null || !mLastHeartAdded.equals(heartModel)) {
            mPlayerModel.setPlayerHearts(mPlayerModel.getPlayerHearts() + 1);
            mLastHeartAdded = heartModel;
        }
    }

    public int getHealthHearts() {
        return mPlayerModel.getPlayerHearts();
    }

    public void setCurrentLevel(int level) {
        mPlayerModel.setCurrentLevel(level);
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
        BodyUtils.applyLinearImpulseToBody(mBody, JUMP_FORCE, "y");
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

    public void moveLeft() {
        if (mIsFlinching) {
            if (getIsOnGround() && !getCurrentTextureRegion().equals(mFlinchingRunningLeftSprites)) {
                setAnimation(mFlinchingRunningLeftSprites, 1 / 12f);
            }
            if (!getIsOnGround() && !getCurrentTextureRegion().equals(mFlinchingJumpUpLeftSprites)
                    && !getCurrentTextureRegion().equals(mFlinchingJumpFallLeftSprites)) {
                setAnimation(mFlinchingJumpUpLeftSprites, 1 / 12f);
            }
        }
        else {
            if (getIsOnGround() && !getCurrentTextureRegion().equals(mRunningLeftSprites)) {
                setAnimation(mRunningLeftSprites, 1 / 12f);
            }
            if (!getIsOnGround() && !getCurrentTextureRegion().equals(mJumpUpLeftSprites)) {
                setAnimation(mJumpUpLeftSprites, 1 / 12f);
            }
        }
        BodyUtils.applyLinearImpulseToBody(mBody, -RUNNING_FORCE, "x");
        setFacingDirection(FACING_LEFT);
    }

    public void moveRight() {
        if (mIsFlinching) {
            if (getIsOnGround() && !getCurrentTextureRegion().equals(mFlinchingRunningRightSprites)) {
                setAnimation(mFlinchingRunningRightSprites, 1 / 12f);
            }
            if (!getIsOnGround() && !getCurrentTextureRegion().equals(mFlinchingJumpUpRightSprites)
                    && !getCurrentTextureRegion().equals(mFlinchingJumpFallRightSprites)) {
                setAnimation(mFlinchingJumpUpRightSprites, 1 / 12f);
            }
        }
        else {
            if (getIsOnGround() && !getCurrentTextureRegion().equals(mRunningRightSprites)) {
                setAnimation(mRunningRightSprites, 1 / 12f);
            }
            if (!getIsOnGround() && !getCurrentTextureRegion().equals(mJumpUpRightSprites)) {
                setAnimation(mJumpUpRightSprites, 1 / 12f);
            }
        }
        BodyUtils.applyLinearImpulseToBody(mBody, RUNNING_FORCE, "x");
        setFacingDirection(FACING_RIGHT);
    }

    public void stoppedMoving() {
        if (mIsFlinching) {
            if (mFacingDirection == FACING_LEFT) {
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
            if (mFacingDirection == FACING_LEFT) {
                if (getIsOnGround() && !getCurrentTextureRegion().equals(mStandingLeftSprites)) {
                    setAnimation(mStandingLeftSprites, 1 / 12f);
                }
            }
            else {
                if (getIsOnGround() && !getCurrentTextureRegion().equals(mStandingRightSprites)) {
                    setAnimation(mStandingRightSprites, 1 / 12f);
                }
            }
        }
        BodyUtils.applyLinearImpulseToBody(mBody, 0, "x");
    }

    private void applyFlinchForce() {
        if (mFacingDirection == FACING_RIGHT) {
            BodyUtils.applyLinearImpulseToBody(mBody, -FLINCH_FORCE, "x");
        }
        else {
            BodyUtils.applyLinearImpulseToBody(mBody, FLINCH_FORCE, "x");
        }
    }

    private void playJumpSfx() {
        Sound jumpSfx = Gizmo.assetManager.get("res/sfx/jump.ogg", Sound.class);
        if (!Constants.IS_DEBUG) {
            jumpSfx.play();
        }
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

}