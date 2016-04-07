package com.derekentringer.gizmo.component.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;

public class HeartActor extends BaseActor {

    private static final String TAG = HeartActor.class.getSimpleName();

    private TextureRegion[] mHeartSprite;
    private Texture mHeart;

    public HeartActor(Body body) {
        super(body);

        mHeart = Gizmo.getAssetManager().get("res/image/object/heart.png", Texture.class);
        mHeartSprite = TextureRegion.split(mHeart, 32, 32)[0];

        setAnimation(mHeartSprite, 1 / 12f);
    }

}