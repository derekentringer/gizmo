package com.derekentringer.gizmo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.controllers.Controllers;
import com.derekentringer.gizmo.screen.LoadingScreen;
import com.derekentringer.gizmo.util.input.InputProcessor;

public class Gizmo extends Game {

	public static AssetManager assetManager = new AssetManager();

    @Override
    public void create() {
        Controllers.addListener(new InputProcessor());
        Gdx.input.setInputProcessor(new InputProcessor());
        setScreen(new LoadingScreen(this));
    }

}