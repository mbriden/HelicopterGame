package com.haxtastic.helicoptergame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.haxtastic.helicoptergame.EntityFactory;
import com.haxtastic.helicoptergame.components.Actor;
import com.haxtastic.helicoptergame.components.AnimationSprite;
import com.haxtastic.helicoptergame.components.Player;
import com.haxtastic.helicoptergame.components.Position;

public class CollisionSystem extends EntityProcessingSystem implements ContactListener {
	@Mapper ComponentMapper<Actor> am;
	@Mapper ComponentMapper<Player> pm;
	@Mapper ComponentMapper<AnimationSprite> sm;
	@Mapper ComponentMapper<Position> posm;
	private float time = 0;

	@SuppressWarnings("unchecked")
	public CollisionSystem() {
		super(Aspect.getAspectForAll(Player.class));
	}
	
	public void process(Entity e){
		Body actor = am.get(e).actor;
		Player player = pm.get(e);
		AnimationSprite sprite = sm.get(e);
		Position pos = posm.get(e);
		time += world.delta;
		Integer collision = (Integer)actor.getUserData();
		try{
			if(player.alive && ((collision & 2) != 0 || (collision & 4) != 0)){
				player.alive = false;
				player.started = false;
				player.resetTime = time;
				sprite.active = false;
				EntityFactory.createExplosion(world, pos.x + (sprite.scaleX/2), pos.y + (sprite.scaleY/2)).addToWorld();
			}
		} catch(NullPointerException ex){
			System.out.println(ex);
		}
	}

	@Override
	public void beginContact(Contact contact){
		Fixture contactFixtureA = contact.getFixtureA();
		Fixture contactFixtureB = contact.getFixtureB();
		
		Object ob = (Integer)contactFixtureA.getBody().getUserData() | (Integer)contactFixtureB.getUserData();
		contactFixtureA.getBody().setUserData(ob);
		
		ob = (Integer)contactFixtureB.getBody().getUserData() | (Integer)contactFixtureA.getUserData();
		contactFixtureB.getBody().setUserData(ob);
	}
	
	public void endContact(Contact contact){
		Fixture contactFixtureA = contact.getFixtureA();
		Fixture contactFixtureB = contact.getFixtureB();
		
		Object ob = (Integer)contactFixtureA.getBody().getUserData() & ~(Integer)contactFixtureB.getUserData();
		contactFixtureA.getBody().setUserData(ob);

		ob = (Integer)contactFixtureB.getBody().getUserData() & ~(Integer)contactFixtureA.getUserData();
		contactFixtureB.getBody().setUserData(ob);
	}
	
	public void preSolve(Contact contact, Manifold manifold){
	}
	
	public void postSolve(Contact contact, ContactImpulse impulse){
	}
}
