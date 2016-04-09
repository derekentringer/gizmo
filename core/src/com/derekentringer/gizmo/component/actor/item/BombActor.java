package com.derekentringer.gizmo.component.actor.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.item.interfaces.IItems;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;
import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.util.ItemUtils;
import com.derekentringer.gizmo.util.log.GLog;
import com.derekentringer.gizmo.util.map.MapParser;

import java.util.ArrayList;

public class BombActor extends BaseActor {

    private static final String TAG = BombActor.class.getSimpleName();

    protected ArrayList<IItems> listeners = new ArrayList<IItems>();

    protected static final float MOVEMENT_FORCE_X = 2;
    protected static final float MOVEMENT_FORCE_Y = 2;
    protected static final int BOMB_TIMER = 3;

    protected TextureRegion[] mBombSprite;
    protected Texture mBombTexture;

    protected World mWorld;
    protected MapParser mMapParser;
    protected GameStage mGameStage;
    protected int mPlayerFacingDirection;

    public void addListener(IItems listener) {
        listeners.add(listener);
    }

    public BombActor(Body body, World world, MapParser mapParser, GameStage gameStage, int playerFacingDirection) {
        super(body);
        mWorld = world;
        mMapParser = mapParser;
        mGameStage = gameStage;
        mPlayerFacingDirection = playerFacingDirection;
        mBombTexture = Gizmo.getAssetManager().get("res/image/item/bomb.png", Texture.class);
        mBombSprite = TextureRegion.split(mBombTexture, 32, 32)[0];
        setAnimation(mBombSprite, 1 / 12f);

        if (mPlayerFacingDirection == PlayerActor.FACING_RIGHT) {
            mBody.applyLinearImpulse(MOVEMENT_FORCE_X, MOVEMENT_FORCE_Y, mBody.getPosition().x, mBody.getPosition().y, true);
        }
        else {
            mBody.applyLinearImpulse(-MOVEMENT_FORCE_X, MOVEMENT_FORCE_Y, mBody.getPosition().x, mBody.getPosition().y, true);
        }
        startTimer(BOMB_TIMER);
    }

    private void startTimer(int timerLength) {
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                bombExplode();
            }
        }, timerLength);
    }

    private void bombExplode() {
        GLog.d(TAG, "bombExplode");
        ItemUtils.explodeBomb(mWorld, this.getPosition(), mMapParser, mGameStage);
        removeBomb();
    }

    private void removeBomb() {
        GLog.d(TAG, "removeBomb");
        for (IItems listener : listeners) {
            listener.removePlayerItemFromStage(this);
        }
    }

}