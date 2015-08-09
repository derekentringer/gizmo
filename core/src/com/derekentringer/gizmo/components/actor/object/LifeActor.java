package com.derekentringer.gizmo.components.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.object.LifeModel;

public class LifeActor extends BaseActor {

    private LifeModel lifeModel = new LifeModel();

    private TextureRegion[] sLifeSprite;
    private Texture sLife;

    public LifeActor(Body body) {
        super(body);

        sLife = Gizmo.assetManager.get("res/images/life.png", Texture.class);
        sLifeSprite = TextureRegion.split(sLife, 32, 32)[0];

        setAnimation(sLifeSprite, 1 / 12f);
    }

    @Override
    public BaseModel getUserData() {
        return lifeModel;
    }

}