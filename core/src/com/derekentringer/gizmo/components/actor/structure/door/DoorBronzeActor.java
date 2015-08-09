package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorBronzeActor extends BaseActor {

    private TextureRegion[] sDoorBronzeSprite;
    private Texture sDoorBronze;

    public DoorBronzeActor(Body body, boolean isLocked) {
        super(body);
        if(isLocked) {
            sDoorBronze = Gizmo.assetManager.get("res/images/door_gold.png", Texture.class);
        }
        else {
            sDoorBronze = Gizmo.assetManager.get("res/images/door_opened.png", Texture.class);
        }
        sDoorBronzeSprite = TextureRegion.split(sDoorBronze, 32, 32)[0];
        setAnimation(sDoorBronzeSprite, 1 / 12f);
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

    public void startAnimation() {
        setAnimation(sDoorBronzeSprite, 1 / 12f);
    }

}