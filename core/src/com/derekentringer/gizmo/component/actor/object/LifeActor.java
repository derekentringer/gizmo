package com.derekentringer.gizmo.component.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;

public class LifeActor extends BaseActor {

    private static final String TAG = LifeActor.class.getSimpleName();

    private TextureRegion[] mLifeSprite;
    private Texture mLife;

    public LifeActor(Body body) {
        super(body);
        mLife = Gizmo.assetManager.get("res/image/object/life.png", Texture.class);
        mLifeSprite = TextureRegion.split(mLife, 32, 32)[0];
        setAnimation(mLifeSprite, 1 / 12f);
    }

}