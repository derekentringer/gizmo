package com.derekentringer.gizmo

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.controllers.Controllers
import com.derekentringer.gizmo.component.screen.LoadingScreen
import com.derekentringer.gizmo.settings.Constants
import com.derekentringer.gizmo.util.input.InputProcessor

class Gizmo : Game() {

    override fun create() {
        Controllers.addListener(InputProcessor())
        Gdx.input.inputProcessor = InputProcessor()
        Gdx.app.logLevel = Constants.LOG_LEVEL
        setScreen(LoadingScreen(this))
    }

}