package com.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.Constants;

public class Entity {
	
	public Body body;
	protected float frameWidth;
	protected float frameHeight;
	protected float box2DWidth, box2DHeight;
	public boolean remove;
	public Entity(float x, float y, float pixelWidth, float pixelHeight, BodyType bodyType, String shapeType, World world, short cBits, short mBits, short gIndex) 
	{
		remove = false;
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(new Vector2((x+pixelWidth/2f)/Constants.BOX2D_SCALE,(y+pixelHeight/2f)/Constants.BOX2D_SCALE) );
		bodyDef.type = bodyType;
		this.body = world.createBody(bodyDef);
		this.body.setFixedRotation(true);
		this.frameWidth = pixelWidth;
		this.frameHeight = pixelHeight;
		if(shapeType == "Box")
		{
			PolygonShape box;
			FixtureDef fixtureDef;
			box = new PolygonShape();
			box2DWidth = pixelWidth/(2*Constants.BOX2D_SCALE);
			box2DHeight = pixelHeight/(2*Constants.BOX2D_SCALE);
			box.setAsBox(box2DWidth, box2DHeight);
			
			fixtureDef = new FixtureDef();
			fixtureDef.shape = box;
			fixtureDef.density = 10.0f;
			fixtureDef.friction = 0.0f;
			
			fixtureDef.filter.categoryBits = cBits; // is a kind of
			fixtureDef.filter.maskBits = mBits; // collides with
			fixtureDef.filter.groupIndex = gIndex; 
			
			body.createFixture(fixtureDef).setUserData(this);
		}
	}
	public Entity(float x, float y, float pixelWidth, float pixelHeight,float box2DWidth,float box2DHeight, BodyType bodyType, String shapeType, World world, short cBits, short mBits, short gIndex) 
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(new Vector2((x+pixelWidth/2f)/Constants.BOX2D_SCALE,(y+pixelHeight/2f)/Constants.BOX2D_SCALE) );
		bodyDef.type = bodyType;
		this.body = world.createBody(bodyDef);
		this.body.setFixedRotation(true);
		this.frameWidth = pixelWidth;
		this.frameHeight = pixelHeight;
		if(shapeType == "Box")
		{
			PolygonShape box;
			FixtureDef fixtureDef;
			
			box = new PolygonShape();
			this.box2DWidth = box2DWidth/(2*Constants.BOX2D_SCALE);
			this.box2DHeight = box2DHeight/(2*Constants.BOX2D_SCALE);
			box.setAsBox(this.box2DWidth, this.box2DHeight);
			
			fixtureDef = new FixtureDef();
			fixtureDef.shape = box;
			fixtureDef.density = 10.0f;
			fixtureDef.friction = 0.0f;
			
			fixtureDef.filter.categoryBits = cBits; // is a kind of
			fixtureDef.filter.maskBits = mBits; // collides with
			fixtureDef.filter.groupIndex = gIndex; 
			
			body.createFixture(fixtureDef).setUserData(this);;
		}
	}
	
	public void contactHandle(Entity s)
	{
		
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
