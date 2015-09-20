package com.derekentringer.gizmo.component.actor.structure.door;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorOffActor extends BaseActor {

    public DoorOffActor(Body body) {
        super(body);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBaseModel;
    }

}