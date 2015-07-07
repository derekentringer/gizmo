package com.derekentringer.gizmo.actor.data.player;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class PlayerData extends ObjectData {

    public static final String PLAYER = "player";

    public PlayerData() {
        super();
        objectDataType = ObjectDataType.PLAYER;
    }

}