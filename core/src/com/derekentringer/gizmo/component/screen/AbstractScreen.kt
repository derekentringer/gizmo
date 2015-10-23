package com.derekentringer.gizmo.component.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.derekentringer.gizmo.Gizmo

abstract class AbstractScreen(protected var mGizmoGame: Gizmo) : Screen {

    protected var assetManager = AssetManager()

    override fun pause() {
    }

    override fun resume() {
    }

    override fun dispose() {
        this.dispose()
    }

}