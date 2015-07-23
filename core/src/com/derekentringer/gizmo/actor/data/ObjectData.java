package com.derekentringer.gizmo.actor.data;

public class ObjectData {

    protected ObjectDataType objectDataType;

    public int healthDamage;
    public String keyType;

    public ObjectData() {
    }

    public ObjectDataType getObjectDataType() {
        return objectDataType;
    }

    public int getHealthDamage() {
        return healthDamage;
    }

    public void setHealthDamage(int damage) {
        healthDamage = damage;
    }

    public String getKeyType() {
        return keyType;
    }

}