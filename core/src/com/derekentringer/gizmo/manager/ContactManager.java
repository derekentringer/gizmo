package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;
import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.structure.door.DoorModel;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.FixtureUtils;

public class ContactManager {

    private static final String TAG = ContactManager.class.getSimpleName();

    public ContactManager() {
    }

    public void setPlayerOnGround(PlayerActor playerActor, Fixture fixtureA, Fixture fixtureB) {
        if (FixtureUtils.fixtureIsPlayerHitAreaBottom(fixtureA) && FixtureUtils.fixtureIsGround(fixtureB)) {
            playerActor.setIsOnGround(true);
        }
        else if (FixtureUtils.fixtureIsPlayerHitAreaBottom(fixtureB) && FixtureUtils.fixtureIsGround(fixtureA)) {
            playerActor.setIsOnGround(true);
        }
    }

    public void setPlayerAtDoor(PlayerActor playerActor, Body bodyA, Body bodyB) {
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

    public void setPlayerTouchingDestroyable(PlayerActor playerActor, Fixture fixtureA, Fixture fixtureB) {
        // player fixture and destroyable detection
        // right fixture sensor detection
        if (FixtureUtils.fixtureIsPlayerHitAreaRight(fixtureA) && FixtureUtils.fixtureIsDestroyable(fixtureB)) {
            playerActor.setTouchingBodyDestroyableRight(fixtureB.getBody());
        }
        else if (FixtureUtils.fixtureIsPlayerHitAreaRight(fixtureB) && FixtureUtils.fixtureIsDestroyable(fixtureA)) {
            playerActor.setTouchingBodyDestroyableRight(fixtureA.getBody());
        }

        // bottom fixture sensor detection
        if (FixtureUtils.fixtureIsPlayerHitAreaBottom(fixtureA) && FixtureUtils.fixtureIsDestroyable(fixtureB)) {
            playerActor.setIsOnGround(true);
            playerActor.setTouchingBodyDestroyableBottom(fixtureB.getBody());
        }
        else if (FixtureUtils.fixtureIsPlayerHitAreaBottom(fixtureB) && FixtureUtils.fixtureIsDestroyable(fixtureA)) {
            playerActor.setIsOnGround(true);
            playerActor.setTouchingBodyDestroyableBottom(fixtureA.getBody());
        }

        // left fixture sensor detection
        if (FixtureUtils.fixtureIsPlayerHitAreaLeft(fixtureA) && FixtureUtils.fixtureIsDestroyable(fixtureB)) {
            playerActor.setTouchingBodyDestroyableLeft(fixtureB.getBody());
        }
        else if (FixtureUtils.fixtureIsPlayerHitAreaLeft(fixtureB) && FixtureUtils.fixtureIsDestroyable(fixtureA)) {
            playerActor.setTouchingBodyDestroyableLeft(fixtureA.getBody());
        }
    }

}