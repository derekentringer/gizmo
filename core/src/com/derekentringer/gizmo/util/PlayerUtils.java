package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.model.player.PlayerFootSensorModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.settings.Constants;

public class PlayerUtils {

    private static final String TAG = PlayerUtils.class.getSimpleName();

    public static Body createPlayer(World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // create player
        bodyDef.position.set(WorldUtils.ppmCalc(coordinates.x), WorldUtils.ppmCalc(coordinates.y));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(8), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        body.createFixture(fixtureDef).setUserData(new PlayerModel());

        // create foot sensor
        Vector2 center = new Vector2(0, WorldUtils.ppmCalc(-16));
        shape.setAsBox(WorldUtils.ppmCalc(7), WorldUtils.ppmCalc(1), center, 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        fixtureDef.filter.categoryBits = Constants.PLAYER_ENTITY;

        body.createFixture(fixtureDef).setUserData(new PlayerFootSensorModel());

        body.setUserData(new PlayerModel());

        shape.dispose();
        return body;
    }

}