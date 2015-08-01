package com.derekentringer.gizmo.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class DeleteBody {

    private Body body;
    private ObjectData objectData;

    public DeleteBody(ObjectData objectData, Body body) {
        this.objectData = objectData;
        this.body = body;
    }

    public ObjectData getObjectData() {
        return objectData;
    }

    public void setUserData(ObjectData objectData) {
        this.objectData = objectData;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

}