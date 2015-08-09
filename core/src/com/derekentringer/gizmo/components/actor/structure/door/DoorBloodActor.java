package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorBloodActor extends BaseActor {

    private TextureRegion[] sDoorBloodSprite;
    private Texture sDoorBlood;

    public DoorBloodActor(Body body, boolean isLocked) {
        super(body);
        if(isLocked) {
            sDoorBlood = Gizmo.assetManager.get("res/images/door_gold.png", Texture.class);
        }
        else {
            sDoorBlood = Gizmo.assetManager.get("res/images/door_opened.png", Texture.class);
        }
        sDoorBloodSprite = TextureRegion.split(sDoorBlood, 32, 32)[0];
        setAnimation(sDoorBloodSprite, 1 / 12f);
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

    public void startAnimation() {
        setAnimation(sDoorBloodSprite, 1 / 12f);
    }

}