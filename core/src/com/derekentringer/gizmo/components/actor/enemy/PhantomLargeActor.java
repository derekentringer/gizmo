package com.derekentringer.gizmo.components.actor.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.enemy.PhantomLargeModel;

public class PhantomLargeActor extends BaseActor {

    private PhantomLargeModel phantomLargeModel = new PhantomLargeModel();

    private TextureRegion[] phantomSprite;
    private Texture phantomLarge;

    public PhantomLargeActor(Body body) {
        super(body);
        phantomLarge = Gizmo.assetManager.get("res/images/enemies/phantom/phantom_large.png", Texture.class);
        phantomSprite = TextureRegion.split(phantomLarge, 320, 320)[0];
        setAnimation(phantomSprite, 1 / 12f);
    }

    @Override
    public BaseModel getUserData() {
        return phantomLargeModel;
    }

}