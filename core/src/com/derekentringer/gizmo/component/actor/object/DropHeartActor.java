package com.derekentringer.gizmo.component.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.util.BodyUtils;

public class DropHeartActor extends BaseActor {

    private static final String TAG = DropHeartActor.class.getSimpleName();

    private static final int MOVEMENT_FORCE = 4;
    private static final float PLAYER_DETECTION = 0.5f;

    private float heartRadiusFront = getPosition().x + PLAYER_DETECTION;
    private float heartRadiusBehind = getPosition().x - PLAYER_DETECTION;
    private float heartRadiusAbove = getPosition().y + PLAYER_DETECTION;
    private float heartRadiusBelow = getPosition().y - PLAYER_DETECTION;

    private TextureRegion[] mDropHeartSprite;
    private Texture mDropHeart;

    private Vector2 mPlayerPosition = new Vector2();

    private float mTimeAccumulated;

    private float speedY;
    private float speedX;
    
    public DropHeartActor(Body body) {
        super(body);

        mDropHeart = Gizmo.assetManager.get("res/image/drop/drop_heart.png", Texture.class);
        mDropHeartSprite = TextureRegion.split(mDropHeart, 8, 8)[0];

        setAnimation(mDropHeartSprite, 1 / 12f);

        speedX = MathUtils.random(-1, 1);
        speedY = MathUtils.random(0, 1);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        mTimeAccumulated += delta;
        speedY = speedY - (mTimeAccumulated / 1);

        if (getPlayerPosition().x < heartRadiusFront
                && getPlayerPosition().x > heartRadiusBehind
                && getPlayerPosition().y < heartRadiusAbove
                && getPlayerPosition().y > heartRadiusBelow) {
            mBody.setLinearVelocity((getPlayerPosition().x - getPosition().x) * MOVEMENT_FORCE, (getPlayerPosition().y - getPosition().y) * MOVEMENT_FORCE);
            return;
        }
        else {
            if (speedY <= 0) {
                speedY = -0.75f;
            }
            if (speedY > 0) {
                speedX = speedX - (mTimeAccumulated / 1);
            }
            else {
                speedX = 0;
            }
        }

        BodyUtils.applyLinearImpulseToBody(mBody, speedY, "y");
        BodyUtils.applyLinearImpulseToBody(mBody, speedX, "x");
    }

    public Vector2 getPlayerPosition() {
        return mPlayerPosition;
    }

    public void setPlayerPosition(Vector2 playerPosition) {
        mPlayerPosition.x = playerPosition.x;
        mPlayerPosition.y = playerPosition.y;
    }

}