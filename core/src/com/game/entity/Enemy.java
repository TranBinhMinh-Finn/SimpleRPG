package com.game.entity;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.utils.Constants;

public class Enemy extends Mob {
	public static int melee = 0;
	public static int ranged = 1;
	static float attackDelay = 5;
	static float range2 = 500;
	public boolean charging;
	float range;
	public int type;
	float attackWaitTime = attackDelay;
	float chargeMultiPlier;
	public Enemy(float x, float y, float w, float h, World world, int hp,int atk,float speed,float animation_speed, float range,int type)
	{	
		super(x,y,w,h,world,hp,atk,speed,animation_speed,Constants.BIT_ENEMY, (short)(Constants.BIT_BULLET|Constants.BIT_ENEMY|Constants.BIT_PLAYER|Constants.BIT_WALL),(short)0);
		this.range = range;
		this.type = type;
		Random random = new Random();
		attackWaitTime/=random.ints(0,20).findFirst().getAsInt();
	}
	public Enemy(float x, float y, float w, float h,float box2DWidth, float box2DHeight, World world, int hp,int atk,float speed,float animation_speed, float range, int type)
	{	
		super(x,y,w,h,box2DWidth,box2DHeight,world,hp,atk,speed,animation_speed,Constants.BIT_ENEMY,(short)(Constants.BIT_BULLET|Constants.BIT_ENEMY|Constants.BIT_PLAYER|Constants.BIT_WALL),(short)0);
		this.type = type;
		this.range = range;
	}
	boolean aggroCheck(Player player)
	{
		if(this.currentRoomId == player.currentRoomId)
		{
			return true;
		}
		return false;
	}
	boolean attackRangeCheck(float target_x, float target_y)
	{
		Vector2 distance = new Vector2(target_x - this.getXByCenter(), target_y - this.getYByCenter());
		if(distance.len() < range)
			return true;
		return false;
	}
	boolean rangedRangeCheck(float target_x, float target_y)
	{
		Vector2 distance = new Vector2(target_x - this.getXByCenter(), target_y - this.getYByCenter());
		if(distance.len() > range2)
			return true;
		return false;
	}
	public void actionQuery(Player player, float del)
	{
		if(aggroCheck(player)==false)
			return;
		if(charging)
			return ;
		attackWaitTime -= del;
		if(attackWaitTime > 0)
		{
			randomActionQuery(del);
			return ;
		}
		if(attackRangeCheck(player.getXByCenter(),player.getYByCenter()))
		{

			charge(player.getXByCenter(),player.getYByCenter());
			attackWaitTime = attackDelay;
		}
		else
			move(player.getXByCenter(),player.getYByCenter());
	}
	public void actionQuery(Player player, ArrayList<Bullet> bullets, float del)
	{
		if(aggroCheck(player)==false)
			return;
		attackWaitTime -= del/2;
		if(attackWaitTime > 0)
		{
			randomActionQuery(del);
			return ;
		}
		if(attackRangeCheck(player.getXByCenter(),player.getYByCenter()))
		{
			
			shoot(player.getXByCenter(),player.getYByCenter(), bullets);
			attackWaitTime = attackDelay;
		}
		else if(rangedRangeCheck(player.getXByCenter(),player.getYByCenter()))
			move(player.getXByCenter(),player.getYByCenter());
	}
	void charge(float x, float y)
	{
		vel = new Vector2(x - this.getXByCenter() , y - this.getYByCenter());
		vel.setLength(speed*100*chargeMultiPlier);
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
	public void contactHandle(Object object)
	{
		if(object instanceof Bullet)
		{
			//System.out.println("Hitted by bullet");
			hp -= 10;
			if(hp == 0)
				remove = true;
		}
	}
	private int lastActionX = 0,lastActionY = 0, lastRepeats = 0;
	private static final int limitActions = 100;
	public void randomActionQuery(float del)
	{
		int moveX;
		int moveY;
		Random random = new Random();
		if(lastRepeats < limitActions)
		{
			lastRepeats+=10;
			moveX = lastActionX;
			moveY = lastActionY;
		}
		else
		{
			moveX = random.ints(0,3).findFirst().getAsInt();
			moveY = random.ints(0,3).findFirst().getAsInt();
			lastActionX = moveX;
			lastActionY = moveY;
			lastRepeats = 0;
		}
		float velX = 0;
		float velY = 0;
		switch(moveY)
		{
			case 1:
			{
				velY = 1;
				break;
			}
			case 2:
			{
				velY = -1;
				break;
			}
		}
		switch(moveX)
		{
			case 1:
			{
				velX = 1;
				break;
			}
			case 2:
			{
				velX = -1;
				break;
			}
		}
		vel = new Vector2(velX,velY);
		vel.setLength(speed);
		this.body.setLinearVelocity(vel);
	}
}
