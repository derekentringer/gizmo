package com.derekentringer.gizmo.component.actor.pickup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.IBaseActor;

public class PickupKeyActor extends BaseActor implements IBaseActor {

    private static final String TAG = PickupKeyActor.class.getSimpleName();

    private TextureRegion[] mPickupKeySprite;
    private Texture mPickupKey;

    public PickupKeyActor(Body body) {
        super(body);
        addListener(this);
        mPickupKey = Gizmo.assetManager.get("res/image/tile/pickup_key.png", Texture.class);
        mPickupKeySprite = TextureRegion.split(mPickupKey, 32, 32)[0];

        setAnimation(mPickupKeySprite, 1 / 30f);
    }

    @Override
    public void isAnimationFinished(boolean isFinished) {
        if (isFinished == true) {
            setIsPlayingAnimation(false);
        }
    }

}