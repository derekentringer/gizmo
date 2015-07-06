package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.actor.data.enemy.PhantomData;
import com.derekentringer.gizmo.actor.data.player.PlayerUserData;
import com.derekentringer.gizmo.actor.data.player.PlayerUserHitAreaData;

public class PlayerUtils {

    public static Body createPlayer(World world, int posX, int posY) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // create player
        bodyDef.position.set(WorldUtils.ppmCalc(posX), WorldUtils.ppmCalc(posY));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        body.createFixture(fixtureDef).setUserData(new PlayerUserData());

        // create foot sensor
        Vector2 center = new Vector2(0, WorldUtils.ppmCalc(-16));
        shape.setAsBox(WorldUtils.ppmCalc(10), WorldUtils.ppmCalc(1), center, 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new PlayerUserHitAreaData());

        body.setUserData(new PlayerUserData());

        shape.dispose();
        return body;
    }

    //TODO move this to the tilemapmanager
    public static Body createPhantom(World world, int posX, int posY) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(posX), WorldUtils.ppmCalc(posY));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        body.createFixture(fixtureDef).setUserData(new PhantomData());

        body.setUserData(new PhantomData());

        shape.dispose();
        return body;
    }

}