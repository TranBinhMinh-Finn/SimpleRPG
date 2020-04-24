package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.SimpleRPG;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 60 ;
		config.width = SimpleRPG.WIDTH;
		config.height = SimpleRPG.HEIGHT;
		new LwjglApplication(new SimpleRPG(), config);
	}
}
