package com.derekentringer.gizmo.components.actor.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.log.GLog;

public class PhantomActor extends BaseActor {

    private static final String TAG = PhantomActor.class.getSimpleName();

    private static final float MOVEMENT_FORCE = 0.1f;
    private static final float MOVEMENT_PADDING = WorldUtils.ppmCalc(7);

    private static final int PLAYER_DETECTION_Y = 1;
    private static final int PLAYER_DETECTION_X = 1;

    private TextureRegion[] mPhantomLeftSprite;
    private TextureRegion[] mPhantomRightSprite;
    private Texture mPhantomLeft;
    private Texture mPhantomRight;

    private Vector2 mPlayerPosition = new Vector2();

    public PhantomActor(Body body) {
        super(body);

        mPhantomLeft = Gizmo.assetManager.get("res/images/enemies/phantom/phantom_left.png", Texture.class);
        mPhantomRight = Gizmo.assetManager.get("res/images/enemies/phantom/phantom_right.png", Texture.class);

        mPhantomLeftSprite = TextureRegion.split(mPhantomLeft, 32, 32)[0];
        mPhantomRightSprite = TextureRegion.split(mPhantomRight, 32, 32)[0];

        setAnimation(mPhantomLeftSprite, 1 / 12f);

        setFacingDirection(FACING_LEFT);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBaseModel;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!isPlayerAbove() && !isPlayerBelow()) {

            GLog.d(TAG, "isPlayerAbove: " + isPlayerAbove());
            GLog.d(TAG, "isPlayerBelow: " + isPlayerBelow());

            if (isPlayerBehind()) {

                GLog.d(TAG, "isPlayerBehind: " + isPlayerBehind());

                setFacingDirection(FACING_LEFT);
                BodyUtils.applyLinearImpulseToBody(mBody, -MOVEMENT_FORCE, "x");
            }
            else if (isPlayerInFront()) {

                GLog.d(TAG, "isPlayerInFront: " + isPlayerInFront());

                setFacingDirection(FACING_RIGHT);
                BodyUtils.applyLinearImpulseToBody(mBody, MOVEMENT_FORCE, "x");
            }
            else {
                BodyUtils.applyLinearImpulseToBody(mBody, 0, "x");
            }

        }
        else {
            BodyUtils.applyLinearImpulseToBody(mBody, 0, "x");
        }

        if (mFacingDirection == FACING_LEFT) {
            if (!getCurrentTextureRegion().equals(mPhantomLeftSprite)) {
                setAnimation(mPhantomLeftSprite, 1 / 12f);
            }
        }
        else {
            if (!getCurrentTextureRegion().equals(mPhantomRightSprite)) {
                setAnimation(mPhantomRightSprite, 1 / 12f);
            }
        }
    }

    private boolean isPlayerAbove() {
        return getPosition().y < getPlayerPosition().y - PLAYER_DETECTION_Y;
    }

    private boolean isPlayerBelow() {
        return getPosition().y > getPlayerPosition().y + PLAYER_DETECTION_Y;
    }




    private boolean isPlayerInFront() {
        return getPosition().x < getPlayerPosition().x - PLAYER_DETECTION_X;
    }

    private boolean isPlayerBehind() {
        return getPosition().x > getPlayerPosition().x + PLAYER_DETECTION_X;
    }


    

    public Vector2 getPlayerPosition() {
        return mPlayerPosition;
    }

    public void setPlayerPosition(Vector2 playerPosition) {
        mPlayerPosition.x = playerPosition.x;
        mPlayerPosition.y = playerPosition.y;
    }

}