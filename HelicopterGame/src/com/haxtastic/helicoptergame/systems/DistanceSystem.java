package com.haxtastic.helicoptergame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.haxtastic.helicoptergame.Constants;
import com.haxtastic.helicoptergame.components.Distance;
import com.haxtastic.helicoptergame.components.Player;
import com.haxtastic.helicoptergame.components.Position;

public class DistanceSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Player> plm;

	@SuppressWarnings("unchecked")
	public DistanceSystem() {
		super(Aspect.getAspectForAll(Player.class));
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
		Position pos = pm.get(e);
		Distance dist = world.getManager(GroupManager.class).getEntities(Constants.Groups.DISTANCE).get(0).getComponent(Distance.class);
		Player player = plm.get(e);
		dist.curDistance = Math.round((pos.x - 2)*10.0f)/10.0f;
		if(dist.curDistance > dist.bestDistance)
			dist.bestDistance = dist.curDistance;
		if(player.restart)
			dist.prevDistance = dist.curDistance;
	}

	protected void end() {
	}
}
