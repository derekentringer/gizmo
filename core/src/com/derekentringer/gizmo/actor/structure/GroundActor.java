package com.derekentringer.gizmo.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.UserData;

public class GroundActor extends BaseActor {

    public GroundActor(Body body) {
        super(body);
    }

    @Override
    public UserData getUserData() {
        return null;
    }

}