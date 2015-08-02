package com.derekentringer.gizmo.actor.data.object;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class HeartData extends ObjectData {

    public static final String HEART = "heart";

    public HeartData() {
        super();
        objectDataType = ObjectDataType.HEART;
    }

}