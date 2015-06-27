package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationManager {

    private TextureRegion[] frames;
    private float time;
    private float delay;
    private int currentFrame;
    private int timesPlayed;

    public AnimationManager() {
    }

    public AnimationManager(TextureRegion[] frames) {
        this(frames, 1 / 12f);
    }

    public AnimationManager(TextureRegion[] frames, float delay) {
        setFrames(frames, delay);
    }

    public void setFrames(TextureRegion[] frames, float delay) {
        this.frames = frames;
        this.delay = delay;
        time = 0;
        currentFrame = 0;
        timesPlayed = 0;
    }

    public void update(float delayTime) {
        if (delay <= 0) {
            return;
        }
        time += delayTime;
        while (time >= delay) {
            step();
        }
    }

    private void step() {
        time -= delay;
        currentFrame++;
        if (currentFrame == frames.length) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public TextureRegion getFrame() {
        return frames[currentFrame];
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

}