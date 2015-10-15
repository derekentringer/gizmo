package com.derekentringer.gizmo.component.actor.structure.destroyable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DestroyableBlockDirtActor extends BaseActor {

    private static final String TAG = DestroyableBlockDirtActor.class.getSimpleName();

    private TextureRegion[] mDestroyableBlockSprite;
    private Texture mDestroyableBlock;

    public DestroyableBlockDirtActor(Body body) {
        super(body);

        mDestroyableBlock = Gizmo.assetManager.get("res/image/tile/destroyable_block_dirt.png", Texture.class);
        mDestroyableBlockSprite = TextureRegion.split(mDestroyableBlock, 32, 32)[0];

        setIsPlayingAnimation(false);
        setAnimation(mDestroyableBlockSprite, 1 / 12f);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBaseModel;
    }

}