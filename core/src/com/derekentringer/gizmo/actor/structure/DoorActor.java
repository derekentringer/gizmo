package com.derekentringer.gizmo.actor.structure;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;

public class DoorActor extends BaseActor {

    private String doorType;

    public DoorActor(Body body, String doorType) {
        super(body);
        this.doorType = doorType;
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

    public String getDoorType() {
        return doorType;
    }

}