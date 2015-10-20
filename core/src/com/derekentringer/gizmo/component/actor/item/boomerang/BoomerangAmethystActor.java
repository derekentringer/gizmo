package com.derekentringer.gizmo.component.actor.item.boomerang;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;

public class BoomerangAmethystActor extends BaseBoomerangActor {

    public BoomerangAmethystActor(Body body, int playerFacingDirection) {
        super(body);
        mPlayerFacingDirection = playerFacingDirection;
        mBoomerangTexture = Gizmo.assetManager.get("res/image/item/boomerang_amethyst.png", Texture.class);
        mBoomerangSprite = TextureRegion.split(mBoomerangTexture, 32, 32)[0];
        setAnimation(mBoomerangSprite, 1 / 12f);
    }

}