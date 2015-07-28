package com.derekentringer.gizmo.actor.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.WorldUtils;

public class PhantomActor extends BaseActor {

    private static final float MOVEMENT_FORCE = 0.1f;
    private static final int MOVEMENT_PADDING = 7;

    private TextureRegion[] phantomLeftSprite;
    private TextureRegion[] phantomRightSprite;
    private Texture phantomLeft;
    private Texture phantomRight;

    private Vector2 playerPosition = new Vector2();

    public PhantomActor(Body body) {
        super(body);

        phantomLeft = Gizmo.assetManager.get("res/images/phantom_left.png", Texture.class);
        phantomRight = Gizmo.assetManager.get("res/images/phantom_right.png", Texture.class);
        phantomLeftSprite = TextureRegion.split(phantomLeft, 32, 32)[0];
        phantomRightSprite = TextureRegion.split(phantomRight, 32, 32)[0];

        setAnimation(phantomLeftSprite, 1 / 12f);
        setFacingDirection(FACING_LEFT);
    }

    public Vector2 getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(float xPos) {
        playerPosition.x = xPos;
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

    @Override
    public void act(float delta) {
        if (getPosition().x > getPlayerPosition().x + WorldUtils.ppmCalc(MOVEMENT_PADDING)) {
            BodyUtils.applyLinearImpulseToBody(body, -MOVEMENT_FORCE, "x");
            setFacingDirection(FACING_LEFT);
        }
        else if (getPosition().x < getPlayerPosition().x - WorldUtils.ppmCalc(MOVEMENT_PADDING)) {
            BodyUtils.applyLinearImpulseToBody(body, MOVEMENT_FORCE, "x");
            setFacingDirection(FACING_RIGHT);
        }

        if (facingDirection == FACING_LEFT) {
            if (!getCurrentTextureRegion().equals(phantomLeftSprite)) {
                setAnimation(phantomLeftSprite, 1 / 12f);
            }
        }
        else {
            if (!getCurrentTextureRegion().equals(phantomRightSprite)) {
                setAnimation(phantomRightSprite, 1 / 12f);
            }
        }
    }

}