package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.components.actor.object.DropHeartActor;
import com.derekentringer.gizmo.components.stage.GameStage;
import com.derekentringer.gizmo.model.object.DropHeartModel;
import com.derekentringer.gizmo.util.DropUtils;
import com.derekentringer.gizmo.util.map.MapParser;

public class DropManager extends Stage {

    private static final String TAG = DropManager.class.getSimpleName();

    public void calculateDroppedItems(World world, GameStage gameStage, MapParser mapParser, boolean dropsLoot, Vector2 coordinates) {
        if (dropsLoot) {

            //determine what we are going to drop
            //create the items

            DropHeartActor dropHeartActor = new DropHeartActor(DropUtils.createDropHeart(new DropHeartModel(), world, coordinates));
            dropHeartActor.setName(DropHeartModel.HEART_SMALL);
            addActor(dropHeartActor);
            mapParser.addToTempActorsArray(dropHeartActor);

            //DropUtils.animateDrop();
        }
    }

}