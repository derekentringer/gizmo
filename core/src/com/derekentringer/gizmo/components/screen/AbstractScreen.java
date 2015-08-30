package com.derekentringer.gizmo.components.screen;

import com.badlogic.gdx.Screen;
import com.derekentringer.gizmo.Gizmo;

public abstract class AbstractScreen implements Screen {

    protected Gizmo mGizmoGame;

    public AbstractScreen(Gizmo gizmoGame) {
        this.mGizmoGame = gizmoGame;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        this.dispose();
    }

}