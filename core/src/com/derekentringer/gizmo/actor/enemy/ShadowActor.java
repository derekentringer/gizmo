package com.derekentringer.gizmo.actor.enemy;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class ShadowActor extends BaseActor {

    private ShadowActor(Body body) {
        super(body);
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

}