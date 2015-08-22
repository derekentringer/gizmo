package com.derekentringer.gizmo.components.actor.structure.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.components.actor.IBaseActor;
import com.derekentringer.gizmo.components.actor.structure.door.interfaces.IDoor;
import com.derekentringer.gizmo.model.BaseModel;

import java.util.ArrayList;

public class DoorBloodActor extends BaseActor implements IBaseActor {

    private static final String TAG = DoorBloodActor.class.getSimpleName();

    private ArrayList<IDoor> listeners = new ArrayList<IDoor>();

    private TextureRegion[] mDoorBloodSprite;
    private Texture mDoorBlood;

    public DoorBloodActor(Body body, boolean isLocked) {
        super(body);
        addListener(this);
        if(isLocked) {
            mDoorBlood = Gizmo.assetManager.get("res/images/doors/door_blood_opening.png", Texture.class);
        }
        else {
            mDoorBlood = Gizmo.assetManager.get("res/images/doors/door_opened.png", Texture.class);
        }
        mDoorBloodSprite = TextureRegion.split(mDoorBlood, 32, 32)[0];
        setIsPlayingAnimation(false);
        setAnimation(mDoorBloodSprite, 1 / 12f);
    }

    public void addListener(IDoor listener) {
        listeners.add(listener);
    }

    @Override
    public BaseModel getPlayerModel() {
        return null;
    }

    public void startAnimation() {
        setIsPlayingAnimation(true);
    }

    @Override
    public void isAnimationFinished(boolean isFinished) {
        if (isFinished) {
            for (IDoor listener : listeners) {
                listener.doorAnimationComplete(this);
            }
        }
    }

}