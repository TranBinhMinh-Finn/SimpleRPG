package com.game.entity;

import com.badlogic.gdx.math.Vector2;

public class Entity {
	float hp;
	float atk;
	int x,y;
	public Entity(int x , int y) ///create invulnarable object
	{
		this.x = x;
		this.y = y;
		hp = -1;
		atk = 0;
	}
	public Entity(int x , int y , float hp, float atk) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.atk = atk;
	}
	
}
