package com.derekentringer.gizmo.model.enemy;

import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.BaseModelType;

public class PhantomModel extends BaseModel {

    public static final String PHANTOM = "phantom";
    public int healthDamage = 5;

    public PhantomModel() {
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