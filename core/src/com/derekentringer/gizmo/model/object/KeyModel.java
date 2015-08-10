package com.derekentringer.gizmo.model.object;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class KeyModel extends BaseModel {

    public static final String KEY = "key";

    public static final String KEY_GOLD = "gold";
    public static final String KEY_BRONZE = "bronze";
    public static final String KEY_BLOOD = "blood";
    public static final String KEY_BLACK = "black";

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