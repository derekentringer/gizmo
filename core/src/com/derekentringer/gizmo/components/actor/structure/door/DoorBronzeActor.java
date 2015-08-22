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

public class DoorBronzeActor extends BaseActor implements IBaseActor {

    private static final String TAG = DoorBronzeActor.class.getSimpleName();

    private ArrayList<IDoor> listeners = new ArrayList<IDoor>();

    private TextureRegion[] mDoorBronzeSprite;
    private Texture mDoorBronze;

    public DoorBronzeActor(Body body, boolean isLocked) {
        super(body);
        addListener(this);
        if(isLocked) {
            mDoorBronze = Gizmo.assetManager.get("res/images/doors/door_bronze_opening.png", Texture.class);
        }
        else {
            mDoorBronze = Gizmo.assetManager.get("res/images/doors/door_opened.png", Texture.class);
        }
        mDoorBronzeSprite = TextureRegion.split(mDoorBronze, 32, 32)[0];
        setIsPlayingAnimation(false);
        setAnimation(mDoorBronzeSprite, 1 / 12f);
    }

    public void addListener(IDoor listener) {
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
            for (IDoor listener : listeners) {
                listener.doorAnimationComplete(this);
            }
        }
    }

}