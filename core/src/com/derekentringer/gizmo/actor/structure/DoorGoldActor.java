package com.derekentringer.gizmo.actor.structure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class DoorGoldActor extends BaseActor {

    private TextureRegion[] doorGoldSprite;
    private Texture doorGold;

    public DoorGoldActor(Body body) {
        super(body);

        doorGold = Gizmo.assetManager.get("res/images/door_gold.png", Texture.class);
        doorGoldSprite = TextureRegion.split(doorGold, 32, 32)[0];
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

    public void startAnimation() {
        setAnimation(doorGoldSprite, 1 / 12f);
    }

}