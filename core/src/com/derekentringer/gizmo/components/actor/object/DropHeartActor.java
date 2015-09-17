package com.derekentringer.gizmo.components.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.util.BodyUtils;

public class DropHeartActor extends BaseActor {

    private static final String TAG = DropHeartActor.class.getSimpleName();

    private TextureRegion[] mDropHeartSprite;
    private Texture mDropHeart;

    float mTimeAccumulated;
    float speedY;
    float speedX;
    
    public DropHeartActor(Body body) {
        super(body);

        mDropHeart = Gizmo.assetManager.get("res/images/items/drop_heart.png", Texture.class);
        mDropHeartSprite = TextureRegion.split(mDropHeart, 8, 8)[0];

        setAnimation(mDropHeartSprite, 1 / 12f);

        speedX = MathUtils.random(-2, 2);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBaseModel;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        mTimeAccumulated += delta;
        speedY = 0.5f - (mTimeAccumulated / 1);
        if (speedY <= 0) {
            speedY = -0.3f;
        }
        if (speedY > 0) {
            speedX = speedX - (mTimeAccumulated / 1);
            if (speedX <= 0) {
                speedX = 0;
            }
        }
        else {
            speedX = 0;
        }
        BodyUtils.applyLinearImpulseToBody(mBody, speedY, "y");
        BodyUtils.applyLinearImpulseToBody(mBody, speedX, "x");
    }

}