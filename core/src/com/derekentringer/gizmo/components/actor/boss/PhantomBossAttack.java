package com.derekentringer.gizmo.components.actor.boss;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.components.actor.boss.interfaces.IPhantomBossAttack;
import com.derekentringer.gizmo.components.actor.enemy.PhantomActor;
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

    private World mWorld;
    private Vector2 mPlayerPosition;
    private boolean mAttackInitiated;

    public PhantomBossAttack(World world) {
        mWorld = world;
    }

    public void addListener(IPhantomBossAttack listener) {
        listeners.add(listener);
    }

    public void initiate(Vector2 playerPosition) {
        mPlayerPosition = playerPosition;
        startAttackTimer();
    }

    private void startAttackTimer() {
        if(!getAttackInitiated()) {

            setAttackInitiated(true);

            Thread t = new Thread() {
                public void run() {
                    try {
                        sleep(ATTACK_DELAY);
                    }
                    catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    finally {
                        GLog.d(TAG, "attack and shake");

                        for (IPhantomBossAttack listener : listeners) {
                            listener.phantomBossShakeCamera(true);
                        }
                        startShakingTimer();
                        releasePhantoms();
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
                catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                finally {
                    GLog.d(TAG, "stop shaking");
                    for (IPhantomBossAttack listener : listeners) {
                        listener.phantomBossShakeCamera(false);
                    }
                }
            }
        };
        t.start();
    }

    public boolean getAttackInitiated() {
        return mAttackInitiated;
    }

    public void setAttackInitiated(boolean initiated) {
        mAttackInitiated = initiated;
    }

    private void breatheFire() {
    }

    private void releasePhantoms() {
        PhantomActor phantomActor = new PhantomActor(EnemyUtils.createPhantom(new PhantomModel(), mWorld, new Vector2(WorldUtils.ppmCalcReverse(mPlayerPosition.x - 1), WorldUtils.ppmCalcReverse(mPlayerPosition.y + 3))));
        phantomActor.setName(PhantomModel.PHANTOM);
        addActor(phantomActor);
        for (IPhantomBossAttack listener : listeners) {
            listener.phantomBossAddPhantomActor(phantomActor);
        }
    }

}