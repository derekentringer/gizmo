package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.component.actor.object.DropCrystalBlueActor;
import com.derekentringer.gizmo.component.actor.object.DropHeartActor;
import com.derekentringer.gizmo.manager.interfaces.IDropManager;
import com.derekentringer.gizmo.model.object.DropCrystalBlueModel;
import com.derekentringer.gizmo.model.object.DropHeartModel;
import com.derekentringer.gizmo.util.DropUtils;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class DropManager extends Stage {

    private static final String TAG = DropManager.class.getSimpleName();

    //25% chance for a drop
    //plus chance for zero drops
    private static final int DROP_TYPE_HEART = 0;
    private static final int DROP_TYPE_CRYSTAL_BLUE = 3;

    private static final int MIN_DROPS = 0;
    private static final int MAX_DROPS = 5;

    private static final int MIN_BOSS_DROPS = 50;
    private static final int MAX_BOSS_DROPS = 100;

    private ArrayList<IDropManager> listeners = new ArrayList<IDropManager>();

    private int whichDrop;

    public void addListener(IDropManager listener) {
        listeners.add(listener);
    }

    public void randomDrop(World world, Vector2 coordinates) {
        whichDrop = MathUtils.random(DROP_TYPE_HEART, DROP_TYPE_CRYSTAL_BLUE);
        if (whichDrop == DROP_TYPE_HEART) {
            GLog.d(TAG, "addDropHeart: " + coordinates.x + ", " + coordinates.y);
            addDropHeart(world, coordinates);
        }
        else if (whichDrop == DROP_TYPE_CRYSTAL_BLUE) {
            GLog.d(TAG, "addDropCrystalBlue: " + coordinates.x + ", " + coordinates.y);
            addDropCrystalBlue(world, coordinates);
        }
    }

    public void addDropHeart(World world, Vector2 coordinates) {
        int drops = MathUtils.random(MIN_DROPS, MAX_DROPS);
        for (int i = 0; i <= drops; i++) {
            DropHeartActor dropHeartActor = new DropHeartActor(DropUtils.createDropHeart(new DropHeartModel(), world, coordinates));
            dropHeartActor.setName(DropHeartModel.HEART_SMALL);
            for (IDropManager listener : listeners) {
                listener.addDroppedItem(dropHeartActor);
            }
        }
    }

    public void addDropCrystalBlue(World world, Vector2 coordinates) {
        int drops = MathUtils.random(MIN_DROPS, MAX_DROPS);
        for (int i = 0; i <= drops; i++) {
            DropCrystalBlueActor dropCrystalBlueActor = new DropCrystalBlueActor(DropUtils.createDropHeart(new DropCrystalBlueModel(), world, coordinates), false);
            dropCrystalBlueActor.setName(DropCrystalBlueModel.CRYSTAL_BLUE);
            for (IDropManager listener : listeners) {
                listener.addDroppedItem(dropCrystalBlueActor);
            }
        }
    }

    public void bossDropCrystals(World world, Vector2 coordinates) {
        int drops = MathUtils.random(MIN_BOSS_DROPS, MAX_BOSS_DROPS);
        for (int i = 0; i <= drops; i++) {
            DropCrystalBlueActor dropCrystalBlueActor = new DropCrystalBlueActor(DropUtils.createDropHeart(new DropCrystalBlueModel(), world, coordinates), true);
            dropCrystalBlueActor.setName(DropCrystalBlueModel.CRYSTAL_BLUE);
            for (IDropManager listener : listeners) {
                listener.addDroppedItem(dropCrystalBlueActor);
            }
        }
    }

}