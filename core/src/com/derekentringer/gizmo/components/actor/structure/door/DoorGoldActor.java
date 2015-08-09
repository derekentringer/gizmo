package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorGoldActor extends BaseActor {

    private TextureRegion[] sDoorGoldSprite;
    private Texture sDoorGold;
    private boolean sIsLocked;

    public DoorGoldActor(Body body, boolean isLocked) {
        super(body);
        sIsLocked = isLocked;
        if(sIsLocked) {
            sDoorGold = Gizmo.assetManager.get("res/images/door_gold.png", Texture.class);
        }
        else {
            sDoorGold = Gizmo.assetManager.get("res/images/door_opened.png", Texture.class);
        }
        sDoorGoldSprite = TextureRegion.split(sDoorGold, 32, 32)[0];
        setAnimation(sDoorGoldSprite, 1 / 12f);
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

    public void startAnimation() {
        setAnimation(sDoorGoldSprite, 1 / 12f);
    }

    public boolean getIsLocked() {
        return sIsLocked;
    }

    public void setIsLocked(boolean isLocked) {
        sIsLocked = isLocked;
    }

}