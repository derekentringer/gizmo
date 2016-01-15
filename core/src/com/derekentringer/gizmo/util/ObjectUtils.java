package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.misc.BlockBreakModel;
import com.derekentringer.gizmo.model.misc.PickupHeartModel;
import com.derekentringer.gizmo.model.misc.PickupLifeModel;
import com.derekentringer.gizmo.model.misc.WhiteDotModel;
import com.derekentringer.gizmo.model.object.BoomerangModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.LifeModel;
import com.derekentringer.gizmo.settings.Constants;

public class ObjectUtils {

    private static final String TAG = ObjectUtils.class.getSimpleName();

    public static Body createHeart(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(coordinates.x), WorldUtils.ppmCalc(coordinates.y));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new HeartModel());

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    public static Body createLife(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(coordinates.x), WorldUtils.ppmCalc(coordinates.y));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new LifeModel());

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    public static Body createKey(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(coordinates.x), WorldUtils.ppmCalc(coordinates.y));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new KeyModel());

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    public static Body createBoomerang(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(coordinates.x), WorldUtils.ppmCalc(coordinates.y));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new BoomerangModel());

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    public static Body createDoor(BaseModel userData, World world, int posX, int posY) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(posX), WorldUtils.ppmCalc(posY));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(userData);

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    public static Body createWhiteDot(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(WorldUtils.ppmCalc(coordinates.x), WorldUtils.ppmCalc(coordinates.y));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new WhiteDotModel());

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    public static Body createBlockBreak(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(coordinates.x, coordinates.y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        fixtureDef.filter.categoryBits = Constants.IGNORE_ENTITY;
        fixtureDef.filter.groupIndex = -1;

        body.createFixture(fixtureDef).setUserData(new BlockBreakModel());

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    public static Body createPickupHeart(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(coordinates.x, coordinates.y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        fixtureDef.filter.categoryBits = Constants.IGNORE_ENTITY;
        fixtureDef.filter.groupIndex = -1;

        body.createFixture(fixtureDef).setUserData(new PickupHeartModel());

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

    public static Body createPickupLife(BaseModel userData, World world, Vector2 coordinates) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.position.set(coordinates.x, coordinates.y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        shape.setAsBox(WorldUtils.ppmCalc(14), WorldUtils.ppmCalc(14));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        fixtureDef.filter.categoryBits = Constants.IGNORE_ENTITY;
        fixtureDef.filter.groupIndex = -1;

        body.createFixture(fixtureDef).setUserData(new PickupLifeModel());

        body.setUserData(userData);

        shape.dispose();

        return body;
    }

}