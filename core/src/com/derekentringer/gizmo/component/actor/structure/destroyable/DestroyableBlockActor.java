package com.derekentringer.gizmo.component.actor.structure.destroyable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DestroyableBlockActor extends BaseActor {

    private static final String TAG = DestroyableBlockActor.class.getSimpleName();

    private TextureRegion[] mDestroyableBlockSprite;
    private Texture mDestroyableBlock;

    public DestroyableBlockActor(Body body) {
        super(body);

        mDestroyableBlock = Gizmo.assetManager.get("res/images/tiles/destroyable_block_dirt.png", Texture.class);
        mDestroyableBlockSprite = TextureRegion.split(mDestroyableBlock, 32, 32)[0];

        setIsPlayingAnimation(false);
        setAnimation(mDestroyableBlockSprite, 1 / 12f);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBaseModel;
    }

}