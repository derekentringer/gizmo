package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorBronzeActor extends BaseActor {

    private TextureRegion[] doorBronzeSprite;
    private Texture doorBronze;

    public DoorBronzeActor(Body body) {
        super(body);
        //TODO create needed asset
        doorBronze = Gizmo.assetManager.get("res/images/door_gold.png", Texture.class);
        doorBronzeSprite = TextureRegion.split(doorBronze, 32, 32)[0];
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

    //TODO blah this doesn't work
    //not sure it's needed really
    public void startAnimation() {
        setAnimation(doorBronzeSprite, 1 / 12f);
    }

}