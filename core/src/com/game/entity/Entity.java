package com.game.entity;

public class Entity {
	float hp;
	float atk;
	float x , y;
	public Entity(float x , float y) ///create invulnarable object
	{
		this.x = x;
		this.y = y;
		hp = -1;
		atk = 0;
	}
	public Entity(float x , float y , float hp, float atk) {
		this.x=x ;
		this.y=y;
		this.hp = hp;
		this.atk = atk;
	}
	
}
