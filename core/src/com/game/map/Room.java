package com.game.map;

import com.badlogic.gdx.math.Vector2;

public class Room {
	public Vector2 bottomLeft , size;
	public Room() {
		bottomLeft = new Vector2(0,0);
		size = new Vector2(bottomLeft);
	}
	public Room(float x , float y , float w, float d){
		bottomLeft = new Vector2();
		size = new Vector2();
		bottomLeft.x = x;
		bottomLeft.y = y;
		size.x = w;
		size.y = d;
	}
	public Room(Vector2 pos , Vector2 sz) {
		bottomLeft = pos;
		size = sz;
	}
	public boolean inRoom(Vector2 target) {
		float x = target.x - bottomLeft.x;
		float y = target.y - bottomLeft.y;
		if(0 <= x && x <= size.x && 0 <= y && y <= size.y) return true;
		else return false;
	}
	public boolean inRoom(float p , float q ) {
		float x = p - bottomLeft.x;
		float y = q - bottomLeft.y;
		if(0 <= x && x <= size.x && 0 <= y && y <= size.y) return true;
		else return false;
	}
}
