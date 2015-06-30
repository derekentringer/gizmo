package com.derekentringer.gizmo.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class DoorActor extends BaseActor {

    public DoorActor(Body body) {
        super(body);
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

}