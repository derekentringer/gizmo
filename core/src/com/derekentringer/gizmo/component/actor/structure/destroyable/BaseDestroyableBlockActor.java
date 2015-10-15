package com.derekentringer.gizmo.component.actor.structure.destroyable;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class BaseDestroyableBlockActor extends BaseActor {

    public BaseDestroyableBlockActor(Body body) {
        super(body);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBaseModel;
    }

}