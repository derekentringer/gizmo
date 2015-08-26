package com.derekentringer.gizmo.components.actor.boss;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.components.actor.boss.interfaces.IPhantomBossAttack;
import com.derekentringer.gizmo.components.actor.enemy.PhantomActor;
import com.derekentringer.gizmo.model.enemy.FireBallModel;
import com.derekentringer.gizmo.model.enemy.PhantomModel;
import com.derekentringer.gizmo.util.EnemyUtils;
import com.derekentringer.gizmo.util.WorldUtils;

import java.util.ArrayList;

public class PhantomBossAttack extends Stage {

    private static final String TAG = PhantomBossAttack.class.getSimpleName();

    private ArrayList<IPhantomBossAttack> listeners = new ArrayList<IPhantomBossAttack>();

    private static final int PHANTOM_ATTACK_DELAY = 5000;
    private static final int FIREBALL_ATTACK_DELAY = 2000;

    private static final int SHAKE_DELAY = 2000;
    private static final int SHAKE_LENGTH = 4000;

    private static final int MAX_PHANTOMS = 1;
    private static final int MIN_PHANTOMS = 0;
    private static final int MAX_PHANTOMS_ALLOWED = 6;

    private World mWorld;

    private Vector2 mPlayerPosition;
    private Vector2 mPhantomPosition;

    private boolean mShakingInitiated;
    private boolean mPhantomAttackInitiated;
    private boolean mFireBallAttackInitiated;

    private float mTotalTimePassedShaking;
    private float mTotalTimePassedPhantomAttack;
    private float mTotalTimePassedFireBallAttack;

    private int mTotalPhantoms;

    public PhantomBossAttack(World world) {
        mWorld = world;
    }

    public void addListener(IPhantomBossAttack listener) {
        listeners.add(listener);
    }

    public void attack(float delta, Vector2 playerPosition, Vector2 phantomPosition) {
        mPlayerPosition = playerPosition;
        mPhantomPosition = phantomPosition;

        float mDeltaToSeconds = delta * 1000;

        // shaking
        if (!getShakingInitiated()) {
            mTotalTimePassedShaking += mDeltaToSeconds;
            if (mTotalTimePassedShaking > SHAKE_DELAY) {
                for (IPhantomBossAttack listener : listeners) {
                    listener.phantomBossShakeCamera(true);
                }
                turnOffShaking();
                setShakingInitiated(true);
            }
        }
        else {
            mTotalTimePassedShaking = 0;
        }

        // phantoms
        if (!getPhantomAttackInitiated()) {
            mTotalTimePassedPhantomAttack += mDeltaToSeconds;
            if (mTotalTimePassedPhantomAttack > PHANTOM_ATTACK_DELAY) {
                releasePhantoms(MathUtils.random(MIN_PHANTOMS, MAX_PHANTOMS));
                turnOffPhantoms();
                setPhantomAttackInitiated(true);
            }
        }
        else {
            mTotalTimePassedPhantomAttack = 0;
        }

        // fireballs
        if (!getFireBallAttackInitiated()) {
            mTotalTimePassedFireBallAttack += mDeltaToSeconds;
            if (mTotalTimePassedFireBallAttack > FIREBALL_ATTACK_DELAY) {
                breatheFire();
                turnOffFireBall();
                setFireBallAttackInitiated(true);
            }
        }
        else {
            mTotalTimePassedFireBallAttack = 0;
        }
    }

    private void turnOffShaking() {
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(SHAKE_LENGTH);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    for (IPhantomBossAttack listener : listeners) {
                        listener.phantomBossShakeCamera(false);
                    }
                    setShakingInitiated(false);
                }
            }
        };
        t.start();
    }

    private void turnOffPhantoms() {
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(PHANTOM_ATTACK_DELAY);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    setPhantomAttackInitiated(false);
                }
            }
        };
        t.start();
    }

    private void turnOffFireBall() {
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(FIREBALL_ATTACK_DELAY);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    setFireBallAttackInitiated(false);
                }
            }
        };
        t.start();
    }

    public boolean getShakingInitiated() {
        return mShakingInitiated;
    }

    public void setShakingInitiated(boolean shaking) {
        mShakingInitiated = shaking;
    }

    public boolean getPhantomAttackInitiated() {
        return mPhantomAttackInitiated;
    }

    public void setPhantomAttackInitiated(boolean initiated) {
        mPhantomAttackInitiated = initiated;
    }

    public boolean getFireBallAttackInitiated() {
        return mFireBallAttackInitiated;
    }

    public void setFireBallAttackInitiated(boolean initiated) {
        mFireBallAttackInitiated = initiated;
    }

    private void releasePhantoms(float amountOfPhantoms) {
        for (int i = 0; i <= amountOfPhantoms; i++) {
            if (mTotalPhantoms < MAX_PHANTOMS_ALLOWED) {
                PhantomActor phantomActor = new PhantomActor(EnemyUtils.createPhantom(new PhantomModel(), mWorld, new Vector2(WorldUtils.ppmCalcReverse(mPlayerPosition.x - 1), WorldUtils.ppmCalcReverse(mPlayerPosition.y))));
                phantomActor.setName(PhantomModel.PHANTOM);
                addActor(phantomActor);
                for (IPhantomBossAttack listener : listeners) {
                    listener.phantomBossAddActor(phantomActor);
                }
                mTotalPhantoms++;
            }
        }
    }

    private void breatheFire() {
        FireBallActor fireBallActor = new FireBallActor(EnemyUtils.createFireBall(new FireBallModel(), mWorld, new Vector2(WorldUtils.ppmCalcReverse(mPhantomPosition.x - 1), WorldUtils.ppmCalcReverse(mPlayerPosition.y))));
        fireBallActor.setName(FireBallModel.FIREBALL);
        addActor(fireBallActor);
        for (IPhantomBossAttack listener : listeners) {
            listener.phantomBossAddActor(fireBallActor);
        }
    }

}