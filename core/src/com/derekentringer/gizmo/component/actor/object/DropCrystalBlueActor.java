package com.derekentringer.gizmo.component.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.util.BodyUtils;

public class DropCrystalBlueActor extends BaseActor {

    private static final String TAG = DropCrystalBlueActor.class.getSimpleName();

    private static final int MOVEMENT_FORCE = 4;
    private static final float PLAYER_DETECTION = 0.5f;

    private float heartRadiusFront;
    private float heartRadiusBehind;
    private float heartRadiusAbove;
    private float heartRadiusBelow;

    private TextureRegion[] mDropHeartSprite;
    private Texture mDropHeart;

    private Vector2 mPlayerPosition = new Vector2();

    private float mTimeAccumulated;

    private float speedY;
    private float speedX;
    private float speedYOne;
    private float speedXOne;
    private float speedYTwo;
    private float speedXTwo;

    public DropCrystalBlueActor(Body body, boolean isBoss) {
        super(body);

        mDropHeart = Gizmo.getAssetManager().get("res/image/drop/drop_crystal_blue.png", Texture.class);
        mDropHeartSprite = TextureRegion.split(mDropHeart, 8, 8)[0];

        setAnimation(mDropHeartSprite, 1 / 12f);

        if (isBoss) {
            speedXOne = MathUtils.random(-6, 6);
            speedXTwo = MathUtils.random(-3, 3);

            speedYOne = MathUtils.random(-5, 5);
            speedYTwo = MathUtils.random(-2, 2);

            speedX = MathUtils.random(speedXOne, speedXTwo);
            speedY = MathUtils.random(speedYOne, speedYTwo);
        }
        else {
            speedXOne = MathUtils.random(-1, 1);
            speedXTwo = MathUtils.random(-2, 2);

            speedYOne = MathUtils.random(0, 1);
            speedYTwo = MathUtils.random(0, 2);

            speedX = MathUtils.random(speedXOne, speedXTwo);
            speedY = MathUtils.random(speedYOne, speedYTwo);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        heartRadiusFront = getPosition().x + PLAYER_DETECTION;
        heartRadiusBehind = getPosition().x - PLAYER_DETECTION;
        heartRadiusAbove = getPosition().y + PLAYER_DETECTION;
        heartRadiusBelow = getPosition().y - PLAYER_DETECTION;

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
                speedY = -1f;
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