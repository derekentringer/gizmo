package com.derekentringer.gizmo.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.UserData;

public class WallActor extends BaseActor {

    public WallActor(Body body) {
        super(body);
    }

    @Override
    public UserData getUserData() {
        return null;
    }
    
}