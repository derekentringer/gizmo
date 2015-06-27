package com.derekentringer.gizmo.actor.data.structure;

import com.derekentringer.gizmo.actor.data.UserData;
import com.derekentringer.gizmo.actor.data.UserDataType;

public class WallUserData extends UserData {

    public WallUserData() {
        super();
        userDataType = UserDataType.GROUND;
    }

}