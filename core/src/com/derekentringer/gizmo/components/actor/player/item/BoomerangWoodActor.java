package com.derekentringer.gizmo.components.actor.player.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.components.actor.player.PlayerActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.player.item.BoomerangWoodModel;

public class BoomerangWoodActor extends BaseActor {

    private static final String TAG = BoomerangWoodActor.class.getSimpleName();

    private static final float MOVEMENT_FORCE = 3;

    private BoomerangWoodModel mBoomerangWoodModel = new BoomerangWoodModel();

    private int mPlayerFacingDirection;

    private TextureRegion[] mBoomerangWoodSprite;
    private Texture mBoomerangWoodTexture;

    public BoomerangWoodActor(Body body, int playerFacingDirection) {
        super(body);

        mBody = body;

        mPlayerFacingDirection = playerFacingDirection;

        mBoomerangWoodTexture = Gizmo.assetManager.get("res/images/items/boomerang_wood.png", Texture.class);
        mBoomerangWoodSprite = TextureRegion.split(mBoomerangWoodTexture, 32, 32)[0];
        setAnimation(mBoomerangWoodSprite, 1 / 12f);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBoomerangWoodModel;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (mPlayerFacingDirection == PlayerActor.FACING_RIGHT) {
            mBody.setLinearVelocity(MOVEMENT_FORCE, 0);
        }
        else {
            mBody.setLinearVelocity(-MOVEMENT_FORCE, 0);
        }
    }

}