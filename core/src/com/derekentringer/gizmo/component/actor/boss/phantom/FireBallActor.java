package com.derekentringer.gizmo.component.actor.boss.phantom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;

public class FireBallActor extends BaseActor {

    private static final String TAG = FireBallActor.class.getSimpleName();

    private static final float MOVEMENT_FORCE = -1;

    private TextureRegion[] mFireBallSprite;
    private Texture mFireBall;

    public FireBallActor(Body body) {
        super(body);
        mFireBall = Gizmo.assetManager.get("res/image/character/boss/phantom/fireball.png", Texture.class);
        mFireBallSprite = TextureRegion.split(mFireBall, 64, 64)[0];
        setAnimation(mFireBallSprite, 1 / 12f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        mBody.setLinearVelocity(MOVEMENT_FORCE, 0);
    }

}