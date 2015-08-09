package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomLargeModel extends EnemyModel {

    public static final String PHANTOM_LARGE = "phantomlarge";
    private int healthDamage = 10;

    public PhantomLargeModel() {
        super();
        baseModelType = BaseModelType.ENEMY;
    }

    @Override
    public int getHealthDamage() {
        return healthDamage;
    }

    @Override
    public void setHealthDamage(int damage) {
        healthDamage = damage;
    }

}