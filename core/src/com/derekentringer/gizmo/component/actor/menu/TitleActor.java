package com.derekentringer.gizmo.component.actor.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;

public class TitleActor extends BaseActor {

    private TextureRegion[] mTitleSprite;
    private Texture mTitle;

    public TitleActor(Body body) {
        super(body);

        mTitle = Gizmo.getAssetManager().get("res/image/start/gizmo_title.png", Texture.class);
        mTitleSprite = TextureRegion.split(mTitle, 440, 260)[0];

        setAnimation(mTitleSprite, 1 / 12f);
    }
}