package com.derekentringer.gizmo.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorOffActor extends BaseActor {

    public DoorOffActor(Body body) {
        super(body);
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

}