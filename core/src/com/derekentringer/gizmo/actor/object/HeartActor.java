package com.derekentringer.gizmo.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class HeartActor extends BaseActor {

    private TextureRegion[] heartSprite;
    private Texture heart;

    public HeartActor(Body body) {
        super(body);

        heart = Gizmo.assetManager.get("res/images/heart.png", Texture.class);
        heartSprite = TextureRegion.split(heart, 32, 32)[0];

        setAnimation(heartSprite, 1 / 12f);
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

}