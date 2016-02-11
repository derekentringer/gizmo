package com.derekentringer.gizmo.model.block;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class BlockBreakModel extends BaseModel {

    public static final String BREAK = "BREAK";

    public BlockBreakModel() {
        mBaseModelType = BaseModelType.BREAK;
    }

}