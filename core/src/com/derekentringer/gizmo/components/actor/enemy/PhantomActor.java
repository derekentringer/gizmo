package com.derekentringer.gizmo.components.actor.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.enemy.PhantomModel;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.WorldUtils;

public class PhantomActor extends BaseActor {

    private static final float MOVEMENT_FORCE = 0.1f;
    private static final int MOVEMENT_PADDING = 7;

    private PhantomModel mPhantomModel = new PhantomModel();

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
    public BaseModel getPlayerModel() {
        return mPhantomModel;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getPosition().x > getPlayerPosition().x + WorldUtils.ppmCalc(MOVEMENT_PADDING)) {
            BodyUtils.applyLinearImpulseToBody(mBody, -MOVEMENT_FORCE, "x");
            setFacingDirection(FACING_LEFT);
        }
        else if (getPosition().x < getPlayerPosition().x - WorldUtils.ppmCalc(MOVEMENT_PADDING)) {
            BodyUtils.applyLinearImpulseToBody(mBody, MOVEMENT_FORCE, "x");
            setFacingDirection(FACING_RIGHT);
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

    public Vector2 getPlayerPosition() {
        return mPlayerPosition;
    }

    public void setPlayerPosition(float xPos) {
        mPlayerPosition.x = xPos;
    }

}