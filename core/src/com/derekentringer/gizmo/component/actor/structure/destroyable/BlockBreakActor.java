package com.derekentringer.gizmo.component.actor.structure.destroyable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.IBaseActor;

public class BlockBreakActor extends BaseActor implements IBaseActor {

    private static final String TAG = BlockBreakActor.class.getSimpleName();

    private TextureRegion[] mBreakSprite;
    private Texture mBreak;
    
    public BlockBreakActor(Body body) {
        super(body);
        addListener(this);
        mBreak = Gizmo.assetManager.get("res/image/tile/break.png", Texture.class);
        mBreakSprite = TextureRegion.split(mBreak, 32, 32)[0];

        setAnimation(mBreakSprite, 1 / 12f);
    }

    @Override
    public void isAnimationFinished(boolean isFinished) {
        if (isFinished == true) {
            setIsPlayingAnimation(false);
        }
    }
    
}