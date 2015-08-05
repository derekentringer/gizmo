package com.derekentringer.gizmo.model.object;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class KeyModel extends BaseModel {

    public static final String KEY = "key";

    public static final String KEY_GOLD = "gold";
    public static final String KEY_BRONZE = "bronze";
    public static final String KEY_BLOOD = "blood";
    public static final String KEY_BLACK = "black";

    private String sKeyType;

    public KeyModel() {
        super();
        baseModelType = BaseModelType.KEY;
    }

    public KeyModel(String keyType) {
        super();
        baseModelType = BaseModelType.KEY;
        sKeyType = keyType;
    }

    public String getKeyType() {
        return sKeyType;
    }

}