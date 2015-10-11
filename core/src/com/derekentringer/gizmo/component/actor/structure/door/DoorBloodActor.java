package com.derekentringer.gizmo.component.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.IBaseActor;
import com.derekentringer.gizmo.component.actor.structure.door.interfaces.IDoor;
import com.derekentringer.gizmo.model.BaseModel;

import java.util.ArrayList;

public class DoorBloodActor extends BaseActor implements IBaseActor {

    private static final String TAG = DoorBloodActor.class.getSimpleName();

    private ArrayList<IDoor> listeners = new ArrayList<IDoor>();

    private TextureRegion[] mDoorBloodSprite;
    private Texture mDoorBlood;

    private TextureRegion[] mDoorOpenSprite;
    private Texture mDoorOpen;

    public DoorBloodActor(Body body, boolean isLocked) {
        super(body);
        addListener(this);
        if (isLocked) {
            mDoorBlood = Gizmo.assetManager.get("res/image/door/door_blood_opening.png", Texture.class);
        }
        else {
            mDoorBlood = Gizmo.assetManager.get("res/image/door/door_opened.png", Texture.class);
        }

        mDoorOpen = Gizmo.assetManager.get("res/image/door/door_opened.png", Texture.class);
        mDoorOpenSprite = TextureRegion.split(mDoorOpen, 32, 32)[0];

        mDoorBloodSprite = TextureRegion.split(mDoorBlood, 32, 32)[0];
        setIsPlayingAnimation(false);
        setAnimation(mDoorBloodSprite, 1 / 12f);
    }

    public void addListener(IDoor listener) {
        listeners.add(listener);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBaseModel;
    }

    public void startAnimation() {
        setIsPlayingAnimation(true);
    }

    @Override
    public void isAnimationFinished(boolean isFinished) {
        if (isFinished) {
            setAnimation(mDoorOpenSprite, 1 / 12f);
            for (IDoor listener : listeners) {
                listener.doorAnimationComplete(this);
            }
        }
    }

}