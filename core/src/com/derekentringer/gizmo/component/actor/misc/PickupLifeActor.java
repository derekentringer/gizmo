package com.derekentringer.gizmo.component.actor.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.IBaseActor;

public class PickupLifeActor extends BaseActor implements IBaseActor {

    private static final String TAG = PickupLifeActor.class.getSimpleName();

    private TextureRegion[] mPickupLifeSprite;
    private Texture mPickupLife;

    public PickupLifeActor(Body body) {
        super(body);
        addListener(this);
        mPickupLife = Gizmo.assetManager.get("res/image/tile/pickup_life.png", Texture.class);
        mPickupLifeSprite = TextureRegion.split(mPickupLife, 32, 32)[0];

        setAnimation(mPickupLifeSprite, 1 / 12f);
    }

    @Override
    public void isAnimationFinished(boolean isFinished) {
        if (isFinished == true) {
            setIsPlayingAnimation(false);
        }
    }

}