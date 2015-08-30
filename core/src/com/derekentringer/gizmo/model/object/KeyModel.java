package com.derekentringer.gizmo.model.object;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class KeyModel extends BaseModel {

    public static final String KEY = "KEY";

    public static final String KEY_GOLD = "GOLD";
    public static final String KEY_BRONZE = "BRONZE";
    public static final String KEY_BLOOD = "BLOOD";
    public static final String KEY_BLACK = "BLACK";

    private String mKeyType;

    public KeyModel() {
        super();
        mBaseModelType = BaseModelType.KEY;
    }

    public KeyModel(String keyType) {
        super();
        mBaseModelType = BaseModelType.KEY;
        mKeyType = keyType;
    }

    public String getKeyType() {
        return mKeyType;
    }

}