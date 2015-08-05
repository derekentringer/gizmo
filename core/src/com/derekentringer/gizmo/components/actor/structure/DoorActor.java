package com.derekentringer.gizmo.components.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DoorActor extends BaseActor {

    public DoorActor(Body body) {
        super(body);
    }

    @Override
    public BaseModel getUserData() {
        return null;
    }

}