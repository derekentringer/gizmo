package com.derekentringer.gizmo.components.actor.structure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorGoldActor extends BaseActor {

    private TextureRegion[] doorGoldSprite;
    private Texture doorGold;

    public DoorGoldActor(Body body) {
        super(body);

        doorGold = Gizmo.assetManager.get("res/images/door_gold.png", Texture.class);
        doorGoldSprite = TextureRegion.split(doorGold, 32, 32)[0];
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

    //TODO blah this doesn't work
    //not sure it's needed really
    public void startAnimation() {
        setAnimation(doorGoldSprite, 1 / 12f);
    }

}