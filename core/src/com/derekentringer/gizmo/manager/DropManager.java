package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.components.actor.object.DropHeartActor;
import com.derekentringer.gizmo.manager.interfaces.IDropManager;
import com.derekentringer.gizmo.model.object.DropHeartModel;
import com.derekentringer.gizmo.util.DropUtils;

import java.util.ArrayList;

public class DropManager extends Stage {

    private static final String TAG = DropManager.class.getSimpleName();

    private ArrayList<IDropManager> listeners = new ArrayList<IDropManager>();

    public void addListener(IDropManager listener) {
        listeners.add(listener);
    }

    public void addDrop(World world, Vector2 coordinates) {

        //determine what we are going to drop
        //create the items

        DropHeartActor dropHeartActor = new DropHeartActor(DropUtils.createDropHeart(new DropHeartModel(), world, coordinates));
        dropHeartActor.setName(DropHeartModel.HEART_SMALL);
        for (IDropManager listener : listeners) {
            listener.addDroppedItem(dropHeartActor);
        }

        //DropUtils.animateDrop();
    }

}