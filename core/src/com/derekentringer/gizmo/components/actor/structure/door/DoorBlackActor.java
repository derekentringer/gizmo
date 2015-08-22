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

public class DoorBlackActor extends BaseActor implements IBaseActor {

    private static final String TAG = DoorBlackActor.class.getSimpleName();

    private ArrayList<IDoor> listeners = new ArrayList<IDoor>();

    private TextureRegion[] mDoorBlackSprite;
    private Texture mDoorBlack;

    public DoorBlackActor(Body body, boolean isLocked) {
        super(body);
        addListener(this);
        if(isLocked) {
            mDoorBlack = Gizmo.assetManager.get("res/images/door_black_opening.png", Texture.class);
        }
        else {
            mDoorBlack = Gizmo.assetManager.get("res/images/door_opened.png", Texture.class);
        }
        mDoorBlackSprite = TextureRegion.split(mDoorBlack, 32, 32)[0];
        setIsPlayingAnimation(false);
        setAnimation(mDoorBlackSprite, 1 / 12f);
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