package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomLargeModel extends BaseEnemyModel {

    public static final String PHANTOM_LARGE = "PHANTOM_LARGE";

    private static final int DEFAULT_HEALTH = 20;
    private static final int DEFAULT_HEALTH_DAMAGE = 15;
    private static final boolean IS_BOSS = true;

    public PhantomLargeModel() {

    }

    public PhantomLargeModel(boolean dropsLoot, String bossType) {
        mBaseModelType = BaseModelType.ENEMY;
        mHealth = DEFAULT_HEALTH;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
        mIsBoss = IS_BOSS;
        mDoesLootDrop = dropsLoot;
        mBossType = bossType;
    }

}