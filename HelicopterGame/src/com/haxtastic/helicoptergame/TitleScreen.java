package com.haxtastic.helicoptergame;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.haxtastic.helicoptergame.systems.InterpolationSystem;
import com.haxtastic.helicoptergame.systems.ScreenChangeSystem;
import com.haxtastic.helicoptergame.systems.TitleInputSystem;
import com.haxtastic.helicoptergame.systems.TitleRenderSystem;
import com.haxtastic.helicoptergame.components.Position;
import com.haxtastic.helicoptergame.components.ScreenChange;
import com.haxtastic.helicoptergame.components.Sprite;

public class TitleScreen implements Screen {
	private Game game;
	private World world;
	private OrthographicCamera camera;
	
	private float accum = 0;
	private float dt = 1.0f / 45.0f;
	
	private TitleRenderSystem renderSystem;
	private InterpolationSystem interpolationSystem;
	
	
	public TitleScreen(Game g, World w, OrthographicCamera c) {
		this.game = g;
		this.world = w;
		
		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();
		
		this.camera = c;

		interpolationSystem = 	world.setSystem(new InterpolationSystem(), true);
		renderSystem 		= 	world.setSystem(new TitleRenderSystem(camera), true);
		world.setSystem(new ScreenChangeSystem(game, world, camera));

		world.setSystem(new TitleInputSystem());
		
		world.initialize();
		world.setDelta(dt);
		
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.x = -8f;
		position.y = 0;
		e.addComponent(position);
		
		Sprite sprite = new Sprite();
		sprite.name = "car";
		sprite.r = 255/255f;
		sprite.g = 255/255f;
		sprite.b = 255/255f;
		sprite.a = 0/255f;
		sprite.scaleX = 16f;
		sprite.scaleY = 9f;
		sprite.layer = Sprite.Layer.BACKGROUND;
		e.addComponent(sprite);
		
		ScreenChange change = new ScreenChange();
		change.change = false;
		e.addComponent(change);
		
		e.addToWorld();
		
		world.getManager(GroupManager.class).add(e, Constants.Groups.SCREEN_CHANGE);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		if(delta > 0.45f)
			delta = 0.45f;
		
		accum += delta;
		
		while(accum >= dt){
			world.process();
			accum -= dt;
			renderSystem.addTime(dt);
		}
		
		interpolationSystem.alpha = accum/dt;
		interpolationSystem.process();
		
		renderSystem.process();
	}
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
