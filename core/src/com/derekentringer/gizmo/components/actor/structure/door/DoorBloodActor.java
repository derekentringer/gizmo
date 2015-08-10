package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorBloodActor extends BaseActor {

    private TextureRegion[] mDoorBloodSprite;
    private Texture mDoorBlood;

    public DoorBloodActor(Body body, boolean isLocked) {
        super(body);
        if(isLocked) {
            mDoorBlood = Gizmo.assetManager.get("res/images/door_blood.png", Texture.class);
        }
        else {
            mDoorBlood = Gizmo.assetManager.get("res/images/door_opened.png", Texture.class);
        }
        mDoorBloodSprite = TextureRegion.split(mDoorBlood, 32, 32)[0];
        setAnimation(mDoorBloodSprite, 1 / 12f);
    }

    @Override
    public BaseModel getPlayerModel() {
        return null;
    }

    public void startAnimation() {
        setAnimation(mDoorBloodSprite, 1 / 12f);
    }

}