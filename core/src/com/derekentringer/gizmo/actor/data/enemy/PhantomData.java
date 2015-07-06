package com.derekentringer.gizmo.actor.data.enemy;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class PhantomData extends ObjectData {

    public static final String PHANTOM = "phantom";
    public int healthDamage = 5;

    public PhantomData() {
        super();
        objectDataType = ObjectDataType.ENEMY;
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