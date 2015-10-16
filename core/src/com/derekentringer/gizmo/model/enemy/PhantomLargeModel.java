package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomLargeModel extends BaseEnemyModel {

    public static final String PHANTOM_LARGE = "PHANTOM_LARGE";

    private static final int DEFAULT_HEALTH = 4;
    private static final int DEFAULT_HEALTH_DAMAGE = 10;
    private static final boolean IS_BOSS = true;

    public PhantomLargeModel(boolean dropsLoot) {
        mBaseModelType = BaseModelType.ENEMY;
        mHealth = DEFAULT_HEALTH;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
        mIsBoss = IS_BOSS;
        mDoesLootDrop = dropsLoot;
    }

}