package com.derekentringer.gizmo.actor.data.enemy;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class PhantomData extends ObjectData {

    public static final String PHANTOM = "phantoms";

    public PhantomData() {
        super();
        objectDataType = ObjectDataType.PHANTOM;
    }

}