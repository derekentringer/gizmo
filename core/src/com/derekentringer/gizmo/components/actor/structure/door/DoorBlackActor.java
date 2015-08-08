package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorBlackActor extends BaseActor {

    private TextureRegion[] doorBlackSprite;
    private Texture doorBlack;

    public DoorBlackActor(Body body) {
        super(body);
        //TODO create needed asset
        doorBlack = Gizmo.assetManager.get("res/images/door_gold.png", Texture.class);
        doorBlackSprite = TextureRegion.split(doorBlack, 32, 32)[0];
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

    //TODO blah this doesn't work
    //not sure it's needed really
    public void startAnimation() {
        setAnimation(doorBlackSprite, 1 / 12f);
    }

}