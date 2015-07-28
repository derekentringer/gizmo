package com.derekentringer.gizmo.actor.data.enemy;

import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.ObjectDataType;

public class PhantomLargeData extends ObjectData {

    public static final String PHANTOM_LARGE = "phantomlarge";
    public int healthDamage = 10;

    public PhantomLargeData() {
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