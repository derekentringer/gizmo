package com.derekentringer.gizmo.actor.data.player;

import com.derekentringer.gizmo.actor.data.UserData;
import com.derekentringer.gizmo.actor.data.UserDataType;

public class PlayerUserData extends UserData {

    public PlayerUserData() {
        super();
        userDataType = UserDataType.PLAYER;
    }

}