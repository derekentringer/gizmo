package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.component.actor.object.DropHeartActor;
import com.derekentringer.gizmo.manager.interfaces.IDropManager;
import com.derekentringer.gizmo.model.object.DropHeartModel;
import com.derekentringer.gizmo.util.DropUtils;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class DropManager extends Stage {

    private static final String TAG = DropManager.class.getSimpleName();

    private static final int MIN_DROPS = 0;
    private static final int MAX_DROPS = 5;

    private ArrayList<IDropManager> listeners = new ArrayList<IDropManager>();

    public void addListener(IDropManager listener) {
        listeners.add(listener);
    }

    public void addDropHeart(World world, Vector2 coordinates) {
        int drops = MathUtils.random(MIN_DROPS, MAX_DROPS);
        for (int i = 0; i <= drops; i++) {
            GLog.d(TAG, "addDropHeart: " + coordinates.x + ", " + coordinates.y);
            DropHeartActor dropHeartActor = new DropHeartActor(DropUtils.createDropHeart(new DropHeartModel(), world, coordinates));
            dropHeartActor.setName(DropHeartModel.HEART_SMALL);
            for (IDropManager listener : listeners) {
                listener.addDroppedItem(dropHeartActor);
            }
        }
    }

}