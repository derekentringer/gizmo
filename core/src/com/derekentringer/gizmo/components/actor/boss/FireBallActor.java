package com.derekentringer.gizmo.components.actor.boss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.enemy.FireBallModel;
import com.derekentringer.gizmo.util.BodyUtils;

public class FireBallActor extends BaseActor {

    private static final String TAG = FireBallActor.class.getSimpleName();

    private static final int MOVEMENT_FORCE = 5;

    private FireBallModel mFireBallModel = new FireBallModel();

    private TextureRegion[] mFireBallSprite;
    private Texture mFireBall;

    public FireBallActor(Body body) {
        super(body);

        mFireBall = Gizmo.assetManager.get("res/images/enemies/boss/fireball.png", Texture.class);
        mFireBallSprite = TextureRegion.split(mFireBall, 64, 64)[0];

        setAnimation(mFireBallSprite, 1 / 12f);
    }

    @Override
    public BaseModel getBaseModel() {
        return mFireBallModel;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        BodyUtils.applyLinearImpulseToBody(mBody, -MOVEMENT_FORCE, "x");
    }

}