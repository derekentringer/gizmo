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
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class PhantomBossAttack extends Stage {

    private static final String TAG = PhantomBossAttack.class.getSimpleName();

    private ArrayList<IPhantomBossAttack> listeners = new ArrayList<IPhantomBossAttack>();

    private static final int ATTACK_DELAY = 4000;
    private static final int SHAKE_DELAY = 3000;
    private static final int SHAKE_LENGTH = 4000;

    private static final int MAX_PHANTOMS = 1;
    private static final int MIN_PHANTOMS = 0;
    private static final int MAX_PHANTOMS_ALLOWED = 6;

    private World mWorld;

    private Vector2 mPlayerPosition;
    private Vector2 mPhantomPosition;

    private boolean mShakingInitiated;
    private boolean mAttackInitiated;
    private float mTotalTimePassedShaking;
    private float mTotalTimePassedAttack;

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

        float mDelta = delta * 1000;

        if (!getShakingInitiated()) {

            mTotalTimePassedShaking += mDelta;

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

        if (!getAttackInitiated()) {

            mTotalTimePassedAttack += mDelta;

            if (mTotalTimePassedAttack > ATTACK_DELAY) {
                mTotalTimePassedAttack = 0;
                releasePhantoms(MathUtils.random(MIN_PHANTOMS, MAX_PHANTOMS));
                //breatheFire();
                turnOffPhantoms();
                setAttackInitiated(true);
            }
        }
        else {
            mTotalTimePassedAttack = 0;
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
                    sleep(ATTACK_DELAY);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    setAttackInitiated(false);
                }
            }
        };
        t.start();
    }

    /*private void startAttackTimer() {
        if (!getAttackInitiated()) {

            setAttackInitiated(true);

            Thread t = new Thread() {
                public void run() {
                    try {
                        sleep(ATTACK_DELAY);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        for (IPhantomBossAttack listener : listeners) {
                            listener.phantomBossShakeCamera(true);
                        }
                        startShakingTimer();
                        releasePhantoms(MathUtils.random(MIN_PHANTOMS, MAX_PHANTOMS));
                        setAttackInitiated(false);
                    }
                }
            };
            t.start();
        }
    }

    private void startShakingTimer() {
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(SHAKE_DELAY);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    for (IPhantomBossAttack listener : listeners) {
                        listener.phantomBossShakeCamera(false);
                    }
                    //breatheFire();
                }
            }
        };
        t.start();
    }*/

    public boolean getAttackInitiated() {
        return mAttackInitiated;
    }

    public void setAttackInitiated(boolean initiated) {
        mAttackInitiated = initiated;
    }

    public boolean getShakingInitiated() {
        return mShakingInitiated;
    }

    public void setShakingInitiated(boolean shaking) {
        mShakingInitiated = shaking;
    }

    public float getTotalTimePassedShaking() {
        return mTotalTimePassedShaking;
    }

    public void setTotalTimePassedShaking(float time) {
        mTotalTimePassedShaking = time;
    }

    public float getTotalTimePassedAttack() {
        return mTotalTimePassedAttack;
    }

    public void setTotalTimePassedAttack(float time) {
        mTotalTimePassedAttack = time;
    }

    private void breatheFire() {
        GLog.d(TAG, "breatheFire");
        FireBallActor fireBallActor = new FireBallActor(EnemyUtils.createFireBall(new FireBallModel(), mWorld, new Vector2(WorldUtils.ppmCalcReverse(mPhantomPosition.x - 1), WorldUtils.ppmCalcReverse(mPlayerPosition.y))));
        fireBallActor.setName(FireBallModel.FIREBALL);
        addActor(fireBallActor);
        for (IPhantomBossAttack listener : listeners) {
            listener.phantomBossAddActor(fireBallActor);
        }
    }

    private void releasePhantoms(float amountOfPhantoms) {
        GLog.d(TAG, "releasePhantoms");
        for (int i = 0; i <= amountOfPhantoms; i++) {
            if (mTotalPhantoms < MAX_PHANTOMS_ALLOWED) {
                PhantomActor phantomActor = new PhantomActor(EnemyUtils.createPhantom(new PhantomModel(), mWorld, new Vector2(WorldUtils.ppmCalcReverse(mPlayerPosition.x - 1), WorldUtils.ppmCalcReverse(mPlayerPosition.y + 3))));
                phantomActor.setName(PhantomModel.PHANTOM);
                addActor(phantomActor);
                for (IPhantomBossAttack listener : listeners) {
                    listener.phantomBossAddActor(phantomActor);
                }
                mTotalPhantoms++;
            }
        }
    }

}