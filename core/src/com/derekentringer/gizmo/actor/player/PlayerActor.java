package com.derekentringer.gizmo.actor.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.UserData;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.constant.Constants;

public class PlayerActor extends BaseActor implements IPlayerDelegate {

    private static final int FACING_RIGHT = 1;
    private static final int FACING_LEFT = 2;

    private static final float RUNNING_FORCE = 1f;
    private static final float JUMP_FORCE = 4f;
    private static final float JUMP_FORCE_RESET = -1f;

    private TextureRegion[] runningRightSprites;
    private TextureRegion[] runningLeftSprites;
    private TextureRegion[] standingRightSprites;
    private TextureRegion[] standingLeftSprites;

    private Texture gizmoRunningRight;
    private Texture gizmoRunningLeft;
    private Texture gizmoStandingRight;
    private Texture gizmoStandingLeft;

    public boolean isOnGround;
    public int facingDirection;

    public PlayerActor(Body body) {
        super(body);

        gizmoRunningRight = Gizmo.assetManager.get("res/images/gizmo_running_right.png", Texture.class);
        gizmoRunningLeft = Gizmo.assetManager.get("res/images/gizmo_running_left.png", Texture.class);
        gizmoStandingRight = Gizmo.assetManager.get("res/images/gizmo_standing_right.png", Texture.class);
        gizmoStandingLeft = Gizmo.assetManager.get("res/images/gizmo_standing_left.png", Texture.class);

        runningRightSprites = TextureRegion.split(gizmoRunningRight, 32, 32)[0];
        runningLeftSprites = TextureRegion.split(gizmoRunningLeft, 32, 32)[0];
        standingRightSprites = TextureRegion.split(gizmoStandingRight, 32, 32)[0];
        standingLeftSprites = TextureRegion.split(gizmoStandingLeft, 32, 32)[0];

        setAnimation(runningRightSprites, 1 / 12f);
    }

    @Override
    public UserData getUserData() {
        return null;
    }

    public void jump() {
        BodyUtils.applyLinearImpulseToBody(body, JUMP_FORCE, "y");
        playJumpSfx();
    }

    public void stopJumping() {
        BodyUtils.applyLinearImpulseToBody(body, JUMP_FORCE_RESET, "y");
    }

    public void setIsOnGround(boolean isOnGround) {
        this.isOnGround = isOnGround;
    }

    public boolean getIsOnGround() {
        return isOnGround;
    }

    public void moveLeft() {
        if(!getCurrentTextureRegion().equals(runningLeftSprites)) {
            setAnimation(runningLeftSprites, 1 / 12f);
        }
        BodyUtils.applyLinearImpulseToBody(body, -RUNNING_FORCE, "x");
        setFacingDirection(FACING_LEFT);
    }

    public void moveRight() {
        if(!getCurrentTextureRegion().equals(runningRightSprites)) {
            setAnimation(runningRightSprites, 1 / 12f);
        }
        BodyUtils.applyLinearImpulseToBody(body, RUNNING_FORCE, "x");
        setFacingDirection(FACING_RIGHT);
    }

    public void stoppedMoving() {
        if(!getCurrentTextureRegion().equals(standingRightSprites)) {
            if(facingDirection == FACING_LEFT) {
                setAnimation(standingLeftSprites, 1 / 12f);
            }
            else {
                setAnimation(standingRightSprites, 1 / 12f);
            }
        }
        BodyUtils.applyLinearImpulseToBody(body, 0, "x");
    }

    private void playJumpSfx() {
        Sound jumpSfx = Gizmo.assetManager.get("res/sfx/jump.ogg", Sound.class);
        if(!Constants.DEBUGGING) {
            jumpSfx.play();
        }
    }

    private void setFacingDirection(int direction) {
        facingDirection = direction;
    }

    private int getFacingDirection() {
        return facingDirection;
    }

    @Override
    public void playerIsOffMap(boolean offMap) {
    }

}