package com.haxtastic.helicoptergame.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;



public class Actor extends Component {
	public Body actor;
	public String name;
	
	public Actor(World world, BodyType bt){
        BodyDef bd = new BodyDef();
        bd.type = bt;

        actor = world.createBody(bd);
	}
	
	public Actor(World world, float x, float y, BodyType bt){
        BodyDef bd = new BodyDef();
        bd.type = bt;
        bd.position.set(x, y);

        actor = world.createBody(bd);
	}
	
	public Actor(World world, Vector2 pos, BodyType bt){
        BodyDef bd = new BodyDef();
        bd.type = bt;
        bd.position.set(pos.x, pos.y);

        actor = world.createBody(bd);
	}
}
