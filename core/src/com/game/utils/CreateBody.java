package com.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class CreateBody {
	public static Body create(float x, float y,float w,float h, BodyType bodyType, String shapeType, World world, short cBits, short mBits, short gIndex, Object object) 
	{
		Body body;
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(new Vector2(x,y));
		bodyDef.type = bodyType;
		body = world.createBody(bodyDef);
		body.setFixedRotation(true);
		if(shapeType == "Box")
		{
			PolygonShape box;
			FixtureDef fixtureDef;
			box = new PolygonShape();
			box.setAsBox(w, h);
			
			fixtureDef = new FixtureDef();
			fixtureDef.shape = box;
			fixtureDef.density = 10.0f;
			fixtureDef.friction = 0.0f;
			
			fixtureDef.filter.categoryBits = cBits; // is a kind of
			fixtureDef.filter.maskBits = mBits; // collides with
			fixtureDef.filter.groupIndex = gIndex; 
			
			body.createFixture(fixtureDef).setUserData(object);
			return body;
		}
		return null;
	}
	public static Body create(Shape shape,BodyType bodyType,World world,short cBits, short mBits, short gIndex, Object object)
	{
		BodyDef bodyDef = new BodyDef();
	 	bodyDef.type = bodyType;
	 	Body body = world.createBody(bodyDef);
	 	FixtureDef fixtureDef = new FixtureDef();
	 	fixtureDef.shape = shape;
	 	fixtureDef.density = 10.0f;
		fixtureDef.friction = 0.0f;
		
		fixtureDef.filter.categoryBits = cBits; // is a kind of
		fixtureDef.filter.maskBits = mBits; // collides with
		fixtureDef.filter.groupIndex = gIndex; 
	 	body.createFixture(fixtureDef).setUserData(object);
		return null;
	}
}
