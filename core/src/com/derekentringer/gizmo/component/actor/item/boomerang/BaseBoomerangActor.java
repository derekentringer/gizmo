package com.derekentringer.gizmo.component.actor.item.boomerang;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.item.interfaces.IItems;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;

import java.util.ArrayList;

public class BaseBoomerangActor extends BaseActor {

    protected ArrayList<IItems> listeners = new ArrayList<IItems>();

    protected static final float MOVEMENT_FORCE = 4;
    protected static final float MOVEMENT_FORCE_BACK = 5;
    protected static final float MAX_DISTANCE = 1;

    protected TextureRegion[] mBoomerangSprite;
    protected Texture mBoomerangTexture;

    protected int mPlayerFacingDirection;
    protected Vector2 mPlayerPosition = new Vector2();
    protected boolean mComingBack;

    public BaseBoomerangActor(Body body) {
        super(body);
    }

    public void addListener(IItems listener) {
        listeners.add(listener);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (mPlayerFacingDirection == PlayerActor.FACING_RIGHT) {
            if (mBody.getPosition().x <= getPlayerPosition().x + MAX_DISTANCE && !mComingBack) {
                mBody.setLinearVelocity(MOVEMENT_FORCE, 0);
            }
            else {
                mComingBack = true;
                mBody.setLinearVelocity((getPlayerPosition().x - getPosition().x) * MOVEMENT_FORCE_BACK, (getPlayerPosition().y - getPosition().y) * MOVEMENT_FORCE_BACK);
                if (mBody.getPosition().x <= getPlayerPosition().x + 0.1) {
                    for (IItems listener : listeners) {
                        listener.removePlayerItemFromStage(this);
                    }
                }
            }
        }
        else {
            if (mBody.getPosition().x >= getPlayerPosition().x - MAX_DISTANCE && !mComingBack) {
                mBody.setLinearVelocity(-MOVEMENT_FORCE, 0);
            }
            else {
                mComingBack = true;
                mBody.setLinearVelocity((getPlayerPosition().x - getPosition().x) * MOVEMENT_FORCE_BACK, (getPlayerPosition().y - getPosition().y) * MOVEMENT_FORCE_BACK);
                if (mBody.getPosition().x >= getPlayerPosition().x - 0.1) {
                    for (IItems listener : listeners) {
                        listener.removePlayerItemFromStage(this);
                    }
                }
            }
        }
    }

}