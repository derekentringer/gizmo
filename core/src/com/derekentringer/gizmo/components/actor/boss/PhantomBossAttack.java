package com.derekentringer.gizmo.components.actor.boss;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.util.log.GLog;

public class PhantomBossAttack extends Stage {

    private static final String TAG = PhantomBossAttack.class.getSimpleName();

    private static final int ATTACK_DELAY = 4000;

    private boolean mAttackInitiated;

    public PhantomBossAttack() {
    }

    public void initiate() {
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
                        GLog.d(TAG, "ATTACK!");
                        setAttackInitiated(false);
                    }
                }
            };
            t.start();
        }
    }

    public boolean getAttackInitiated() {
        return mAttackInitiated;
    }

    public void setAttackInitiated(boolean initiated) {
        mAttackInitiated = initiated;
    }

}