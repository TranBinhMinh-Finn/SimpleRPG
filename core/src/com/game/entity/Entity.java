package com.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Entity {
	
	public static final int BOX2D_SCALE = 10;
	public Body body;
	float frameWidth;
	float frameHeight;
	float box2DWidth, box2DHeight;
	public Entity(float x, float y, float pixelWidth, float pixelHeight, BodyType bodyType, String shapeType, World world) 
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(new Vector2((x+pixelWidth/2f)/BOX2D_SCALE,(y+pixelHeight/2f)/BOX2D_SCALE) );
		bodyDef.type = bodyType;
		this.body = world.createBody(bodyDef);
		this.frameWidth = pixelWidth;
		this.frameHeight = pixelHeight;
		if(shapeType == "Box")
		{
			PolygonShape box;
			FixtureDef fixtureDef;
			box = new PolygonShape();
			box2DWidth = pixelWidth/(2*BOX2D_SCALE);
			box2DHeight = pixelHeight/(2*BOX2D_SCALE);
			box.setAsBox(box2DWidth, box2DHeight);
			fixtureDef = new FixtureDef();
			fixtureDef.shape = box;
			fixtureDef.density = 10.0f;
			fixtureDef.friction = 0.0f;
			body.createFixture(fixtureDef);
		}
	}
	public Entity(float x, float y, float pixelWidth, float pixelHeight,float box2DWidth,float box2DHeight, BodyType bodyType, String shapeType, World world) 
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(new Vector2((x+pixelWidth/2f)/BOX2D_SCALE,(y+pixelHeight/2f)/BOX2D_SCALE) );
		bodyDef.type = bodyType;
		this.body = world.createBody(bodyDef);
		this.frameWidth = pixelWidth;
		this.frameHeight = pixelHeight;
		if(shapeType == "Box")
		{
			PolygonShape box;
			FixtureDef fixtureDef;
			box = new PolygonShape();
			this.box2DWidth = box2DWidth/(2*BOX2D_SCALE);
			this.box2DHeight = box2DHeight/(2*BOX2D_SCALE);
			box.setAsBox(this.box2DWidth, this.box2DHeight);
			fixtureDef = new FixtureDef();
			fixtureDef.shape = box;
			fixtureDef.density = 10.0f;
			fixtureDef.friction = 0.0f;
			body.createFixture(fixtureDef);
		}
	}
	public float getXByPixels()
	{
		return (body.getPosition().x - box2DWidth)*BOX2D_SCALE;
	}
	
	public float getYByPixels()
	{
		return (body.getPosition().y - box2DHeight)*BOX2D_SCALE;
	}
	public float getXByCenter()
	{
		return body.getPosition().x * BOX2D_SCALE;
	}
	public float getYByCenter()
	{
		return body.getPosition().y * BOX2D_SCALE;
	}
}
