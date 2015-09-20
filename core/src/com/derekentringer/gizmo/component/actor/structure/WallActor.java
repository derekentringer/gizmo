package com.derekentringer.gizmo.component.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class WallActor extends BaseActor {

    private static final String TAG = WallActor.class.getSimpleName();

    public WallActor(Body body) {
        super(body);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBaseModel;
    }

}