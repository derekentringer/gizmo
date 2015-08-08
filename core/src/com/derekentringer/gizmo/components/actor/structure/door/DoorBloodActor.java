package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorBloodActor extends BaseActor {

    private TextureRegion[] doorBloodSprite;
    private Texture doorBlood;

    public DoorBloodActor(Body body) {
        super(body);
        //TODO create needed asset
        doorBlood = Gizmo.assetManager.get("res/images/door_gold.png", Texture.class);
        doorBloodSprite = TextureRegion.split(doorBlood, 32, 32)[0];
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

    //TODO blah this doesn't work
    //not sure it's needed really
    public void startAnimation() {
        setAnimation(doorBloodSprite, 1 / 12f);
    }

}