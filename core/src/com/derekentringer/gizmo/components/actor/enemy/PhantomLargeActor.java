package com.derekentringer.gizmo.components.actor.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.enemy.PhantomLargeModel;

public class PhantomLargeActor extends BaseActor {

    private static final String TAG = PhantomLargeActor.class.getSimpleName();

    private PhantomLargeModel mPhantomLargeModel = new PhantomLargeModel();

    private TextureRegion[] mPhantomSprite;
    private Texture mPhantomLarge;

    public PhantomLargeActor(Body body) {
        super(body);
        mPhantomLarge = Gizmo.assetManager.get("res/images/enemies/phantom/phantom_large.png", Texture.class);
        mPhantomSprite = TextureRegion.split(mPhantomLarge, 320, 320)[0];
        setAnimation(mPhantomSprite, 1 / 12f);
    }

    @Override
    public BaseModel getPlayerModel() {
        return mPhantomLargeModel;
    }

}