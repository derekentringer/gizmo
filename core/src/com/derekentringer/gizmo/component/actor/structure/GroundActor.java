package com.derekentringer.gizmo.component.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.structure.GroundModel;

public class GroundActor extends BaseActor {

    private static final String TAG = GroundActor.class.getSimpleName();

    private GroundModel mGroundModel = new GroundModel();

    public GroundActor(Body body) {
        super(body);
    }

    @Override
    public BaseModel getBaseModel() {
        return mGroundModel;
    }

}