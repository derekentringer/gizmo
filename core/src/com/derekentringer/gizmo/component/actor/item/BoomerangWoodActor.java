package com.derekentringer.gizmo.component.actor.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.item.interfaces.IItems;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;

import java.util.ArrayList;

public class BoomerangWoodActor extends BaseActor {

    private static final String TAG = BoomerangWoodActor.class.getSimpleName();

    private ArrayList<IItems> listeners = new ArrayList<IItems>();

    private static final float MOVEMENT_FORCE = 4;
    private static final float MOVEMENT_FORCE_BACK = 5;
    private static final float MAX_DISTANCE = 1;

    private int mPlayerFacingDirection;
    private Vector2 mPlayerPosition = new Vector2();
    private boolean mComingBack;

    private TextureRegion[] mBoomerangWoodSprite;
    private Texture mBoomerangWoodTexture;

    public BoomerangWoodActor(Body body, int playerFacingDirection) {
        super(body);

        mPlayerFacingDirection = playerFacingDirection;

        mBoomerangWoodTexture = Gizmo.assetManager.get("res/image/item/boomerang_wood.png", Texture.class);
        mBoomerangWoodSprite = TextureRegion.split(mBoomerangWoodTexture, 32, 32)[0];
        setAnimation(mBoomerangWoodSprite, 1 / 12f);
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

    public Vector2 getPlayerPosition() {
        return mPlayerPosition;
    }

    public void setPlayerPosition(Vector2 playerPosition) {
        mPlayerPosition.x = playerPosition.x;
        mPlayerPosition.y = playerPosition.y;
    }

}