package com.derekentringer.gizmo.actor.data.object;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class KeyData extends ObjectData {

    public static final String KEY = "key";

    public static final String KEY_GOLD = "gold";
    public static final String KEY_BRONZE = "bronze";
    public static final String KEY_BLOOD = "blood";
    public static final String KEY_BLACK = "black";

    private String sKeyType;

    public KeyData() {
        super();
        objectDataType = ObjectDataType.KEY;
    }

    public KeyData(String keyType) {
        super();
        objectDataType = ObjectDataType.KEY;
        sKeyType = keyType;
    }

    public String getKeyType() {
        return sKeyType;
    }

}