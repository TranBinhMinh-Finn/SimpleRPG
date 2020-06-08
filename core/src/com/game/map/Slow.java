package com.game.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.utils.CreateBody;

public class Slow {
	public Vector2 bottomLeft , size;
	Body body;
	public int enemyCount;
	public Slow(Shape shape, Vector2 bottomLeft, Vector2 size, World world)
	{
		this.body = CreateBody.create(shape, BodyDef.BodyType.StaticBody, world, (short) 0 ,(short) 0, (short)0, this);
		this.bottomLeft = new Vector2(bottomLeft);
		this.size = new Vector2(size);
	}
	
	public boolean inSlowRange(Vector2 target) {
		float x = target.x - bottomLeft.x;
		float y = target.y - bottomLeft.y;
		if(0 <= x && x <= size.x && 0 <= y && y <= size.y) 
			return true;
		else return false;
	}
	public boolean inSlowRange(float p , float q ) {
		float x = p - bottomLeft.x;
		float y = q - bottomLeft.y;
		if(0 <= x && x <= size.x && 0 <= y && y <= size.y) return true;
		else return false;
	}
}
