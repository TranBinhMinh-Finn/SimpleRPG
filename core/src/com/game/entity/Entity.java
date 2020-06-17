package com.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.game.utils.Constants;
import com.game.utils.CreateBody;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Entity {
	
	protected Body body;
	protected float frameWidth;
	protected float frameHeight;
	protected float box2DWidth, box2DHeight;
	public boolean remove;
	public Entity(float x, float y, float pixelWidth, float pixelHeight, BodyType bodyType,String shapeType, World world, short cBits, short mBits, short gIndex) 
	{
		remove = false;
		float X = x/Constants.BOX2D_SCALE;
		float Y = y/Constants.BOX2D_SCALE;
		this.frameWidth = pixelWidth;
		this.frameHeight = pixelHeight;
		this.box2DWidth = pixelWidth/(2*Constants.BOX2D_SCALE);
		this.box2DHeight = pixelHeight/(2*Constants.BOX2D_SCALE);
		this.body = CreateBody.create(X, Y, box2DWidth, box2DHeight, bodyType, shapeType, world, cBits, mBits, gIndex, this);
	}
	public Entity(float x, float y, float pixelWidth, float pixelHeight,float box2DWidth,float box2DHeight, BodyType bodyType, String shapeType, World world, short cBits, short mBits, short gIndex) 
	{
		float X = x/Constants.BOX2D_SCALE;
		float Y = y/Constants.BOX2D_SCALE;
		this.frameWidth = pixelWidth;
		this.frameHeight = pixelHeight;
		this.box2DWidth = box2DWidth/(2*Constants.BOX2D_SCALE);
		this.box2DHeight = box2DHeight/(2*Constants.BOX2D_SCALE);
		this.body = CreateBody.create(X, Y, this.box2DWidth, this.box2DHeight, bodyType, shapeType, world, cBits, mBits, gIndex, this);
	}
	
	public void contactHandle(Object object)
	{
		
	}
	public Body getBody()
	{
		return body;
	}
	public void setLinearVelocity(Vector2 vel)
	{
		body.setLinearVelocity(vel);
	}
	public float getXByPixels()
	{
		return (body.getPosition().x - box2DWidth)*Constants.BOX2D_SCALE;
	}
	public float getYByPixels()
	{
		return (body.getPosition().y - box2DHeight)*Constants.BOX2D_SCALE;
	}
	public float getXByCenter()
	{
		return body.getPosition().x * Constants.BOX2D_SCALE;
	}
	public float getYByCenter()
	{
		return body.getPosition().y * Constants.BOX2D_SCALE;
	}
	public void dispose()
	{
		this.body.getWorld().destroyBody(this.body);
	}
}
