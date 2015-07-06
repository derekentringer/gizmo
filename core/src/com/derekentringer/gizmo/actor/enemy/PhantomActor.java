package com.derekentringer.gizmo.actor.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class PhantomActor extends BaseActor {

    private TextureRegion[] phantomLeftSprite;
    private Texture phantomLeft;

    public PhantomActor(Body body) {
        super(body);

        phantomLeft = Gizmo.assetManager.get("res/images/phantom_left.png", Texture.class);
        phantomLeftSprite = TextureRegion.split(phantomLeft, 32, 32)[0];

        setAnimation(phantomLeftSprite, 1/12f);
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

}