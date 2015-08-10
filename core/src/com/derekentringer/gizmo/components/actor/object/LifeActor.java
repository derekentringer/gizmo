package com.derekentringer.gizmo.components.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.object.LifeModel;

public class LifeActor extends BaseActor {

    private LifeModel mLifeModel = new LifeModel();

    private TextureRegion[] mLifeSprite;
    private Texture mLife;

    public LifeActor(Body body) {
        super(body);
        mLife = Gizmo.assetManager.get("res/images/life.png", Texture.class);
        mLifeSprite = TextureRegion.split(mLife, 32, 32)[0];
        setAnimation(mLifeSprite, 1 / 12f);
    }

    @Override
    public BaseModel getPlayerModel() {
        return mLifeModel;
    }

}