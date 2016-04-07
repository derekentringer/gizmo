package com.derekentringer.gizmo.component.actor.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;

public class WhiteDotActor extends BaseActor {

    private static final String TAG = WhiteDotActor.class.getSimpleName();

    private TextureRegion[] mWhiteDotSprite;
    private Texture mWhiteDot;

    public WhiteDotActor(Body body) {
        super(body);

        mWhiteDot = Gizmo.getAssetManager().get("res/image/start/white_dot.png", Texture.class);
        mWhiteDotSprite = TextureRegion.split(mWhiteDot, 8, 8)[0];

        setAnimation(mWhiteDotSprite, 1 / 12f);
    }

}