package com.haxtastic.helicoptergame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.haxtastic.helicoptergame.Constants;
import com.haxtastic.helicoptergame.components.AnimationSprite;
import com.haxtastic.helicoptergame.components.Player;
import com.haxtastic.helicoptergame.components.Position;
import com.haxtastic.helicoptergame.components.Sprite;

public class InterpolationSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Sprite> sm;
	@Mapper ComponentMapper<AnimationSprite> am;
	
	public float alpha;

	@SuppressWarnings("unchecked")
	public InterpolationSystem() {
		super(Aspect.getAspectForAll(Position.class).one(Sprite.class, AnimationSprite.class));
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
		float x = (position.x*alpha) + (position.px * (1.0f - alpha));
		float y = (position.y*alpha) + (position.py * (1.0f - alpha));
		if(sm.has(e)) {
			sm.get(e).setPosition(x, y);
		} else if(am.has(e)) {
			am.get(e).setPosition(x, y);;
		}
	}
	
	protected void end() {
	}
}
