package com.derekentringer.gizmo.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class GroundActor extends BaseActor {

    public GroundActor(Body body) {
        super(body);
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

}