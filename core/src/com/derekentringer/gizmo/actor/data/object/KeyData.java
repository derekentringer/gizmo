package com.derekentringer.gizmo.actor.data.object;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class KeyData extends ObjectData {

    public static final String KEY = "key";

    public KeyData() {
        super();
        objectDataType = ObjectDataType.KEY;
    }

}