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

public class DoorBronzeActor extends BaseActor implements IBaseActor {

    private static final String TAG = DoorBronzeActor.class.getSimpleName();

    private ArrayList<IDoor> listeners = new ArrayList<IDoor>();

    private TextureRegion[] mDoorBronzeSprite;
    private Texture mDoorBronze;

    private TextureRegion[] mDoorOpenSprite;
    private Texture mDoorOpen;

    public DoorBronzeActor(Body body, boolean isLocked) {
        super(body);
        addListener(this);
        if (isLocked) {
            mDoorBronze = Gizmo.assetManager.get("res/image/door/door_bronze_opening.png", Texture.class);
        }
        else {
            mDoorBronze = Gizmo.assetManager.get("res/image/door/door_opened.png", Texture.class);
        }

        mDoorOpen = Gizmo.assetManager.get("res/image/door/door_opened.png", Texture.class);
        mDoorOpenSprite = TextureRegion.split(mDoorOpen, 32, 32)[0];

        mDoorBronzeSprite = TextureRegion.split(mDoorBronze, 32, 32)[0];
        setIsPlayingAnimation(false);
        setAnimation(mDoorBronzeSprite, 1 / 12f);
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