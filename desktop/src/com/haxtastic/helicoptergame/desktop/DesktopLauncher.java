package com.haxtastic.helicoptergame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.haxtastic.helicoptergame.HelicopterGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "HelicopterGame";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new HelicopterGame(), config);
	}
}
