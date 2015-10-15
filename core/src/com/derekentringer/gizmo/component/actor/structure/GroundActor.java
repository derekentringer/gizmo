package com.derekentringer.gizmo.component.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.component.actor.BaseActor;

public class GroundActor extends BaseActor {

    private static final String TAG = GroundActor.class.getSimpleName();

    public GroundActor(Body body) {
        super(body);
    }

}