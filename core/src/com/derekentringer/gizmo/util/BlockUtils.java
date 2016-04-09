package com.derekentringer.gizmo.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.model.structure.destroyable.BaseDestroyableModel;

public class BlockUtils {

    private static final String TAG = BlockUtils.class.getSimpleName();

    public static int getBlockHealth(Body body) {
        BaseDestroyableModel baseDestroyableModel = (BaseDestroyableModel) body.getUserData();
        if (baseDestroyableModel != null) {
            return baseDestroyableModel.getHealth();
        }
        else {
            return 0;
        }
    }

    //TODO random crash
    public static void setBlockHealth(Body body, int health) {
        //if (body.getUserData() instanceof BaseDestroyableModel) {
            BaseDestroyableModel baseDestroyableModel = (BaseDestroyableModel) body.getUserData();
            if (baseDestroyableModel != null) {
                int newHealth = baseDestroyableModel.getHealth() - health;
                baseDestroyableModel.setHealth(newHealth);
            }
        //}
    }

    public static boolean getBlockDropsLoot(Body body) {
        BaseDestroyableModel baseDestroyableModel = (BaseDestroyableModel) body.getUserData();
        if (baseDestroyableModel != null) {
            return baseDestroyableModel.getDoesLootDrop();
        }
        else {
            return false;
        }
    }

}