package com.derekentringer.gizmo.components.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.object.HeartModel;

public class HeartActor extends BaseActor {

    private static final String TAG = HeartActor.class.getSimpleName();

    private HeartModel mHeartModel = new HeartModel();

    private TextureRegion[] mHeartSprite;
    private Texture mHeart;

    public HeartActor(Body body) {
        super(body);

        mHeart = Gizmo.assetManager.get("res/images/heart.png", Texture.class);
        mHeartSprite = TextureRegion.split(mHeart, 32, 32)[0];

        setAnimation(mHeartSprite, 1 / 12f);
    }

    @Override
    public BaseModel getPlayerModel() {
        return mHeartModel;
    }

}