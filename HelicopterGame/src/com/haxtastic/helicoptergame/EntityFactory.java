package com.haxtastic.helicoptergame;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.haxtastic.helicoptergame.systems.SimulationSystem;
import com.haxtastic.helicoptergame.components.AnimationSprite;
import com.haxtastic.helicoptergame.components.Floating;
import com.haxtastic.helicoptergame.components.Offset;
import com.haxtastic.helicoptergame.components.Player;
import com.haxtastic.helicoptergame.components.Position;
import com.haxtastic.helicoptergame.components.Sprite;
import com.haxtastic.helicoptergame.components.Velocity;
import com.haxtastic.helicoptergame.components.Actor;

public class EntityFactory {
	
	static BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("fixtures/car.json"));
	
	public static void cleanWorld(World world){
		ImmutableBag<EntitySystem> systems = world.getSystems();
		for(int i = 0; systems.size() > i; i++) {
			world.deleteSystem(systems.get(i));
		}
	}
	
	public static Entity createPlayer(World world, SimulationSystem sim, float x, float y) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.x = x;
		position.y = y;
		e.addComponent(position);
		
		AnimationSprite sprite = new AnimationSprite();
		sprite.name = "helicopter";
		sprite.width = 256;
		sprite.height = 128;
		sprite.cols = 2;
		sprite.rows = 3;
		sprite.frameTime = 0.075f;
		sprite.r = 255/255f;
		sprite.g = 255/255f;
		sprite.b = 255/255f;
		sprite.a = 255/255f;
		sprite.scaleX = 1.0f;
		sprite.scaleY = 0.5f;
		sprite.loop = true;
		sprite.layer = AnimationSprite.Layer.ACTORS_3;
		e.addComponent(sprite);
		
		Velocity velocity = new Velocity();
		velocity.velocity = new Vector2(0, 0);
		e.addComponent(velocity);
		
        FixtureDef fd = new FixtureDef();
        fd.density = 1.5f;
        fd.friction = 0.5f;
        
        Actor actor = new Actor(sim.simulation, x, y, BodyType.DynamicBody);
        actor.name = "Player";
        //actor.actor.setTransform(actor.actor.getPosition(), -90.0f * MathUtils.degreesToRadians);
        actor.actor.setFixedRotation(true);
        
        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(actor.actor, "helicopter", fd, 1f, 1);
        actor.actor.setUserData((Object)0);
        //fd.isSensor = true;
        //loader.attachFixture(actor.actor, "jumpsensor", fd, 0.33f, 3);
        
        e.addComponent(actor);
		
		Player player = new Player();
		player.alive = true;
		player.started = false;
		player.restart = false;
		e.addComponent(player);
		
		world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER_CAR);
		
		return e;
	}
	
	public static Entity createWall(World world, SimulationSystem sim, float x, float y, float of, boolean type) {
		Entity e = world.createEntity();
		
		Position position = new Position();
		position.x = x;
		position.y = y + of;
		e.addComponent(position);
		
		Offset offset = new Offset();
		offset.offset = of;
		offset.type = type;
		e.addComponent(offset);
		
		Sprite sprite = new Sprite();
		sprite.name = "wall";
		sprite.r = 255/255f;
		sprite.g = 255/255f;
		sprite.b = 255/255f;
		sprite.a = 255/255f;
		sprite.scaleX = 0.75f;
		sprite.scaleY = 5f;
		sprite.layer = Sprite.Layer.ACTORS_2;
		e.addComponent(sprite);
		
		Velocity velocity = new Velocity();
		velocity.velocity = new Vector2(0, 0);
		e.addComponent(velocity);
		
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        
        Actor actor = new Actor(sim.simulation, position.x, position.y, BodyType.StaticBody);
        actor.name = "Wall";
        actor.actor.setFixedRotation(true);
        
        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(actor.actor, "wall", fd, 0.75f, 2);
        actor.actor.setUserData((Object)0);
        
        e.addComponent(actor);
		
		world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMY_CARS);
		
		return e;
	}
	
	public static Entity createFloatingWall(World world, SimulationSystem sim, float x, float y, float of) {
		Entity e = world.createEntity();
		
		e.addComponent(new Floating());
		
		Position position = new Position();
		position.x = x;
		position.y = y + of;
		e.addComponent(position);
		
		Offset offset = new Offset();
		offset.offset = of;
		e.addComponent(offset);
		
		Sprite sprite = new Sprite();
		sprite.name = "floatwall";
		sprite.r = 255/255f;
		sprite.g = 255/255f;
		sprite.b = 255/255f;
		sprite.a = 255/255f;
		sprite.scaleX = 0.75f;
		sprite.scaleY = 1.5f;
		sprite.layer = Sprite.Layer.ACTORS_1;
		e.addComponent(sprite);
		
		Velocity velocity = new Velocity();
		velocity.velocity = new Vector2(0, 0);
		e.addComponent(velocity);
		
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        
        Actor actor = new Actor(sim.simulation, position.x, position.y, BodyType.StaticBody);
        actor.name = "FloatWall";
        actor.actor.setFixedRotation(true);
        loader.attachFixture(actor.actor, "floatwall", fd, 0.75f, 4);
        actor.actor.setUserData((Object)0);
        e.addComponent(actor);
		
		world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMY_CARS);
		
		return e;
	}

	public static Entity createExplosion(World world, float x, float y) {
		Entity e = world.createEntity();
		
		AnimationSprite sprite = new AnimationSprite();
		sprite.name = "explosion";
		sprite.width = 256;
		sprite.height = 128;
		sprite.cols = 6;
		sprite.rows = 6;
		sprite.frameTime = 0.025f;
		sprite.r = 255/255f;
		sprite.g = 255/255f;
		sprite.b = 255/255f;
		sprite.a = 255/255f;
		sprite.scaleX = 1.5f;
		sprite.scaleY = 1.5f;
		sprite.active = true;
		sprite.layer = AnimationSprite.Layer.ACTORS_3;
		e.addComponent(sprite);
		
		Position position = new Position();
		position.x = x - sprite.scaleX/2;
		position.y = y - sprite.scaleY/2;
		e.addComponent(position);
		
		//e.addComponent(new ParallaxStar());
		
		return e;
	}
}
