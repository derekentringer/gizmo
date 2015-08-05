package com.derekentringer.gizmo.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.structure.GroundModel;

public class GroundActor extends BaseActor {

    private GroundModel groundModel = new GroundModel();

    public GroundActor(Body body) {
        super(body);
    }

    @Override
    public BaseModel getUserData() {
        return groundModel;
    }

}