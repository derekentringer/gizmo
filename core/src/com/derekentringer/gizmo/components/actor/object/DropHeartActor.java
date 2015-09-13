package com.derekentringer.gizmo.components.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;

public class DropHeartActor extends BaseActor {

    private static final String TAG = DropHeartActor.class.getSimpleName();

    private TextureRegion[] mDropHeartSprite;
    private Texture mDropHeart;

    public DropHeartActor(Body body) {
        super(body);

        mDropHeart = Gizmo.assetManager.get("res/images/items/drop_heart.png", Texture.class);
        mDropHeartSprite = TextureRegion.split(mDropHeart, 8, 8)[0];

        setAnimation(mDropHeartSprite, 1 / 12f);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBaseModel;
    }

}