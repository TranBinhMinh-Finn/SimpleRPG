package com.game.entity;

public class Entity {
	float hp;
	float atk;
	public Entity() ///create invulnarable object
	{
		hp = -1;
		atk = 0;
	}
	public Entity(float hp, float atk) {
		this.hp = hp;
		this.atk = atk;
	}
	
}
