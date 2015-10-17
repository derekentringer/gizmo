package com.derekentringer.gizmo.component.actor.structure.destroyable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.model.structure.destroyable.interfaces.IDestroyable;
import com.derekentringer.gizmo.util.BodyUtils;

import java.util.ArrayList;

public class DestroyableBlockFallActor extends BaseDestroyableBlockActor {

    private static final String TAG = DestroyableBlockFallActor.class.getSimpleName();

    private static final float MOVEMENT_FORCE = 1.0f;
    private static final double DETECTION_RADIUS = 0.25;

    private ArrayList<IDestroyable> listeners = new ArrayList<IDestroyable>();

    private TextureRegion[] mDestroyableBlockSprite;
    private Texture mDestroyableBlock;

    private boolean mPlayerCloseEnough;

    public DestroyableBlockFallActor(Body body) {
        super(body);

        mDestroyableBlock = Gizmo.assetManager.get("res/image/tile/destroyable_block_fall.png", Texture.class);
        mDestroyableBlockSprite = TextureRegion.split(mDestroyableBlock, 32, 32)[0];

        setIsPlayingAnimation(false);
        setAnimation(mDestroyableBlockSprite, 1 / 12f);
    }

    public void addListener(IDestroyable listener) {
        listeners.add(listener);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getPlayerPosition().x >= getPosition().x - DETECTION_RADIUS
                && getPlayerPosition().x <= getPosition().x + DETECTION_RADIUS
                && getPlayerPosition().y >= getPosition().y - DETECTION_RADIUS
                && getPlayerPosition().y <= getPosition().y + DETECTION_RADIUS) {

            setPlayerCloseEnough(true);
            mBody.setActive(true);
            BodyUtils.applyLinearImpulseToBody(mBody, -MOVEMENT_FORCE, "y");

            for (IDestroyable listener : listeners) {
                listener.removeBlockFromMap(this);
            }
        }
        else {
            if (!getPlayerCloseEnough()) {
                mBody.setActive(false);
            }
        }
    }

    private boolean getPlayerCloseEnough() {
        return mPlayerCloseEnough;
    }

    private void setPlayerCloseEnough(boolean closeEnough) {
        mPlayerCloseEnough = closeEnough;
    }

}