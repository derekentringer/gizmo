package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.item.BaseItemModel;
import com.derekentringer.gizmo.settings.Constants;

public class ItemUtils {

    private static final String TAG = ItemUtils.class.getSimpleName();

    public static int getItemHealthDamage(Body body) {
        BaseItemModel itemModel = (BaseItemModel) body.getUserData();
        return itemModel.getHealthDamage();
    }

    public static Body createBoomerang(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(coordinates.x, coordinates.y);
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;

        fixtureDef.filter.categoryBits = Constants.PLAYER_ATTACK_ENTITY;
        fixtureDef.filter.maskBits = Constants.ENEMY_ENTITY;

        body.createFixture(fixtureDef).setUserData(new HeartModel());

        body.setUserData(userData);

        shape.dispose();
        return body;
    }

}