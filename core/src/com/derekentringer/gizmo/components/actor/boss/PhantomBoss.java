package com.derekentringer.gizmo.components.actor.boss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.components.actor.boss.interfaces.IPhantomBoss;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.enemy.PhantomLargeModel;

import java.util.ArrayList;

public class PhantomBoss extends BaseActor {

    private static final String TAG = PhantomBoss.class.getSimpleName();

    private PhantomLargeModel mPhantomLargeModel = new PhantomLargeModel();

    private ArrayList<IPhantomBoss> listeners = new ArrayList<IPhantomBoss>();

    private Vector2 mPlayerPosition = new Vector2();
    private TextureRegion[] mPhantomSprite;
    private Texture mPhantomLarge;

    private PhantomBossAttack attack = new PhantomBossAttack();

    public void addListener(IPhantomBoss listener) {
        listeners.add(listener);
    }

    public PhantomBoss(Body body) {
        super(body);
        mPhantomLarge = Gizmo.assetManager.get("res/images/enemies/boss/phantom.png", Texture.class);
        mPhantomSprite = TextureRegion.split(mPhantomLarge, 320, 320)[0];
        setAnimation(mPhantomSprite, 1 / 5f);
    }

    @Override
    public BaseModel getBaseModel() {
        return mPhantomLargeModel;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getPlayerPosition().x > (getPosition().x - 5)) {
            for(IPhantomBoss listener : listeners){
                listener.shakeCamera(true);
                attack.initiate();
            }
        }
        else {
            for(IPhantomBoss listener : listeners){
                listener.shakeCamera(false);
                attack.setAttackInitiated(false);
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