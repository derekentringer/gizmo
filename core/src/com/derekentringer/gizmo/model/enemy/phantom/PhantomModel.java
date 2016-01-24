package com.derekentringer.gizmo.model.enemy.phantom;

import com.derekentringer.gizmo.model.BaseModelType;
import com.derekentringer.gizmo.model.enemy.BaseEnemyModel;

public class PhantomModel extends BaseEnemyModel {

    public static final String PHANTOM = "PHANTOM";

    private static final int DEFAULT_HEALTH = 4;
    private static final int DEFAULT_HEALTH_DAMAGE = 5;

    public PhantomModel(boolean dropsLoot) {
        mHealth = DEFAULT_HEALTH;
        mHealthDamage = DEFAULT_HEALTH_DAMAGE;
        mBaseModelType = BaseModelType.ENEMY;
        mDoesLootDrop = dropsLoot;
    }

}