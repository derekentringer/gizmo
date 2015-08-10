package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorGoldActor extends BaseActor {

    private TextureRegion[] mDoorGoldSprite;
    private Texture mDoorGold;

    public DoorGoldActor(Body body, boolean isLocked) {
        super(body);
        if(isLocked) {
            mDoorGold = Gizmo.assetManager.get("res/images/door_gold.png", Texture.class);
        }
        else {
            mDoorGold = Gizmo.assetManager.get("res/images/door_opened.png", Texture.class);
        }
        mDoorGoldSprite = TextureRegion.split(mDoorGold, 32, 32)[0];
        setAnimation(mDoorGoldSprite, 1 / 12f);
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

    public void startAnimation() {
        setAnimation(mDoorGoldSprite, 1 / 12f);
    }

}