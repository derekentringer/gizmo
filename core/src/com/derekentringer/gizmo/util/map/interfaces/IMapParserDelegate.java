package com.derekentringer.gizmo.util.map.interfaces;

import com.derekentringer.gizmo.components.actor.player.PlayerActor;
import com.derekentringer.gizmo.components.actor.structure.DoorGoldActor;

public interface IMapParserDelegate {

    void setPlayerActor(PlayerActor playerActor);
    void setLockedGoldDoor(DoorGoldActor goldDoorActor);

}