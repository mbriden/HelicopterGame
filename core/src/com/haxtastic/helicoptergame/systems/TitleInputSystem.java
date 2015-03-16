package com.haxtastic.helicoptergame.systems;

import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.haxtastic.helicoptergame.Constants;
import com.haxtastic.helicoptergame.components.ScreenChange;

public class TitleInputSystem extends VoidEntitySystem implements InputProcessor {
	
	public TitleInputSystem() {
	}
	
	@Override
	protected void initialize() {
		Gdx.input.setInputProcessor(this);
	}
	
	protected void processSystem(){
	}

	@Override
	public boolean keyDown(int keycode) {
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		world.getManager(GroupManager.class).getEntities(Constants.Groups.SCREEN_CHANGE).get(0).getComponent(ScreenChange.class).change = true;
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
