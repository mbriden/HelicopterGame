package com.haxtastic.helicoptergame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.haxtastic.helicoptergame.Helicoptergame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "HelicopterGame";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 720;
		cfg.useCPUSynch = false;
		cfg.vSyncEnabled = false;
		
		new LwjglApplication(new Helicoptergame(), cfg);
	}
}
