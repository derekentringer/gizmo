package com.derekentringer.gizmo.component.actor.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.IBaseActor;
import com.derekentringer.gizmo.component.actor.item.interfaces.IItems;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class BombExplodeActor extends BaseActor implements IBaseActor {

    private static final String TAG = BombExplodeActor.class.getSimpleName();

    protected ArrayList<IItems> listeners = new ArrayList<IItems>();

    protected TextureRegion[] mBombExplodeSprite;
    protected Texture mBombExplodeTexture;

    public void addListener(IItems listener) {
        listeners.add(listener);
    }

    public BombExplodeActor(Body body) {
        super(body);
        mBombExplodeTexture = Gizmo.getAssetManager().get("res/image/item/bomb.png", Texture.class);
        mBombExplodeSprite = TextureRegion.split(mBombExplodeTexture, 32, 32)[0];
        setAnimation(mBombExplodeSprite, 1 / 12f);
    }

    @Override
    public void isAnimationFinished(boolean isFinished) {
        for (IItems listener : listeners) {
            GLog.d(TAG, "removeBombExplosion");
            listener.removePlayerItemFromStage(this);
        }
    }

}