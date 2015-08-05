package com.derekentringer.gizmo.components.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.object.HeartModel;

public class HeartActor extends BaseActor {

    private HeartModel heartModel = new HeartModel();

    private TextureRegion[] heartSprite;
    private Texture heart;

    public HeartActor(Body body) {
        super(body);

        heart = Gizmo.assetManager.get("res/images/heart.png", Texture.class);
        heartSprite = TextureRegion.split(heart, 32, 32)[0];

        setAnimation(heartSprite, 1 / 12f);
    }

    @Override
    public BaseModel getUserData() {
        return heartModel;
    }

}