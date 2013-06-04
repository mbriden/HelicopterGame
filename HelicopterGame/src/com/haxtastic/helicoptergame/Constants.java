package com.haxtastic.helicoptergame;

public class Constants {
	
	public class Groups {
		public static final String PLAYER_CAR = "player car";
		public static final String ENEMY_CARS = "enemy cars";
		public static final String SCREEN_CHANGE = "screen change";
		public static final String DISTANCE = "distance";
	}
	
	public static float PIXELS_PER_METER_X;
	public static float PIXELS_PER_METER_Y;
	
	public class Layers {
		public static final int PLAYER = (1 << 0);
		public static final int ENEMY = (1 << 1);
		public static final int WALL = (1 << 2);
	}

}
