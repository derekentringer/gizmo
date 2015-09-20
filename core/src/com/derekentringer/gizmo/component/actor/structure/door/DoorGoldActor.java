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

public class DoorGoldActor extends BaseActor implements IBaseActor {

    private static final String TAG = DoorGoldActor.class.getSimpleName();

    private ArrayList<IDoor> listeners = new ArrayList<IDoor>();

    private TextureRegion[] mDoorGoldSprite;
    private Texture mDoorGold;

    private TextureRegion[] mDoorOpenSprite;
    private Texture mDoorOpen;

    public DoorGoldActor(Body body, boolean isLocked) {
        super(body);
        addListener(this);
        if (isLocked) {
            mDoorGold = Gizmo.assetManager.get("res/images/doors/door_gold_opening.png", Texture.class);
        }
        else {
            mDoorGold = Gizmo.assetManager.get("res/images/doors/door_opened.png", Texture.class);
        }

        mDoorOpen = Gizmo.assetManager.get("res/images/doors/door_opened.png", Texture.class);
        mDoorOpenSprite = TextureRegion.split(mDoorOpen, 32, 32)[0];

        mDoorGoldSprite = TextureRegion.split(mDoorGold, 32, 32)[0];
        setIsPlayingAnimation(false);
        setAnimation(mDoorGoldSprite, 1 / 12f);
    }

    public void addListener(com.derekentringer.gizmo.component.actor.structure.door.interfaces.IDoor listener) {
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
            for (com.derekentringer.gizmo.component.actor.structure.door.interfaces.IDoor listener : listeners) {
                listener.doorAnimationComplete(this);
            }
        }
    }

}