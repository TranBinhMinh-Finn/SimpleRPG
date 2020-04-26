package com.game.entity;

import com.badlogic.gdx.math.Vector2;

public class Entity {
	float hp;
	float atk;
	Vector2 position;
	public Entity(float x , float y) ///create invulnarable object
	{
		position = new Vector2(x,y);
		hp = -1;
		atk = 0;
	}
	public Entity(float x , float y , float hp, float atk) {
		position.x = x;
		position.y = y;
		this.hp = hp;
		this.atk = atk;
	}
	
}
