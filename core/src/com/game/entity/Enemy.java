package com.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.utils.Constants;

public class Enemy extends Mob {
	boolean charging;
	public Enemy(float x, float y, float w, float h, World world, int hp,int atk,float speed,float animation_speed)
	{	
		super(x,y,w,h,world,hp,atk,speed,animation_speed,Constants.BIT_ENEMY, (short)(Constants.BIT_BULLET|Constants.BIT_ENEMY|Constants.BIT_PLAYER|Constants.BIT_WALL),(short)0);
	}
	public Enemy(float x, float y, float w, float h,float box2DWidth, float box2DHeight, World world, int hp,int atk,float speed,float animation_speed)
	{	
		super(x,y,w,h,box2DWidth,box2DHeight,world,hp,atk,speed,animation_speed,Constants.BIT_ENEMY,(short)(Constants.BIT_BULLET|Constants.BIT_ENEMY|Constants.BIT_PLAYER|Constants.BIT_WALL),(short)0);
	}
	void charge(float x, float y)
	{
		vel = new Vector2(x - this.getXByCenter() , y - this.getYByCenter());
		vel.setLength(speed*5);
		this.body.setLinearVelocity(vel);
		charging = true;
	}
	void move(float x, float y)
	{
		vel = new Vector2(x - this.getXByCenter() , y - this.getYByCenter());
		vel.setLength(speed);
		this.body.setLinearVelocity(vel);
	}
	void shoot(float x, float y, ArrayList<Bullet> bullets)
	{
		bullets.add(new Bullet(this.getXByCenter()  , this.getYByCenter()   , x, y, this.body.getWorld(),Constants.BIT_PLAYER));
	}
	
}
