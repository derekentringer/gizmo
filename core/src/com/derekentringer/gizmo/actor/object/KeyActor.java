package com.derekentringer.gizmo.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.BaseActor;
import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.actor.data.object.KeyData;

public class KeyActor extends BaseActor {

    private TextureRegion[] keySprite;
    private Texture key;

    public KeyActor(Body body, String keyType) {
        super(body);

        if(keyType.equalsIgnoreCase(KeyData.KEY_GOLD)) {
            key = Gizmo.assetManager.get("res/images/key_gold.png", Texture.class);
        }
        else if(keyType.equalsIgnoreCase(KeyData.KEY_BRONZE)) {
            key = Gizmo.assetManager.get("res/images/key_bronze.png", Texture.class);
        }
        else if(keyType.equalsIgnoreCase(KeyData.KEY_BLOOD)) {
            key = Gizmo.assetManager.get("res/images/key_blood.png", Texture.class);
        }
        keySprite = TextureRegion.split(key, 32, 32)[0];

        setAnimation(keySprite, 1/12f);
    }

    @Override
    public ObjectData getUserData() {
        return null;
    }

}