package com.derekentringer.gizmo.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class KeyActor extends BaseActor {

    private TextureRegion[] keySprite;
    private Texture key;

    public KeyActor(Body body) {
        super(body);

        key = Gizmo.assetManager.get("res/images/key.png", Texture.class);
        keySprite = TextureRegion.split(key, 32, 32)[0];

        setAnimation(keySprite, 1/12f);
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

}