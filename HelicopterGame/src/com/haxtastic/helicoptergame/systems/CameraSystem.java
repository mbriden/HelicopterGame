package com.haxtastic.helicoptergame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.haxtastic.helicoptergame.Constants;
import com.haxtastic.helicoptergame.components.Player;
import com.haxtastic.helicoptergame.components.Position;

public class CameraSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> pm;
	
	OrthographicCamera camera;
	float cameraOffset;
	
	@SuppressWarnings("unchecked")
	public CameraSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Player.class));
		this.camera = camera;
		cameraOffset = 6f;
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void process(Entity e) {
		Position pos = pm.get(e);
		camera.translate(((pos.x + cameraOffset) * Constants.PIXELS_PER_METER_X) - camera.position.x, 0);
		camera.update();
	}

}
