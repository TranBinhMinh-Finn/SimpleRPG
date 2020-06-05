package com.game.map;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.utils.Constants;
import com.game.utils.CreateBody;

public class Wall {
	Body body;
	public Wall()
	{
		
	}
	public Wall(Shape shape,World world)
	{
		this.body = CreateBody.create(shape, BodyDef.BodyType.StaticBody, world, Constants.BIT_WALL,(short) (Constants.BIT_BULLET | Constants.BIT_ENEMY | Constants.BIT_PLAYER), (short)0, this);
	}
}
