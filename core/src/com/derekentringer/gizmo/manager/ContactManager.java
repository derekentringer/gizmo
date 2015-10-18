package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.structure.door.DoorModel;
import com.derekentringer.gizmo.util.BodyUtils;

public class ContactManager {

    private static final String TAG = ContactManager.class.getSimpleName();

    public ContactManager() {

    }

    public void isPlayerAtDoor(PlayerActor playerActor, Body bodyA, Body bodyB) {
        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.DOOR)) {
            playerActor.setIsAtDoor(true);
            playerActor.setIsAtDoorUserData((DoorModel) bodyB.getUserData());
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.DOOR)) {
            playerActor.setIsAtDoor(true);
            playerActor.setIsAtDoorUserData((DoorModel) bodyA.getUserData());
        }

        if (BodyUtils.bodyTypeCheck(bodyA, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyB, BaseModelType.DOOR_OFF)) {
            playerActor.setIsAtDoor(false);
        }
        else if (BodyUtils.bodyTypeCheck(bodyB, BaseModelType.PLAYER) && BodyUtils.bodyTypeCheck(bodyA, BaseModelType.DOOR_OFF)) {
            playerActor.setIsAtDoor(false);
        }
    }

}