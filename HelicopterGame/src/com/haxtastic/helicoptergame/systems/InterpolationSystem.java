package com.haxtastic.helicoptergame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.haxtastic.helicoptergame.Constants;
import com.haxtastic.helicoptergame.components.Player;
import com.haxtastic.helicoptergame.components.Position;

public class InterpolationSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<Position> pm;
	
	public float alpha;

	@SuppressWarnings("unchecked")
	public InterpolationSystem() {
		super(Aspect.getAspectForAll(Position.class, Player.class));
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
		Position position = pm.get(e);
		position.px = position.x; position.py = position.y;
		position.x = position.x*alpha + position.px * (1.0f - alpha);
		position.y = position.y*alpha + position.py * (1.0f - alpha);
	}

	protected void end() {
	}
}
