package com.haxtastic.helicoptergame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.haxtastic.helicoptergame.Constants;
import com.haxtastic.helicoptergame.components.Actor;
import com.haxtastic.helicoptergame.components.AnimationSprite;
import com.haxtastic.helicoptergame.components.Distance;
import com.haxtastic.helicoptergame.components.Player;
import com.haxtastic.helicoptergame.components.Position;
import com.haxtastic.helicoptergame.components.Velocity;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {
	
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<Actor> am;
	@Mapper ComponentMapper<Player> plm;
	@Mapper ComponentMapper<AnimationSprite> sm;
	
	private boolean jump, jumpPress, startPress, restartPress = false;
	public float time, pressTime = 0;
	//private float time, prevTime, resetTime;
	
	@SuppressWarnings("unchecked")
	public PlayerInputSystem() {
		super(Aspect.getAspectForAll(Velocity.class, Player.class, Actor.class));
		//this.camera = camera;
		//time = 0;
		//resetTime = 0;
		//prevTime = System.nanoTime();
	}
	
	@Override
	protected void initialize() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	protected void process(Entity e) {
		Vector2 velocity = vm.get(e).velocity;
		Body actor = am.get(e).actor;
		Player player = plm.get(e);
		AnimationSprite sprite = sm.get(e);
		time += world.delta;
		
		float yVel = 0;
		velocity.x = 6.5f;
		yVel = actor.getLinearVelocity().y;
		actor.setLinearVelocity(velocity.x, yVel);
		if(startPress && player.alive){
			player.started = true;
			startPress = false;
			jump = true;
			sprite.active = true;
		}else if(restartPress){
			player.restart = true;
			restartPress = false;
		}else if(jump && player.alive){
			velocity.y += 0.35f;
		}
		if(!player.alive)
			jump = false;
		/*if(time - pressTime > 5 && jumpPress){
			Preferences prefs = Gdx.app.getPreferences("flygaviltspelet");
			prefs.putFloat("best", 0.0f);
			prefs.flush();
			world.getManager(GroupManager.class).getEntities(Constants.Groups.DISTANCE).get(0).getComponent(Distance.class).bestDistance = 0.0f;
			world.getManager(GroupManager.class).getEntities(Constants.Groups.DISTANCE).get(0).getComponent(Distance.class).curDistance = 0.0f;
		}*/
	}

	@Override
	public boolean keyDown(int keycode) {
		Player player = world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Player.class);
		if(keycode == Input.Keys.SPACE){
			if(player.started)
				jump = true;
			else if(!player.started && player.alive){
				startPress = true;
			}else{
				restartPress = true;
			}
			
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		Player player = world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Player.class);
		if(keycode == Input.Keys.SPACE){
			if(player.started)
				jump = false;
			else if(!player.started && player.alive){
				startPress = false;
			}else{
				restartPress = false;
			}			
		}
		
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		Player player = world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Player.class);
		if(player.started && player.alive){
			jump = true;
			pressTime = time;
			jumpPress = true;
		}
		else if(!player.started && player.alive){
			startPress = true;
		}else{
			restartPress = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		Player player = world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Player.class);
		if(player.started && player.alive){
			jump = false;
			pressTime = 0;
			jumpPress = false;
		}
		else if(!player.started && player.alive){
			startPress = false;
			jumpPress = false;
		}else{
			restartPress = false;
			jumpPress = false;
		}
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
