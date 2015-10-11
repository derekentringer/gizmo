package com.derekentringer.gizmo.component.actor.boss.phantom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.boss.phantom.interfaces.IPhantomBoss;
import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.util.BodyUtils;

import java.util.ArrayList;

public class PhantomBossActor extends BaseActor {

    private static final String TAG = PhantomBossActor.class.getSimpleName();

    private static final float MOVEMENT_FORCE = 0.5f;

    private ArrayList<IPhantomBoss> listeners = new ArrayList<IPhantomBoss>();

    private com.derekentringer.gizmo.component.stage.GameStage mGameStage;
    private Vector2 mPlayerPosition = new Vector2();
    private TextureRegion[] mPhantomSprite;
    private Texture mPhantomLarge;

    private PhantomBossAttack mPhantomBossAttack;

    public void addListener(IPhantomBoss listener) {
        listeners.add(listener);
    }

    public PhantomBossActor(World world, GameStage gameStage, Body body) {
        super(body);
        mGameStage = gameStage;

        mPhantomLarge = Gizmo.assetManager.get("res/image/character/enemy/boss/phantom/phantom.png", Texture.class);
        mPhantomSprite = TextureRegion.split(mPhantomLarge, 320, 320)[0];
        setAnimation(mPhantomSprite, 1 / 5f);

        mPhantomBossAttack = new PhantomBossAttack(world);
        mPhantomBossAttack.addListener(mGameStage);
    }

    @Override
    public BaseModel getBaseModel() {
        return mBaseModel;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getPlayerPosition().x > (getPosition().x - 5)) {
            mPhantomBossAttack.attack(delta, getPlayerPosition(), getPosition());
        }
        BodyUtils.applyLinearImpulseToBody(mBody, -MOVEMENT_FORCE, "x");
    }

    public Vector2 getPlayerPosition() {
        return mPlayerPosition;
    }

    public void setPlayerPosition(Vector2 playerPosition) {
        mPlayerPosition.x = playerPosition.x;
        mPlayerPosition.y = playerPosition.y;
    }

}