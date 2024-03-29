package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationManager {

    private static final String TAG = AnimationManager.class.getSimpleName();

    private TextureRegion[] mFrames;
    private float mTime;
    private float mDelay;
    private int mCurrentFrame;
    private int mTimesPlayed;
    private boolean mDidAnimationRunOnce;

    public AnimationManager() {
    }

    public AnimationManager(TextureRegion[] frames) {
        this(frames, 1 / 12f);
    }

    public AnimationManager(TextureRegion[] frames, float delay) {
        setFrames(frames, delay);
    }

    public void setFrames(TextureRegion[] frames, float delay) {
        mFrames = frames;
        mDelay = delay;
        mTime = 0;
        mCurrentFrame = 0;
        mTimesPlayed = 0;
    }

    public void update(float delayTime) {
        if (mDelay <= 0) {
            return;
        }
        mTime += delayTime;
        while (mTime >= mDelay) {
            step();
        }
    }

    private void step() {
        mTime -= mDelay;
        mCurrentFrame++;
        if (mCurrentFrame == mFrames.length) {
            mCurrentFrame = 0;
            mTimesPlayed++;
            if (mTimesPlayed == 1) {
                mDidAnimationRunOnce = true;
            }
        }
    }

    public TextureRegion getFrame() {
        return mFrames[mCurrentFrame];
    }

    public int getTimesPlayed() {
        return mTimesPlayed;
    }

    public boolean didAnimationRunOnce() {
        return mDidAnimationRunOnce;
    }

}