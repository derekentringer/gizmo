package com.derekentringer.gizmo.component.actor.misc.pickup_animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.IBaseActor;

public class PickupHeartActor extends BaseActor implements IBaseActor {

    private static final String TAG = PickupHeartActor.class.getSimpleName();

    private TextureRegion[] mPickupHeartSprite;
    private Texture mPickupHeart;

    public PickupHeartActor(Body body) {
        super(body);
        addListener(this);
        mPickupHeart = Gizmo.assetManager.get("res/image/tile/pickup_heart.png", Texture.class);
        mPickupHeartSprite = TextureRegion.split(mPickupHeart, 32, 32)[0];

        setAnimation(mPickupHeartSprite, 1 / 12f);
    }

    @Override
    public void isAnimationFinished(boolean isFinished) {
        if (isFinished == true) {
            setIsPlayingAnimation(false);
        }
    }

}