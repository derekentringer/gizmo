package com.derekentringer.gizmo.model.enemy.boss.phantom;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.enemy.BaseEnemyModel;

public class PhantomLargeModel extends BaseEnemyModel {

    public static final String PHANTOM_LARGE = "PHANTOM_LARGE";

    private static final int DEFAULT_HEALTH = 100;
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