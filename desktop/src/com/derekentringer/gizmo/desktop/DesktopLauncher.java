package com.derekentringer.gizmo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.derekentringer.gizmo.Gizmo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = Constants.GAME_WIDTH;
        //config.height = Constants.GAME_HEIGHT;
        config.vSyncEnabled = true;
		new LwjglApplication(new Gizmo(), config);
	}
}
