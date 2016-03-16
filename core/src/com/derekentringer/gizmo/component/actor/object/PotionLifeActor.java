package com.derekentringer.gizmo.component.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;

public class PotionLifeActor extends BaseActor {

    private TextureRegion[] mPotionLifeSprite;
    private Texture mPotionLife;

    public PotionLifeActor(Body body) {
        super(body);

        mPotionLife = Gizmo.assetManager.get("res/image/object/potion_life.png", Texture.class);
        mPotionLifeSprite = TextureRegion.split(mPotionLife, 32, 32)[0];

        setAnimation(mPotionLifeSprite, 1 / 4f);
    }

}