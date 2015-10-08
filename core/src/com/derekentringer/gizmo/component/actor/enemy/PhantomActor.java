package com.derekentringer.gizmo.component.actor.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.WorldUtils;

public class PhantomActor extends BaseActor {

    private static final String TAG = PhantomActor.class.getSimpleName();

    private static final float MOVEMENT_FORCE = 0.1f;
    private static final float MOVEMENT_PADDING = WorldUtils.ppmCalc(7);

    private static final float PLAYER_DETECTION_Y = 3f;
    private static final float PLAYER_DETECTION_X = 4f;

    private TextureRegion[] mPhantomLeftSprite;
    private TextureRegion[] mPhantomRightSprite;
    private Texture mPhantomLeft;
    private Texture mPhantomRight;

    private float phantomRadiusFront = getPosition().x + PLAYER_DETECTION_X;
    private float phantomRadiusBehind = getPosition().x - PLAYER_DETECTION_X;

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
            if (isPlayerBehind()) {
                setFacingDirection(FACING_LEFT);
                BodyUtils.applyLinearImpulseToBody(mBody, -MOVEMENT_FORCE, "x");
            }
            else if (isPlayerInFront()) {
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
        return getPlayerPosition().x < phantomRadiusFront
                && getPlayerPosition().x > getPosition().x;
    }

    private boolean isPlayerBehind() {
        return getPlayerPosition().x > phantomRadiusBehind
                && getPlayerPosition().x < getPosition().x;
    }

    public Vector2 getPlayerPosition() {
        return mPlayerPosition;
    }

    public void setPlayerPosition(Vector2 playerPosition) {
        mPlayerPosition.x = playerPosition.x;
        mPlayerPosition.y = playerPosition.y;
    }

}