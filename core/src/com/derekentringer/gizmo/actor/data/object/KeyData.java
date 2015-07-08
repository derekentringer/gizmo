package com.derekentringer.gizmo.actor.data.object;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class KeyData extends ObjectData {

    public static final String KEY = "key";
    private Float sKeyKey;
    private String sKeyType;

    public KeyData(Float key, String keyType) {
        super();
        objectDataType = ObjectDataType.KEY;
        sKeyKey = key;
        sKeyType = keyType;
    }

    public Float getKeyKey() {
        return sKeyKey;
    }

    public String getKeyType() {
        return sKeyType;
    }

}