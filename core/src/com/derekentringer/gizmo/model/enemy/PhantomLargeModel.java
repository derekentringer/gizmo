package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomLargeModel extends BaseEnemyModel {

    public static final String PHANTOM_LARGE = "PHANTOM_LARGE";

    public PhantomLargeModel(boolean dropsLoot) {
        super();
        mBaseModelType = BaseModelType.ENEMY;
        mHealth = 4;
        mHealthDamage = 10;
        mDoesLootDrop = dropsLoot;
        mIsBoss = true;
    }

}