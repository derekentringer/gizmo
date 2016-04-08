package com.derekentringer.gizmo.component.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;

public class BombActor extends BaseActor {

    private TextureRegion[] mBombSprite;
    private Texture mBomb;

    public BombActor(Body body) {
        super(body);

        mBomb = Gizmo.getAssetManager().get("res/image/item/bomb_pickup_shine.png", Texture.class);
        mBombSprite = TextureRegion.split(mBomb, 32, 32)[0];

        setAnimation(mBombSprite, 1 / 4f);
    }

}