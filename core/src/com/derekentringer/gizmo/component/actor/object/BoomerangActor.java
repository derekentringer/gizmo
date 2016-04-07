package com.derekentringer.gizmo.component.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.model.object.BoomerangModel;

public class BoomerangActor extends BaseActor {

    private static final String TAG = BoomerangActor.class.getSimpleName();

    private TextureRegion[] mBoomerangSprite;
    private Texture mBoomerangTexture;

    public BoomerangActor(Body body, String boomerangType) {
        super(body);
        if (boomerangType.equalsIgnoreCase(BoomerangModel.BOOMERANG_WOOD)) {
            mBoomerangTexture = Gizmo.getAssetManager().get("res/image/item/boomerang_wood_pickup_shine.png", Texture.class);
        }
        else if (boomerangType.equalsIgnoreCase(BoomerangModel.BOOMERANG_AMETHYST)) {
            mBoomerangTexture = Gizmo.getAssetManager().get("res/image/item/boomerang_amethyst_pickup_shine.png", Texture.class);
        }
        else if (boomerangType.equalsIgnoreCase(BoomerangModel.BOOMERANG_EMERALD)) {
            mBoomerangTexture = Gizmo.getAssetManager().get("res/image/item/boomerang_emerald_pickup_shine.png", Texture.class);
        }
        else if (boomerangType.equalsIgnoreCase(BoomerangModel.BOOMERANG_BLOODSTONE)) {
            mBoomerangTexture = Gizmo.getAssetManager().get("res/image/item/boomerang_bloodstone_pickup_shine.png", Texture.class);
        }
        mBoomerangSprite = TextureRegion.split(mBoomerangTexture, 32, 32)[0];
        setAnimation(mBoomerangSprite, 1 / 12f);
    }

}