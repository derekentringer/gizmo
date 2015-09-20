package com.derekentringer.gizmo.component.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.IBaseActor;
import com.derekentringer.gizmo.model.BaseModel;

import java.util.ArrayList;

public class DoorBlackActor extends BaseActor implements IBaseActor {

    private static final String TAG = DoorBlackActor.class.getSimpleName();

    private ArrayList<com.derekentringer.gizmo.component.actor.structure.door.interfaces.IDoor> listeners = new ArrayList<com.derekentringer.gizmo.component.actor.structure.door.interfaces.IDoor>();

    private TextureRegion[] mDoorBlackSprite;
    private Texture mDoorBlack;

    private TextureRegion[] mDoorOpenSprite;
    private Texture mDoorOpen;

    public DoorBlackActor(Body body, boolean isLocked) {
        super(body);
        addListener(this);
        if (isLocked) {
            mDoorBlack = Gizmo.assetManager.get("res/images/doors/door_black_opening.png", Texture.class);
        }
        else {
            mDoorBlack = Gizmo.assetManager.get("res/images/doors/door_opened.png", Texture.class);
        }

        mDoorOpen = Gizmo.assetManager.get("res/images/doors/door_opened.png", Texture.class);
        mDoorOpenSprite = TextureRegion.split(mDoorOpen, 32, 32)[0];

        mDoorBlackSprite = TextureRegion.split(mDoorBlack, 32, 32)[0];
        setIsPlayingAnimation(false);
        setAnimation(mDoorBlackSprite, 1 / 12f);
    }

    public void addListener(com.derekentringer.gizmo.component.actor.structure.door.interfaces.IDoor listener) {
        listeners.add(listener);
    }

    @Override
    public BaseModel getBaseModel() {
        return null;
    }

    public void startAnimation() {
        setIsPlayingAnimation(true);
    }

    @Override
    public void isAnimationFinished(boolean isFinished) {
        if (isFinished) {
            setAnimation(mDoorOpenSprite, 1 / 12f);
            for (com.derekentringer.gizmo.component.actor.structure.door.interfaces.IDoor listener : listeners) {
                listener.doorAnimationComplete(this);
            }
        }
    }

}