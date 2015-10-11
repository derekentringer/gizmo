package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.model.player.PlayerSensorBottom;
import com.derekentringer.gizmo.model.player.PlayerSensorLeft;
import com.derekentringer.gizmo.model.player.PlayerSensorRight;
import com.derekentringer.gizmo.model.player.PlayerSensorTop;
import com.derekentringer.gizmo.settings.Constants;

public class PlayerUtils {

    private static final String TAG = PlayerUtils.class.getSimpleName();

    public static Body createPlayer(World world, Vector2 coordinates) {

        BodyDef bodyDef = new BodyDef();

        FixtureDef playerFixtureDef = new FixtureDef();
        FixtureDef topFixtureDef = new FixtureDef();
        FixtureDef rightFixtureDef = new FixtureDef();
        FixtureDef bottomFixtureDef = new FixtureDef();
        FixtureDef leftFixtureDef = new FixtureDef();

        PolygonShape shapePlayer = new PolygonShape();
        PolygonShape shapeTop = new PolygonShape();
        PolygonShape shapeRight = new PolygonShape();
        PolygonShape shapeBottom = new PolygonShape();
        PolygonShape shapeLeft = new PolygonShape();

        // create player
        bodyDef.position.set(WorldUtils.ppmCalc(coordinates.x), WorldUtils.ppmCalc(coordinates.y));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        shapePlayer.setAsBox(WorldUtils.ppmCalc(8), WorldUtils.ppmCalc(14));
        playerFixtureDef.shape = shapePlayer;
        playerFixtureDef.isSensor = false;
        body.createFixture(playerFixtureDef).setUserData(new PlayerModel());

        // create top sensor
        Vector2 centerTop = new Vector2(0, WorldUtils.ppmCalc(16));
        shapeTop.setAsBox(WorldUtils.ppmCalc(7), WorldUtils.ppmCalc(1), centerTop, 0);
        topFixtureDef.shape = shapeTop;
        topFixtureDef.isSensor = true;
        body.createFixture(topFixtureDef).setUserData(new PlayerSensorTop());

        // create right sensor
        Vector2 centerRight = new Vector2(WorldUtils.ppmCalc(10), WorldUtils.ppmCalc(0));
        shapeRight.setAsBox(WorldUtils.ppmCalc(1), WorldUtils.ppmCalc(12), centerRight, 0);
        rightFixtureDef.shape = shapeRight;
        rightFixtureDef.isSensor = true;
        body.createFixture(rightFixtureDef).setUserData(new PlayerSensorRight());

        // create bottom sensor
        Vector2 centerBottom = new Vector2(0, WorldUtils.ppmCalc(-16));
        shapeBottom.setAsBox(WorldUtils.ppmCalc(7), WorldUtils.ppmCalc(1), centerBottom, 0);
        bottomFixtureDef.shape = shapeBottom;
        bottomFixtureDef.isSensor = true;
        body.createFixture(bottomFixtureDef).setUserData(new PlayerSensorBottom());

        // create left sensor
        Vector2 centerLeft = new Vector2(WorldUtils.ppmCalc(-10), WorldUtils.ppmCalc(0));
        shapeLeft.setAsBox(WorldUtils.ppmCalc(1), WorldUtils.ppmCalc(12), centerLeft, 0);
        leftFixtureDef.shape = shapeLeft;
        leftFixtureDef.isSensor = true;
        body.createFixture(leftFixtureDef).setUserData(new PlayerSensorLeft());

        playerFixtureDef.filter.categoryBits = Constants.PLAYER_ENTITY;

        body.setUserData(new PlayerModel());

        shapePlayer.dispose();
        shapeTop.dispose();
        shapeRight.dispose();
        shapeBottom.dispose();
        shapeLeft.dispose();

        return body;
    }

}