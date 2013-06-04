package com.haxtastic.helicoptergame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.haxtastic.helicoptergame.Constants;
import com.haxtastic.helicoptergame.EntityFactory;
import com.haxtastic.helicoptergame.components.Actor;
import com.haxtastic.helicoptergame.components.Floating;
import com.haxtastic.helicoptergame.components.Offset;
import com.haxtastic.helicoptergame.components.Player;
import com.haxtastic.helicoptergame.components.Position;

public class WallSystem extends IntervalEntityProcessingSystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Actor> am;
	@Mapper ComponentMapper<Offset> om;
	
	SimulationSystem sim;

	@SuppressWarnings("unchecked")
	public WallSystem(SimulationSystem s) {
		super(Aspect.getAspectForAll(Position.class, Actor.class, Offset.class, Floating.class), 0.5f);
		sim = s;
	}
	
	@Override
	public void initialize() {
		for(int i = 2; i < 6; i++){
			float offset = MathUtils.random(-1.0f, 1.5f);
			EntityFactory.createFloatingWall(world, sim, (8*i)*0.75f, 3.5f, offset).addToWorld();
		}
	}

	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		Body actor = am.get(e).actor;
		float offset = om.get(e).offset;
		Position playerPos = world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Position.class);
		if(position.x < playerPos.x - 3.0f) {
			sim.simulation.destroyBody(actor);
			actor.setUserData(null);
			actor = null;
			e.deleteFromWorld();
			float newOffset = MathUtils.random(-1.0f, 1.5f);
			EntityFactory.createFloatingWall(world, sim, position.x+(32)*0.75f, position.y - offset, newOffset).addToWorld();
		}
	}
	
	@Override
	protected boolean checkProcessing() {
		if(!world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Player.class).restart)
			return true;
		else
			return false;
	}

}
