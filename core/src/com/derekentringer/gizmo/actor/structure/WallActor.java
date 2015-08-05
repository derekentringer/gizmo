package com.derekentringer.gizmo.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.structure.WallModel;

public class WallActor extends BaseActor {

    private WallModel wallModel = new WallModel();

    public WallActor(Body body) {
        super(body);
    }

    @Override
    public BaseModel getUserData() {
        return wallModel;
    }
    
}