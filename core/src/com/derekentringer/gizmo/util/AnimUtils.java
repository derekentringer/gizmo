package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.component.actor.structure.destroyable.BlockBreakActor;
import com.derekentringer.gizmo.component.actor.pickup.PickupHeartActor;
import com.derekentringer.gizmo.component.actor.pickup.PickupKeyActor;
import com.derekentringer.gizmo.component.actor.pickup.PickupLifeActor;
import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.model.block.BlockBreakModel;
import com.derekentringer.gizmo.model.pickup.PickupHeartModel;
import com.derekentringer.gizmo.model.pickup.PickupKeyModel;
import com.derekentringer.gizmo.model.pickup.PickupLifeModel;
import com.derekentringer.gizmo.util.log.GLog;
import com.derekentringer.gizmo.util.map.MapParser;

public class AnimUtils {

    private static final String TAG = AnimUtils.class.getSimpleName();

    public static void breakBlock(Body body, GameStage gameStage, MapParser mapParser, World world) {
        GLog.d(TAG, "break block animation");
        BlockBreakActor blockBreakActor = new BlockBreakActor(ObjectUtils.createBlockBreak(new BlockBreakModel(), world, new Vector2(body.getPosition().x, body.getPosition().y)));
        blockBreakActor.setName(BlockBreakModel.BREAK);
        gameStage.addActor(blockBreakActor);
        mapParser.addToTempActorsArray(blockBreakActor);
    }

    public static void pickupHeart(Vector2 vector, GameStage gameStage, MapParser mapParser, World world) {
        GLog.d(TAG, "pickup heart animation");
        PickupHeartActor pickupHeartActor = new PickupHeartActor(ObjectUtils.createPickupHeart(new PickupHeartModel(), world, vector));
        pickupHeartActor.setName(PickupHeartModel.PICKUP_HEART);
        gameStage.addActor(pickupHeartActor);
        mapParser.addToTempActorsArray(pickupHeartActor);
    }

    public static void pickupLife(Vector2 vector, GameStage gameStage, MapParser mapParser, World world) {
        GLog.d(TAG, "pickup life animation");
        PickupLifeActor pickupLifeActor = new PickupLifeActor(ObjectUtils.createPickupLife(new PickupLifeModel(), world, vector));
        pickupLifeActor.setName(PickupLifeModel.PICKUP_LIFE);
        gameStage.addActor(pickupLifeActor);
        mapParser.addToTempActorsArray(pickupLifeActor);
    }

    public static void pickupKey(Vector2 vector, GameStage gameStage, MapParser mapParser, World world) {
        GLog.d(TAG, "pickup key animation");
        PickupKeyActor pickupKeyActor = new PickupKeyActor(ObjectUtils.createPickupKey(new PickupKeyModel(), world, vector));
        pickupKeyActor.setName(PickupKeyModel.PICKUP_KEY);
        gameStage.addActor(pickupKeyActor);
        mapParser.addToTempActorsArray(pickupKeyActor);
    }

    public static void poof(Vector2 vector, GameStage gameStage, MapParser mapParser, World world) {
        GLog.d(TAG, "poof animation");
    }

}