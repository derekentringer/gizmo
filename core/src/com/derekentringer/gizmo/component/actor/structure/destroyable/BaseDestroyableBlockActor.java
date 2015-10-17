package com.derekentringer.gizmo.component.actor.structure.destroyable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.component.actor.BaseActor;

public class BaseDestroyableBlockActor extends BaseActor {

    private Vector2 mPlayerPosition = new Vector2();

    public BaseDestroyableBlockActor(Body body) {
        super(body);
    }

    public Vector2 getPlayerPosition() {
        return mPlayerPosition;
    }

    public void setPlayerPosition(Vector2 playerPosition) {
        mPlayerPosition.x = playerPosition.x;
        mPlayerPosition.y = playerPosition.y;
    }

}