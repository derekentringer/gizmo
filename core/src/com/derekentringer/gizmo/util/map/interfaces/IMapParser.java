package com.derekentringer.gizmo.util.map.interfaces;

import com.derekentringer.gizmo.component.actor.player.PlayerActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorBlackActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorBloodActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorBronzeActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorGoldActor;

public interface IMapParser {
    void setPlayerActor(PlayerActor playerActor);
    void setLockedGoldDoor(DoorGoldActor goldDoorActor);
    void setLockedBronzeDoor(DoorBronzeActor bronzeDoorActor);
    void setLockedBloodDoor(DoorBloodActor bloodDoorActor);
    void setLockedBlackDoor(DoorBlackActor blackDoorActor);
}