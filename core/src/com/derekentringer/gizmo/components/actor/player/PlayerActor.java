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

    private PlayerModel playerModel = new PlayerModel();

    public IPlayerDelegate delegate = null;

    private TextureRegion[] runningRightSprites;
    private TextureRegion[] runningLeftSprites;
    private TextureRegion[] standingRightSprites;
    private TextureRegion[] standingLeftSprites;
    private TextureRegion[] jumpUpRightSprites;
    private TextureRegion[] jumpUpLeftSprites;
    private TextureRegion[] jumpFallRightSprites;
    private TextureRegion[] jumpFallLeftSprites;

    private TextureRegion[] flinchingRunningLeftSprites;
    private TextureRegion[] flinchingRunningRightSprites;
    private TextureRegion[] flinchingStandingLeftSprites;
    private TextureRegion[] flinchingStandingRightSprites;
    private TextureRegion[] flinchingJumpUpLeftSprites;
    private TextureRegion[] flinchingJumpUpRightSprites;
    private TextureRegion[] flinchingJumpFallLeftSprites;
    private TextureRegion[] flinchingJumpFallRightSprites;

    private Texture gizmoRunningRight;
    private Texture gizmoRunningLeft;
    private Texture gizmoStandingRight;
    private Texture gizmoStandingLeft;
    private Texture gizmoJumpUpRightSprites;
    private Texture gizmoJumpUpLeftSprites;
    private Texture gizmoFallRightSprites;
    private Texture gizmoFallLeftSprites;

    private Texture gizmoFlinchingRunningLeft;
    private Texture gizmoFlinchingRunningRight;
    private Texture gizmoFlinchingStandingLeft;
    private Texture gizmoFlinchingStandingRight;
    private Texture gizmoFlinchingJumpUpLeft;
    private Texture gizmoFlinchingJumpUpRight;
    private Texture gizmoFlinchingJumpFallLeft;
    private Texture gizmoFlinchingJumpFallRight;

    private DoorModel isAtDoorUserData;
    private boolean isOnGround;
    private boolean isAtDoor;
    private static boolean isFlinching;

    private KeyModel lastKeyAdded;
    private HeartModel lastHeartAdded;

    public PlayerActor(Body body) {
        super(body);

        gizmoRunningRight = Gizmo.assetManager.get("res/images/gizmo_running_right.png", Texture.class);
        gizmoRunningLeft = Gizmo.assetManager.get("res/images/gizmo_running_left.png", Texture.class);
        gizmoStandingRight = Gizmo.assetManager.get("res/images/gizmo_standing_right_large.png", Texture.class);
        gizmoStandingLeft = Gizmo.assetManager.get("res/images/gizmo_standing_left_large.png", Texture.class);

        gizmoJumpUpRightSprites = Gizmo.assetManager.get("res/images/gizmo_jump_up_right_large.png", Texture.class);
        gizmoJumpUpLeftSprites = Gizmo.assetManager.get("res/images/gizmo_jump_up_left_large.png", Texture.class);
        gizmoFallRightSprites = Gizmo.assetManager.get("res/images/gizmo_jump_fall_right_large.png", Texture.class);
        gizmoFallLeftSprites = Gizmo.assetManager.get("res/images/gizmo_jump_fall_left_large.png", Texture.class);

        gizmoFlinchingRunningLeft = Gizmo.assetManager.get("res/images/gizmo_running_flinching_left.png", Texture.class);
        gizmoFlinchingRunningRight = Gizmo.assetManager.get("res/images/gizmo_running_flinching_right.png", Texture.class);
        gizmoFlinchingStandingLeft = Gizmo.assetManager.get("res/images/gizmo_standing_left_large_flinching.png", Texture.class);
        gizmoFlinchingStandingRight = Gizmo.assetManager.get("res/images/gizmo_standing_right_large_flinching.png", Texture.class);
        gizmoFlinchingJumpUpRight = Gizmo.assetManager.get("res/images/gizmo_jump_up_right_large_flinching.png", Texture.class);
        gizmoFlinchingJumpUpLeft = Gizmo.assetManager.get("res/images/gizmo_jump_up_left_large_flinching.png", Texture.class);
        gizmoFlinchingJumpFallRight = Gizmo.assetManager.get("res/images/gizmo_jump_fall_right_large_flinching.png", Texture.class);
        gizmoFlinchingJumpFallLeft = Gizmo.assetManager.get("res/images/gizmo_jump_fall_left_large_flinching.png", Texture.class);

        runningRightSprites = TextureRegion.split(gizmoRunningRight, 32, 32)[0];
        runningLeftSprites = TextureRegion.split(gizmoRunningLeft, 32, 32)[0];
        standingRightSprites = TextureRegion.split(gizmoStandingRight, 32, 32)[0];
        standingLeftSprites = TextureRegion.split(gizmoStandingLeft, 32, 32)[0];

        jumpUpRightSprites = TextureRegion.split(gizmoJumpUpRightSprites, 32, 32)[0];
        jumpUpLeftSprites = TextureRegion.split(gizmoJumpUpLeftSprites, 32, 32)[0];
        jumpFallRightSprites = TextureRegion.split(gizmoFallRightSprites, 32, 32)[0];
        jumpFallLeftSprites = TextureRegion.split(gizmoFallLeftSprites, 32, 32)[0];

        flinchingRunningLeftSprites = TextureRegion.split(gizmoFlinchingRunningLeft, 32, 32)[0];
        flinchingRunningRightSprites = TextureRegion.split(gizmoFlinchingRunningRight, 32, 32)[0];
        flinchingStandingLeftSprites = TextureRegion.split(gizmoFlinchingStandingLeft, 32, 32)[0];
        flinchingStandingRightSprites = TextureRegion.split(gizmoFlinchingStandingRight, 32, 32)[0];
        flinchingJumpUpRightSprites = TextureRegion.split(gizmoFlinchingJumpUpRight, 32, 32)[0];
        flinchingJumpUpLeftSprites = TextureRegion.split(gizmoFlinchingJumpUpLeft, 32, 32)[0];
        flinchingJumpFallRightSprites = TextureRegion.split(gizmoFlinchingJumpFallRight, 32, 32)[0];
        flinchingJumpFallLeftSprites = TextureRegion.split(gizmoFlinchingJumpFallLeft, 32, 32)[0];

        setAnimation(runningRightSprites, 1 / 12f);
        setFacingDirection(FACING_RIGHT);
    }

    @Override
    public PlayerModel getUserData() {
        return playerModel;
    }

    public void initPlayerData(PlayerModel pData) {
        playerModel.setPlayerHearts(pData.getPlayerHearts());
        playerModel.setPlayerHealth(pData.getPlayerHealth());
        playerModel.setPlayerLives(pData.getPlayerLives());
        playerModel.setCurrentLevel(pData.getCurrentLevel());
        if (pData.getPlayerKeys().size() > 0) {
            for (int i = 0; i < pData.getPlayerKeys().size(); i++) {
                playerModel.setPlayerKey(pData.getPlayerKeys().get(i));
            }
        }
    }

    public void setHitEnemy(int healthDamage) {
        if (!isFlinching) {
            playerModel.setPlayerHealth(playerModel.getPlayerHealth() - healthDamage);
            applyFlinchForce();
            delegate.playerGotHit(playerModel.getPlayerHealth());
        }
    }

    public void resetHealth() {
        playerModel.setPlayerHealth(playerModel.getPlayerHearts() * PlayerModel.HEART_HEALTH_AMOUNT);
    }

    public void resetLives() {
        playerModel.setPlayerLives(playerModel.DEFAULT_LIVES);
    }

    public void incrementLives() {
        if(playerModel.getPlayerLives() < playerModel.DEFAULT_MAX_LIVES) {
            playerModel.setPlayerLives(playerModel.getPlayerLives() + 1);
        }
    }

    public void deIncrementLives() {
        playerModel.setPlayerLives(playerModel.getPlayerLives() - 1);
        if (playerModel.getPlayerLives() <= 0) {
            delegate.playerZeroLives();
        }
    }

    public int getPlayerLives() {
        return playerModel.getPlayerLives();
    }

    public void addKey(KeyModel keyModel) {
        if (lastKeyAdded == null || !lastKeyAdded.equals(keyModel)) {
            playerModel.setPlayerKey(keyModel);
            lastKeyAdded = keyModel;
        }
    }

    public boolean hasCorrectKey(String keyType) {
        ArrayList<KeyModel> playerKeys = playerModel.getPlayerKeys();
        for (int i = 0; i < playerKeys.size(); i++) {
            if (playerKeys.get(i).getKeyType().equalsIgnoreCase(keyType)) {
                playerModel.removePlayerKey(keyType);
                return true;
            }
        }
        return false;
    }

    public void addHealthHeart(HeartModel heartModel) {
        if (lastHeartAdded == null || !lastHeartAdded.equals(heartModel)) {
            playerModel.setPlayerHearts(playerModel.getPlayerHearts() + 1);
            lastHeartAdded = heartModel;
        }
    }

    public int getHealthHearts() {
        return playerModel.getPlayerHearts();
    }

    public void setCurrentLevel(int level) {
        playerModel.setCurrentLevel(level);
    }

    public void jump() {
        if (isFlinching) {
            if (getFacingDirection() == FACING_RIGHT) {
                if (!getCurrentTextureRegion().equals(flinchingJumpUpRightSprites)) {
                    setAnimation(flinchingJumpUpRightSprites, 1 / 12f);
                }
            }
            else if (getFacingDirection() == FACING_LEFT) {
                if (!getCurrentTextureRegion().equals(flinchingJumpUpLeftSprites)) {
                    setAnimation(flinchingJumpUpLeftSprites, 1 / 12f);
                }
            }
        }
        else {
            if (getFacingDirection() == FACING_RIGHT) {
                if (!getCurrentTextureRegion().equals(jumpUpRightSprites)) {
                    setAnimation(jumpUpRightSprites, 1 / 12f);
                }
            }
            else if (getFacingDirection() == FACING_LEFT) {
                if (!getCurrentTextureRegion().equals(jumpUpLeftSprites)) {
                    setAnimation(jumpUpLeftSprites, 1 / 12f);
                }
            }
        }
        BodyUtils.applyLinearImpulseToBody(body, JUMP_FORCE, "y");
        playJumpSfx();
    }

    public void stopJumping() {
        if (isFlinching) {
            if (getFacingDirection() == FACING_RIGHT) {
                if (!getIsOnGround() && !getCurrentTextureRegion().equals(flinchingJumpFallRightSprites)) {
                    setAnimation(flinchingJumpFallRightSprites, 1 / 12f);
                }
            }
            else if (getFacingDirection() == FACING_LEFT) {
                if (!getIsOnGround() && !getCurrentTextureRegion().equals(flinchingJumpFallLeftSprites)) {
                    setAnimation(flinchingJumpFallLeftSprites, 1 / 12f);
                }
            }
        }
        else {
            if (getFacingDirection() == FACING_RIGHT) {
                if (!getIsOnGround() && !getCurrentTextureRegion().equals(jumpFallRightSprites)) {
                    setAnimation(jumpFallRightSprites, 1 / 12f);
                }
            }
            else if (getFacingDirection() == FACING_LEFT) {
                if (!getIsOnGround() && !getCurrentTextureRegion().equals(jumpFallLeftSprites)) {
                    setAnimation(jumpFallLeftSprites, 1 / 12f);
                }
            }
        }
        BodyUtils.applyLinearImpulseToBody(body, JUMP_FORCE_RESET, "y");
    }

    public void moveLeft() {
        if (isFlinching) {
            if (getIsOnGround() && !getCurrentTextureRegion().equals(flinchingRunningLeftSprites)) {
                setAnimation(flinchingRunningLeftSprites, 1 / 12f);
            }
            if (!getIsOnGround() && !getCurrentTextureRegion().equals(flinchingJumpUpLeftSprites)
                    && !getCurrentTextureRegion().equals(flinchingJumpFallLeftSprites)) {
                setAnimation(flinchingJumpUpLeftSprites, 1 / 12f);
            }
        }
        else {
            if (getIsOnGround() && !getCurrentTextureRegion().equals(runningLeftSprites)) {
                setAnimation(runningLeftSprites, 1 / 12f);
            }
            if (!getIsOnGround() && !getCurrentTextureRegion().equals(jumpUpLeftSprites)) {
                setAnimation(jumpUpLeftSprites, 1 / 12f);
            }
        }
        BodyUtils.applyLinearImpulseToBody(body, -RUNNING_FORCE, "x");
        setFacingDirection(FACING_LEFT);
    }

    public void moveRight() {
        if (isFlinching) {
            if (getIsOnGround() && !getCurrentTextureRegion().equals(flinchingRunningRightSprites)) {
                setAnimation(flinchingRunningRightSprites, 1 / 12f);
            }
            if (!getIsOnGround() && !getCurrentTextureRegion().equals(flinchingJumpUpRightSprites)
                    && !getCurrentTextureRegion().equals(flinchingJumpFallRightSprites)) {
                setAnimation(flinchingJumpUpRightSprites, 1 / 12f);
            }
        }
        else {
            if (getIsOnGround() && !getCurrentTextureRegion().equals(runningRightSprites)) {
                setAnimation(runningRightSprites, 1 / 12f);
            }
            if (!getIsOnGround() && !getCurrentTextureRegion().equals(jumpUpRightSprites)) {
                setAnimation(jumpUpRightSprites, 1 / 12f);
            }
        }
        BodyUtils.applyLinearImpulseToBody(body, RUNNING_FORCE, "x");
        setFacingDirection(FACING_RIGHT);
    }

    public void stoppedMoving() {
        if (isFlinching) {
            if (facingDirection == FACING_LEFT) {
                if (getIsOnGround() && !getCurrentTextureRegion().equals(flinchingStandingLeftSprites)) {
                    setAnimation(flinchingStandingLeftSprites, 1 / 12f);
                }
            }
            else {
                if (getIsOnGround() && !getCurrentTextureRegion().equals(flinchingStandingRightSprites)) {
                    setAnimation(flinchingStandingRightSprites, 1 / 12f);
                }
            }
        }
        else {
            if (facingDirection == FACING_LEFT) {
                if (getIsOnGround() && !getCurrentTextureRegion().equals(standingLeftSprites)) {
                    setAnimation(standingLeftSprites, 1 / 12f);
                }
            }
            else {
                if (getIsOnGround() && !getCurrentTextureRegion().equals(standingRightSprites)) {
                    setAnimation(standingRightSprites, 1 / 12f);
                }
            }
        }
        BodyUtils.applyLinearImpulseToBody(body, 0, "x");
    }

    private void applyFlinchForce() {
        if (facingDirection == FACING_RIGHT) {
            BodyUtils.applyLinearImpulseToBody(body, -FLINCH_FORCE, "x");
        }
        else {
            BodyUtils.applyLinearImpulseToBody(body, FLINCH_FORCE, "x");
        }
    }

    private void playJumpSfx() {
        Sound jumpSfx = Gizmo.assetManager.get("res/sfx/jump.ogg", Sound.class);
        if (!Constants.DEBUGGING) {
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
        isOnGround = onGround;
    }

    public boolean getIsOnGround() {
        return isOnGround;
    }

    public void setIsAtDoor(boolean atDoor) {
        isAtDoor = atDoor;
    }

    public boolean getIsAtDoor() {
        return isAtDoor;
    }

    public void setIsAtDoorUserData(DoorModel doorUserData) {
        isAtDoorUserData = doorUserData;
    }

    public DoorModel getIsAtDoorUserData() {
        return isAtDoorUserData;
    }

    public void setIsFlinching(boolean flinching) {
        isFlinching = flinching;
    }

    public boolean getIsFlinching() {
        return isFlinching;
    }

}