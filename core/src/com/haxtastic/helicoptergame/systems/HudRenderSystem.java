package com.haxtastic.helicoptergame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haxtastic.helicoptergame.HelicopterGame;
import com.haxtastic.helicoptergame.Constants;
import com.haxtastic.helicoptergame.components.Distance;
import com.haxtastic.helicoptergame.components.Player;
import com.haxtastic.helicoptergame.components.Position;
import com.haxtastic.helicoptergame.components.Sprite;

public class HudRenderSystem extends VoidEntitySystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Sprite> sm;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private BitmapFont font;
	
	public HudRenderSystem(OrthographicCamera camera) {
		this.camera = camera;
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
	}
	
	@Override
	protected void initialize() {
		batch = new SpriteBatch();
		
		Texture fontTexture = new Texture(Gdx.files.internal("fonts/hud_0.png"));
		fontTexture.setFilter(TextureFilter.Linear, TextureFilter.MipMapLinearLinear);
		TextureRegion fontRegion = new TextureRegion(fontTexture);
		font = new BitmapFont(Gdx.files.internal("fonts/hud.fnt"), fontRegion, false);
		font.setUseIntegerPositions(false);
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}

	@Override
	protected void processSystem() {
		//Position pos = world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Position.class);
		Distance dist = world.getManager(GroupManager.class).getEntities(Constants.Groups.DISTANCE).get(0).getComponent(Distance.class);
		batch.setColor(1, 1, 1, 1);
		if(!world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Player.class).alive){
			String press = null;
			String msg = null;
			if(Gdx.app.getType() == ApplicationType.Android)
				press = "Touch";
			else
				press = "Press";
			msg = press + " to restart";
			font.draw(batch, msg, HelicopterGame.FRAME_WIDTH/2-(font.getBounds(msg).width/2), HelicopterGame.FRAME_HEIGHT/2);
			msg = "You flew for " + dist.curDistance + " meters before you crashed.";
			font.draw(batch, msg, HelicopterGame.FRAME_WIDTH/2-(font.getBounds(msg).width/2), (HelicopterGame.FRAME_HEIGHT/2) + (font.getBounds(msg).height) + 8);
		}else if(!world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Player.class).started){
			String press = null;
			String msg = null;
			if(Gdx.app.getType() == ApplicationType.Android)
				press = "Touch";
			else
				press = "Press";
			msg = press + " to start";
			font.draw(batch, msg,HelicopterGame.FRAME_WIDTH/2-(font.getBounds(msg).width/2), HelicopterGame.FRAME_HEIGHT/2 + (font.getBounds(msg).height) + 8);
			msg = "Try to not get hit by the blue things";
			font.draw(batch, msg, HelicopterGame.FRAME_WIDTH/2-(font.getBounds(msg).width/2), HelicopterGame.FRAME_HEIGHT/2);
			msg = press + " to accelerate up, release to go down";
			font.draw(batch, msg, HelicopterGame.FRAME_WIDTH/2-(font.getBounds(msg).width/2), HelicopterGame.FRAME_HEIGHT/2 - (font.getBounds(msg).height) - 8);
		}
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, HelicopterGame.FRAME_HEIGHT - (font.getBounds("F").height) - 8);
		font.draw(batch, "Previous Distance:  " + dist.prevDistance + " Meters", 20, HelicopterGame.FRAME_HEIGHT - (font.getBounds("F").height*2) - 16);
		font.draw(batch, "Record Distance:     " + dist.bestDistance + " Meters", 20, HelicopterGame.FRAME_HEIGHT - (font.getBounds("F").height*3) - 24);
		font.draw(batch, "Current Distance:   " + dist.curDistance + " Meters", 20, HelicopterGame.FRAME_HEIGHT - (font.getBounds("F").height*4) - 32);
		font.draw(batch, "Haxtastic gaming 2013 by mAgz", 20, 30);
		
		/*
		 * 		//Position pos = world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Position.class);
		Distance dist = world.getManager(GroupManager.class).getEntities(Constants.Groups.DISTANCE).get(0).getComponent(Distance.class);
		batch.setColor(1, 1, 1, 1);
		if(!world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Player.class).alive){
			String msg = null;
			if(Gdx.app.getType() == ApplicationType.Android)
				msg = "Touch to restart";
			else
				msg = "Press to restart";
			font.draw(batch, "You flew for " + dist.curDistance + " meters before you crashed.", -280, (helicoptergame.FRAME_HEIGHT/2)+20);
			//-(helicoptergame.FRAME_WIDTH / 6)+35
			font.draw(batch, msg, -110, helicoptergame.FRAME_HEIGHT/2);
		}else if(!world.getManager(GroupManager.class).getEntities(Constants.Groups.PLAYER_CAR).get(0).getComponent(Player.class).started){
			String msg = null;
			if(Gdx.app.getType() == ApplicationType.Android)
				msg = "Touch to start";
			else
				msg = "Press to start";
			font.draw(batch, msg, -110, helicoptergame.FRAME_HEIGHT/2);
		}
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), -(helicoptergame.FRAME_WIDTH / 2) + 20, helicoptergame.FRAME_HEIGHT - 20);
		/*font.draw(batch, "Active entities: " + world.getEntityManager().getActiveEntityCount(), -(helicoptergame.FRAME_WIDTH / 2) + 20, helicoptergame.FRAME_HEIGHT - 40);
		font.draw(batch, "Total created: " + world.getEntityManager().getTotalCreated(), -(helicoptergame.FRAME_WIDTH / 2) + 20, helicoptergame.FRAME_HEIGHT - 60);
		font.draw(batch, "Total deleted: " + world.getEntityManager().getTotalDeleted(), -(helicoptergame.FRAME_WIDTH / 2) + 20, helicoptergame.FRAME_HEIGHT - 80);
		font.draw(batch, "Previous Distance: " + dist.prevDistance + " Meters", -(helicoptergame.FRAME_WIDTH / 2) + 20, helicoptergame.FRAME_HEIGHT - 40);
		font.draw(batch, "Record Distance: " + dist.bestDistance + " Meters", -(helicoptergame.FRAME_WIDTH / 2) + 20, helicoptergame.FRAME_HEIGHT - 60);
		font.draw(batch, "Current Distance: " + dist.curDistance + " Meters", -(helicoptergame.FRAME_WIDTH / 2) + 20, helicoptergame.FRAME_HEIGHT - 80);
		font.draw(batch, "Haxtastic gaming 2013 by mAgz", -(helicoptergame.FRAME_WIDTH / 2) + 20, 20);
		*/
	}
	
	@Override
	protected void end() {
		batch.end();
		camera.update();
	}
	
}
