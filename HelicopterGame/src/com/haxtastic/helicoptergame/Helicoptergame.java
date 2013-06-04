package com.haxtastic.helicoptergame;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Helicoptergame extends Game {
	public static final int FRAME_WIDTH = 1920;
	public static final int FRAME_HEIGHT = 1080;
	
	@Override
	public void create() {
		World world = new World();
		world.setManager(new GroupManager());
		OrthographicCamera camera = new OrthographicCamera(Helicoptergame.FRAME_WIDTH, Helicoptergame.FRAME_HEIGHT);
		Constants.PIXELS_PER_METER_X = Helicoptergame.FRAME_WIDTH/16;
		Constants.PIXELS_PER_METER_Y = Helicoptergame.FRAME_HEIGHT/9;
		camera.position.set(0, camera.viewportHeight/2, 0);
		camera.update();
		setScreen(new TitleScreen(this, world, camera));
	}
}
