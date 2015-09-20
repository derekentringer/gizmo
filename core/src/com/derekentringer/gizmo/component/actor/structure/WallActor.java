package com.derekentringer.gizmo.component.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.structure.WallModel;

public class WallActor extends BaseActor {

    private static final String TAG = WallActor.class.getSimpleName();

    private WallModel mWallModel = new WallModel();

    public WallActor(Body body) {
        super(body);
    }

    @Override
    public BaseModel getBaseModel() {
        return mWallModel;
    }

}