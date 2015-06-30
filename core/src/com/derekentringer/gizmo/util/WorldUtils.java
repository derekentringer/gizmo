package com.derekentringer.gizmo.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.structure.GroundUserData;
import com.derekentringer.gizmo.util.constant.Constants;

public class WorldUtils {

    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static float ppmCalc(int dimension) {
        return dimension / Constants.PPM;
    }

    public static Body createStaticBody(ObjectData userData, World world, float tileSize, int row, int col, boolean isSensor) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(
                (col + 0.5f) * tileSize / Constants.PPM,
                (row + 0.5f) * tileSize / Constants.PPM
        );

        //don't have to use chainshape here...there are other options
        ChainShape chainShape = new ChainShape();
        Vector2[] v = new Vector2[5];
        v[0] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[1] = new Vector2(-tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[2] = new Vector2(tileSize / 2 / Constants.PPM, tileSize / 2 / Constants.PPM);
        v[3] = new Vector2(tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        v[4] = new Vector2(-tileSize / 2 / Constants.PPM, -tileSize / 2 / Constants.PPM);
        chainShape.createChain(v);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.shape = chainShape;
        fixtureDef.filter.categoryBits = 1;
        fixtureDef.filter.maskBits = -1;
        fixtureDef.isSensor = isSensor;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(new GroundUserData());
        body.setUserData(userData);

        chainShape.dispose();
        return body;
    }

}