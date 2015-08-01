package com.derekentringer.gizmo.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class DoorOffActor extends BaseActor {

    public DoorOffActor(Body body) {
        super(body);
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

}