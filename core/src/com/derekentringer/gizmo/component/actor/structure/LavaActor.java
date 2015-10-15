package com.derekentringer.gizmo.component.actor.structure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;

public class LavaActor extends BaseActor {

    private static final String TAG = LavaActor.class.getSimpleName();

    private TextureRegion[] mLavaSprite;
    private Texture mLava;

    public LavaActor(Body body) {
        super(body);

        mLava = Gizmo.assetManager.get("res/image/tile/lava.png", Texture.class);
        mLavaSprite = TextureRegion.split(mLava, 32, 32)[0];

        setIsPlayingAnimation(true);
        setAnimation(mLavaSprite, 1 / 12f);
    }

}