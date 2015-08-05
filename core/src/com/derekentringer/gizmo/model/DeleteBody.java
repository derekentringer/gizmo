package com.derekentringer.gizmo.model;

import com.badlogic.gdx.physics.box2d.Body;

public class DeleteBody {

    private Body body;
    private BaseModel baseModel;

    public DeleteBody(BaseModel baseModel, Body body) {
        this.baseModel = baseModel;
        this.body = body;
    }

    public BaseModel getBaseModel() {
        return baseModel;
    }

    public void setBaseModel(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

}