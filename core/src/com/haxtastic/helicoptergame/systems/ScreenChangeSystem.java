package com.haxtastic.helicoptergame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.haxtastic.helicoptergame.GameScreen;
import com.haxtastic.helicoptergame.components.ScreenChange;
import com.haxtastic.helicoptergame.components.Sprite;

public class ScreenChangeSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<ScreenChange> sm;
	
	private Game game;
	private World world;
	private OrthographicCamera camera;
	
	@SuppressWarnings("unchecked")
	public ScreenChangeSystem(Game g, World w, OrthographicCamera c) {
		super(Aspect.getAspectForAll(Sprite.class, ScreenChange.class));
		game = g;
		world = w;
		camera = c;
	}

	@Override
	protected void initialize() {
	}
	
	@Override
	protected void begin() {
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	protected void process(Entity e) {
		ScreenChange screen = sm.get(e);
		if(screen.change == true){
			e.deleteFromWorld();
			game.setScreen(new GameScreen(game, world, camera));
		}
	}

	protected void end() {
	}
}
