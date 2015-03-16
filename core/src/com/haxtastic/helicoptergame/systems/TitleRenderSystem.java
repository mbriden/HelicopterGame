package com.haxtastic.helicoptergame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.haxtastic.helicoptergame.Constants;
import com.haxtastic.helicoptergame.components.Position;
import com.haxtastic.helicoptergame.components.ScreenChange;
import com.haxtastic.helicoptergame.components.Sprite;

public class TitleRenderSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> pm;
	@Mapper	ComponentMapper<Sprite> sm;
	@Mapper	ComponentMapper<ScreenChange> scm;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	TextureRegion region;
	float time, fadeTime;
	float fadeStep = 0.008f;
	boolean fade;
	
	@SuppressWarnings("unchecked")
	public TitleRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Position.class, Sprite.class));
		this.camera = camera;
	}
	
	@Override
	protected void initialize() {
		Texture texture = new Texture(Gdx.files.internal("textures/titlescreen.png"));
		region = new TextureRegion(texture, 0, 0, 1280, 720);
		region.flip(false, false);
		batch = new SpriteBatch();
		time = 0;
		fadeTime = 0.008f;
		fade = true;
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

	protected void process(Entity e) {
		Position position = pm.getSafe(e);
		Sprite sprite = sm.get(e);
		batch.setColor(sprite.r, sprite.g, sprite.b, sprite.a);
		
		float posX = position.x * Constants.PIXELS_PER_METER_X;// - (spriteRegion.getRegionWidth() / 2 * sprite.scaleX);
		float posY = position.y * Constants.PIXELS_PER_METER_Y;// - (spriteRegion.getRegionHeight() / 2 * sprite.scaleX);
		batch.draw(region, posX, posY, 0, 0, (sprite.scaleX * Constants.PIXELS_PER_METER_X), (sprite.scaleY * Constants.PIXELS_PER_METER_Y), 1, 1, position.r * MathUtils.radiansToDegrees);
		// GdxUtils.drawCentered(batch, spriteRegion, position.x, position.y);
		if(sprite.a <= 0/255f && !fade){
			scm.get(e).change = true;
		}else if(time > fadeTime && sprite.a < 255/255f && fade) {
			if(sprite.a + 1.5/255f <= 255/255f)
				sprite.a += 1.5/255f;
			else
				sprite.a = 255/255f;
			fadeTime += fadeStep;
		}else if(sprite.a == 255/255f && fade){
			time = 0;
			fadeTime = fadeStep;
			fade = false;
		}else if(time > fadeTime && sprite.a > 0/255f && !fade){
			if(sprite.a - 3/255f >= 0/255f)
				sprite.a -= 3/255f;
			else
				sprite.a = 0;
			fadeTime += fadeStep;
		}
			
	}
	
	public void addTime(float t){
		time += t;
	}

	protected void end() {
		batch.end();
	}

}
