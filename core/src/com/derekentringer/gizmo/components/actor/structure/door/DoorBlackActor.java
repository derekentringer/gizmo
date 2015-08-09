package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorBlackActor extends BaseActor {

    private TextureRegion[] sDoorBlackSprite;
    private Texture sDoorBlack;

    public DoorBlackActor(Body body, boolean isLocked) {
        super(body);
        if(isLocked) {
            sDoorBlack = Gizmo.assetManager.get("res/images/door_black.png", Texture.class);
        }
        else {
            sDoorBlack = Gizmo.assetManager.get("res/images/door_opened.png", Texture.class);
        }
        sDoorBlackSprite = TextureRegion.split(sDoorBlack, 32, 32)[0];
        setAnimation(sDoorBlackSprite, 1 / 12f);
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

    public void startAnimation() {
        setAnimation(sDoorBlackSprite, 1 / 12f);
    }

}