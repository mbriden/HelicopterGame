package com.haxtastic.helicoptergame.systems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.haxtastic.helicoptergame.Constants;
import com.haxtastic.helicoptergame.components.AnimationSprite;
import com.haxtastic.helicoptergame.components.Position;
import com.haxtastic.helicoptergame.components.Sprite;

public class AnimationRenderSystem extends EntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<AnimationSprite> sm;

	private HashMap<String, AtlasRegion> regions;
	private TextureAtlas textureAtlas;
	private TextureRegion texture;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private float stateTime;

	private Bag<AtlasRegion> regionsByEntity;
	private List<Entity> sortedEntities;
	
	@SuppressWarnings("unchecked")
	public AnimationRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Position.class, AnimationSprite.class));
		this.camera = camera;
	}

	@Override
	protected void initialize() {
		regions = new HashMap<String, AtlasRegion>();
		textureAtlas = new TextureAtlas(Gdx.files.internal("textures/animations.atlas"));
		for (AtlasRegion r : textureAtlas.getRegions())
			regions.put(r.name, r);
		regionsByEntity = new Bag<AtlasRegion>();

		batch = new SpriteBatch();
		
		sortedEntities = new ArrayList<Entity>();
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

	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for(int i = 0; sortedEntities.size() > i; i++) {
			process(sortedEntities.get(i));
		}
	}

	protected void process(Entity e) {
		if(pm.has(e)) {
			Position position = pm.getSafe(e);
			AnimationSprite sprite = sm.get(e);
	
			//AtlasRegion spriteRegion = regionsByEntity.get(e.getId());
			batch.setColor(sprite.r, sprite.g, sprite.b, sprite.a);
			
			float posX = position.x * Constants.PIXELS_PER_METER_X;// - (spriteRegion.getRegionWidth() / 2 * sprite.scaleX);
			float posY = position.y * Constants.PIXELS_PER_METER_Y;// - (spriteRegion.getRegionHeight() / 2 * sprite.scaleX);
			if(sprite.active)
				texture = sprite.animation.getKeyFrame(sprite.time, sprite.loop);
			else
				texture = sprite.animation.getKeyFrame(0, sprite.loop);
			batch.draw(texture, posX, posY, 0, 0, (sprite.scaleX * Constants.PIXELS_PER_METER_X), (sprite.scaleY * Constants.PIXELS_PER_METER_Y), 1, 1, position.r * MathUtils.radiansToDegrees);
			// GdxUtils.drawCentered(batch, spriteRegion, position.x, position.y);
			sprite.time += Gdx.graphics.getDeltaTime();
		}
	}

	protected void end() {
		batch.end();
	}

	@Override
	protected void inserted(Entity e) {
		AnimationSprite sprite = sm.get(e);
		//regionsByEntity.set(e.getId(), regions.get(sprite.name));
		AtlasRegion texture = textureAtlas.findRegion(sprite.name);//regionsByEntity.get(e.getId());
		TextureRegion[][] tmp = texture.split(texture.packedWidth / 
				sprite.cols, texture.packedHeight / sprite.rows);
		
		TextureRegion[] walkFrames = new TextureRegion[sprite.cols * sprite.rows];
        int index = 0;
        for (int i = 0; i < sprite.rows; i++) {
                for (int j = 0; j < sprite.cols; j++) {
                        walkFrames[index++] = tmp[i][j];
                }
        }
        sprite.animation = new Animation(sprite.frameTime, walkFrames);


		sortedEntities.add(e);
		
		Collections.sort(sortedEntities, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				AnimationSprite s1 = sm.get(e1);
				AnimationSprite s2 = sm.get(e2);
				return s1.layer.compareTo(s2.layer);
			}
		});
	}
	
	public void addTime(float t){
		stateTime += t;
	}

	@Override
	protected void removed(Entity e) {
		regionsByEntity.set(e.getId(), null);
		sortedEntities.remove(e);
	}

}
