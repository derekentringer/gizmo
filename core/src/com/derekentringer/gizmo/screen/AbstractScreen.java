package com.derekentringer.gizmo.screen;

import com.badlogic.gdx.Screen;
import com.derekentringer.gizmo.Gizmo;

public abstract class AbstractScreen implements Screen {

    protected Gizmo game;

    public AbstractScreen(Gizmo game) {
        this.game = game;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}