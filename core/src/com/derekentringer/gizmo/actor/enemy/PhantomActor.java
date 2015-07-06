package com.derekentringer.gizmo.actor.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.util.BodyUtils;

public class PhantomActor extends BaseActor implements ContactListener {

    private static final float RUNNING_FORCE = 0.1f;

    private TextureRegion[] phantomLeftSprite;
    private Texture phantomLeft;

    public PhantomActor(Body body) {
        super(body);

        phantomLeft = Gizmo.assetManager.get("res/images/phantom_left.png", Texture.class);
        phantomLeftSprite = TextureRegion.split(phantomLeft, 32, 32)[0];

        setAnimation(phantomLeftSprite, 1/12f);
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

    @Override
    public void act (float delta) {
        BodyUtils.applyLinearImpulseToBody(body, -RUNNING_FORCE, "x");
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}