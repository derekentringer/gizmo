package com.derekentringer.gizmo.components.actor.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.components.actor.enemy.interfaces.IEnemy;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.enemy.PhantomLargeModel;

import java.util.ArrayList;

public class PhantomLargeActor extends BaseActor {

    private static final String TAG = PhantomLargeActor.class.getSimpleName();

    private PhantomLargeModel mPhantomLargeModel = new PhantomLargeModel();

    private ArrayList<IEnemy> listeners = new ArrayList<IEnemy>();

    private Vector2 mPlayerPosition = new Vector2();

    private TextureRegion[] mPhantomSprite;
    private Texture mPhantomLarge;

    public void addListener(IEnemy listener) {
        listeners.add(listener);
    }

    public PhantomLargeActor(Body body) {
        super(body);
        mPhantomLarge = Gizmo.assetManager.get("res/images/enemies/phantom/phantom_large.png", Texture.class);
        mPhantomSprite = TextureRegion.split(mPhantomLarge, 320, 320)[0];
        setAnimation(mPhantomSprite, 1 / 12f);
    }

    @Override
    public BaseModel getPlayerModel() {
        return mPhantomLargeModel;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getPlayerPosition().x < (getPosition().x - 5)) {
            for(IEnemy listener : listeners){
                listener.shakeCamera(true);
            }
        }
        else {
            for(IEnemy listener : listeners){
                listener.shakeCamera(false);
            }
        }
    }

    public Vector2 getPlayerPosition() {
        return mPlayerPosition;
    }

    public void setPlayerPosition(float xPos) {
        mPlayerPosition.x = xPos;
    }

}