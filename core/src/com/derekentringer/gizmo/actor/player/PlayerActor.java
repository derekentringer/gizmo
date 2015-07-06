package com.derekentringer.gizmo.actor.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.structure.DoorUserData;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.constant.Constants;

public class PlayerActor extends BaseActor implements IPlayerDelegate {

    public static final int FACING_RIGHT = 1;
    public static final int FACING_LEFT = 2;

    private static final float RUNNING_FORCE = 1f;
    private static final float JUMP_FORCE = 4f;
    private static final float JUMP_FORCE_RESET = -1f;

    private TextureRegion[] runningRightSprites;
    private TextureRegion[] runningLeftSprites;
    private TextureRegion[] standingRightSprites;
    private TextureRegion[] standingLeftSprites;
    private TextureRegion[] jumpUpRightSprites;
    private TextureRegion[] jumpUpLeftSprites;
    private TextureRegion[] jumpFallRightSprites;
    private TextureRegion[] jumpFallLeftSprites;

    private Texture gizmoRunningRight;
    private Texture gizmoRunningLeft;
    private Texture gizmoStandingRight;
    private Texture gizmoStandingLeft;
    private Texture gizmoJumpUpRightSprites;
    private Texture gizmoJumpUpLeftSprites;
    private Texture gizmoFallRightSprites;
    private Texture gizmoFallLeftSprites;

    private boolean isOnGround;
    private boolean isAtDoor;
    private DoorUserData isAtDoorUserData;
    private int facingDirection;
    
    public PlayerActor(Body body) {
        super(body);

        gizmoRunningRight = Gizmo.assetManager.get("res/images/gizmo_running_right.png", Texture.class);
        gizmoRunningLeft = Gizmo.assetManager.get("res/images/gizmo_running_left.png", Texture.class);
        gizmoStandingRight = Gizmo.assetManager.get("res/images/gizmo_standing_right.png", Texture.class);
        gizmoStandingLeft = Gizmo.assetManager.get("res/images/gizmo_standing_left.png", Texture.class);

        gizmoJumpUpRightSprites = Gizmo.assetManager.get("res/images/gizmo_jump_up_right.png", Texture.class);
        gizmoJumpUpLeftSprites = Gizmo.assetManager.get("res/images/gizmo_jump_up_left.png", Texture.class);
        gizmoFallRightSprites = Gizmo.assetManager.get("res/images/gizmo_jump_fall_right.png", Texture.class);
        gizmoFallLeftSprites = Gizmo.assetManager.get("res/images/gizmo_jump_fall_left.png", Texture.class);

        runningRightSprites = TextureRegion.split(gizmoRunningRight, 32, 32)[0];
        runningLeftSprites = TextureRegion.split(gizmoRunningLeft, 32, 32)[0];
        standingRightSprites = TextureRegion.split(gizmoStandingRight, 32, 32)[0];
        standingLeftSprites = TextureRegion.split(gizmoStandingLeft, 32, 32)[0];

        jumpUpRightSprites = TextureRegion.split(gizmoJumpUpRightSprites, 32, 32)[0];
        jumpUpLeftSprites = TextureRegion.split(gizmoJumpUpLeftSprites, 32, 32)[0];
        jumpFallRightSprites = TextureRegion.split(gizmoFallRightSprites, 32, 32)[0];
        jumpFallLeftSprites = TextureRegion.split(gizmoFallLeftSprites, 32, 32)[0];

        setAnimation(runningRightSprites, 1 / 12f);
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

    public void jump() {
        if(getFacingDirection() == FACING_RIGHT) {
            if (!getCurrentTextureRegion().equals(jumpUpRightSprites)) {
                setAnimation(jumpUpRightSprites, 1 / 12f);
            }
        }
        else if(getFacingDirection() == FACING_LEFT) {
            if (!getCurrentTextureRegion().equals(jumpUpLeftSprites)) {
                setAnimation(jumpUpLeftSprites, 1 / 12f);
            }
        }
        BodyUtils.applyLinearImpulseToBody(body, JUMP_FORCE, "y");
        playJumpSfx();
    }

    public void stopJumping() {
        if(getFacingDirection() == FACING_RIGHT) {
            if (!getIsOnGround() && !getCurrentTextureRegion().equals(jumpFallRightSprites)) {
                setAnimation(jumpFallRightSprites, 1 / 12f);
            }
        }
        else if(getFacingDirection() == FACING_LEFT) {
            if (!getIsOnGround() && !getCurrentTextureRegion().equals(jumpFallLeftSprites)) {
                setAnimation(jumpFallLeftSprites, 1 / 12f);
            }
        }
        BodyUtils.applyLinearImpulseToBody(body, JUMP_FORCE_RESET, "y");
    }

    public void setIsOnGround(boolean isOnGround) {
        this.isOnGround = isOnGround;
    }

    public boolean getIsOnGround() {
        return isOnGround;
    }

    public void setIsAtDoor(boolean isAtDoor) {
        this.isAtDoor = isAtDoor;
    }

    public boolean getIsAtDoor() {
        return isAtDoor;
    }

    public void setIsAtDoorUserData(DoorUserData doorUserData) {
        this.isAtDoorUserData = doorUserData;
    }

    public DoorUserData getIsAtDoorUserData() {
        return isAtDoorUserData;
    }

    public void moveLeft() {
        if(getIsOnGround() && !getCurrentTextureRegion().equals(runningLeftSprites)) {
            setAnimation(runningLeftSprites, 1 / 12f);
        }
        BodyUtils.applyLinearImpulseToBody(body, -RUNNING_FORCE, "x");
        setFacingDirection(FACING_LEFT);
    }

    public void moveRight() {
        if(getIsOnGround() && !getCurrentTextureRegion().equals(runningRightSprites)) {
            setAnimation(runningRightSprites, 1 / 12f);
        }
        BodyUtils.applyLinearImpulseToBody(body, RUNNING_FORCE, "x");
        setFacingDirection(FACING_RIGHT);
    }

    public void stoppedMoving() {
        if(getIsOnGround() && !getCurrentTextureRegion().equals(standingRightSprites)) {
            if(facingDirection == FACING_LEFT) {
                setAnimation(standingLeftSprites, 1 / 12f);
            }
            else {
                setAnimation(standingRightSprites, 1 / 12f);
            }
        }
        BodyUtils.applyLinearImpulseToBody(body, 0, "x");
    }

    public void enterDoor() {
        if(isAtDoor) {

        }
    }

    private void playJumpSfx() {
        Sound jumpSfx = Gizmo.assetManager.get("res/sfx/jump.ogg", Sound.class);
        if(!Constants.DEBUGGING) {
            jumpSfx.play();
        }
    }

    public void setFacingDirection(int direction) {
        facingDirection = direction;
    }

    public int getFacingDirection() {
        return facingDirection;
    }

    @Override
    public void playerIsOffMap(boolean offMap) {
    }

}