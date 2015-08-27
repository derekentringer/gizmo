package com.derekentringer.gizmo.components.actor.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.object.KeyModel;

public class KeyActor extends BaseActor {

    private static final String TAG = BaseActor.class.getSimpleName();

    private KeyModel mKeyModel = new KeyModel();

    private TextureRegion[] mKeySprite;
    private Texture mKey;

    public KeyActor(Body body, String keyType) {
        super(body);
        if (keyType.equalsIgnoreCase(KeyModel.KEY_GOLD)) {
            mKey = Gizmo.assetManager.get("res/images/objects/key_gold.png", Texture.class);
        }
        else if (keyType.equalsIgnoreCase(KeyModel.KEY_BRONZE)) {
            mKey = Gizmo.assetManager.get("res/images/objects/key_bronze.png", Texture.class);
        }
        else if (keyType.equalsIgnoreCase(KeyModel.KEY_BLOOD)) {
            mKey = Gizmo.assetManager.get("res/images/objects/key_blood.png", Texture.class);
        }
        else if (keyType.equalsIgnoreCase(KeyModel.KEY_BLACK)) {
            mKey = Gizmo.assetManager.get("res/images/objects/key_black.png", Texture.class);
        }
        mKeySprite = TextureRegion.split(mKey, 32, 32)[0];
        setAnimation(mKeySprite, 1 / 12f);
    }

    @Override
    public BaseModel getBaseModel() {
        return mKeyModel;
    }

}