package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorBlackActor extends BaseActor {

    private TextureRegion[] mDoorBlackSprite;
    private Texture mDoorBlack;

    public DoorBlackActor(Body body, boolean isLocked) {
        super(body);
        if(isLocked) {
            mDoorBlack = Gizmo.assetManager.get("res/images/door_black.png", Texture.class);
        }
        else {
            mDoorBlack = Gizmo.assetManager.get("res/images/door_opened.png", Texture.class);
        }
        mDoorBlackSprite = TextureRegion.split(mDoorBlack, 32, 32)[0];
        setAnimation(mDoorBlackSprite, 1 / 12f);
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

    public void startAnimation() {
        setAnimation(mDoorBlackSprite, 1 / 12f);
    }

}