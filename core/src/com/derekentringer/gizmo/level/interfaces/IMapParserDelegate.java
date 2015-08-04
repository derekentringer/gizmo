package com.derekentringer.gizmo.level.interfaces;

import com.derekentringer.gizmo.actor.player.PlayerActor;
import com.derekentringer.gizmo.actor.structure.DoorGoldActor;

public interface IMapParserDelegate {

    void setPlayerActor(PlayerActor playerActor);
    void setLockedGoldDoor(DoorGoldActor goldDoorActor);

}