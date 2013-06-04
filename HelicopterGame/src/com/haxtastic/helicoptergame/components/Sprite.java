package com.haxtastic.helicoptergame.components;

import com.artemis.Component;

public class Sprite extends Component {
	public enum Layer {
		DEFAULT,
		BACKGROUND,
		ACTORS_1,
		ACTORS_2,
		ACTORS_3,
		PARTICLES;
		
		public int getLayerId() {
			return ordinal();
		}
	}
	
	public String name;
	public float scaleX = 1;
	public float scaleY = 1;
	public float rotation;
	public float r = 1;
	public float g = 1;
	public float b = 1;
	public float a = 1;
	public Layer layer = Layer.DEFAULT;
}
