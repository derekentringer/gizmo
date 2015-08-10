package com.derekentringer.gizmo.model.body;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.model.BaseModel;

public class DeleteBody {

    private Body mBody;
    private BaseModel mBaseModel;

    public DeleteBody(BaseModel baseModel, Body body) {
        mBaseModel = baseModel;
        mBody = body;
    }

    public BaseModel getBaseModel() {
        return mBaseModel;
    }

    public Body getBody() {
        return mBody;
    }

    public void setBody(Body body) {
        mBody = body;
    }

}