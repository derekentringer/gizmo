package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorBronzeActor extends BaseActor {

    private TextureRegion[] mDoorBronzeSprite;
    private Texture mDoorBronze;

    public DoorBronzeActor(Body body, boolean isLocked) {
        super(body);
        if(isLocked) {
            mDoorBronze = Gizmo.assetManager.get("res/images/door_bronze.png", Texture.class);
        }
        else {
            mDoorBronze = Gizmo.assetManager.get("res/images/door_opened.png", Texture.class);
        }
        mDoorBronzeSprite = TextureRegion.split(mDoorBronze, 32, 32)[0];
        setAnimation(mDoorBronzeSprite, 1 / 12f);
    }

    @Override
    public BaseModel getPlayerModel() {
        return null;
    }

    public void startAnimation() {
        setAnimation(mDoorBronzeSprite, 1 / 12f);
    }

}