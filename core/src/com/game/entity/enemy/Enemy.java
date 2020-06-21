package com.game.entity.enemy;

import java.util.ArrayList;
import java.util.*;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.Bullet;
import com.game.entity.Entity;
import com.game.entity.Mob;
import com.game.entity.Player;
import com.game.handlers.GameStateManager;
import com.game.handlers.SoundManager;
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
	
	ArrayList<Vector2> path;
	public Enemy(float x, float y, float w, float h, World world, int hp,int atk,float speed,float animation_speed, float range,int type)
	{	
		super(x,y,w,h,world,hp,atk,speed,animation_speed,Constants.BIT_ENEMY, (short)(Constants.BIT_BULLET|Constants.BIT_ENEMY|Constants.BIT_PLAYER|Constants.BIT_WALL),(short)0);
		this.range = range;
		this.type = type;
		Random random = new Random();
		attackWaitTime/=random.ints(0,20).findFirst().getAsInt();
		path = new ArrayList<Vector2>();
	}
	public Enemy(float x, float y, float w, float h,float box2DWidth, float box2DHeight, World world, int hp,int atk,float speed,float animation_speed, float range, int type)
	{	
		super(x,y,w,h,box2DWidth,box2DHeight,world,hp,atk,speed,animation_speed,Constants.BIT_ENEMY,(short)(Constants.BIT_BULLET|Constants.BIT_ENEMY|Constants.BIT_PLAYER|Constants.BIT_WALL),(short)0);
		this.type = type;
		this.range = range;
		path = new ArrayList<Vector2>();
		
	}
	boolean aggroCheck(Player player)
	{
		if(this.currentRoomId == player.currentRoomId)
		{
			Vector2 distance = new Vector2(player.getXByCenter() - this.getXByCenter(), player.getYByCenter() - this.getYByCenter());
			//if(distance.len()<100f)
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
	
	void findPath()
	{
		path = GameStateManager.getMap().findPath(this.body.getPosition(), GameStateManager.getPlayer().getBody().getPosition());
		/*for(Vector2 i: path)
		{
			System.out.println(i.x + " " + i.y);
		}
		System.out.println("---------------------");*/
	}
	public boolean obstruct;
	RayCastCallback callback = new RayCastCallback()
	{
		
				@Override
			public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
					if(fixture.getUserData() instanceof Entity)
					{
						return -1;
					}
					obstruct = true;
					return 0;
			}
	};
	private boolean rayCast(Vector2 point1, Vector2 point2, World world)
	{
		obstruct = false;
		if(point1.epsilonEquals(point2))
			return false;
		world.rayCast(callback, point1, point2);
		//GameStateManager.line.add(point1);
		//GameStateManager.line.add(point2);
		//System.out.println(rayFraction);
		return obstruct;
	}
	private boolean obstructed(Player player)
	{
		return rayCast(this.body.getPosition(),player.getBody().getPosition(),this.body.getWorld());
	}
	public void actionQuery(Player player, float del)
	{
		if(aggroCheck(player)==false)
			return;
		if(charging)
			return ;
		attackWaitTime -= del;
		if(!path.isEmpty())
		{
			if(body.getPosition().dst(path.get(0))<30f)
			{
				path.remove(0);
			}
			else
				move(path.get(0).x, path.get(0).y);
			return ;
		}
		if(obstructed(player))
			findPath();
		else 
		{
			if(attackWaitTime > 0)
			{
				randomActionQuery(del);
				return ;
			}
			if(attackRangeCheck(player.getXByCenter(), player.getYByCenter()))
			{
				charge(player.getBody().getPosition().x, player.getBody().getPosition().y);
				attackWaitTime = attackDelay;
			}
			else
				move(player.getBody().getPosition().x, player.getBody().getPosition().y);
		}
	}
	public void actionQuery(Player player, ArrayList<Bullet> bullets, float del)
	{
		if(aggroCheck(player)==false)
			return;
		if(!path.isEmpty())
		{
			if(body.getPosition().dst(path.get(0))<30f)
			{
				path.remove(0);
			}
			else
				move(path.get(0).x, path.get(0).y);
			return ;
		}
		if(obstructed(player))
			findPath();
		else 
		{
			attackWaitTime -= del*5;
			if(attackWaitTime > 0)
			{
				randomActionQuery(del);
				return ;
			}
			if(attackRangeCheck(player.getXByCenter(),player.getYByCenter()))
			{
				
				shoot(player.getXByCenter(), player.getYByCenter(),bullets);
				attackWaitTime = attackDelay;
			}
			else if(rangedRangeCheck(player.getXByCenter(),player.getYByCenter()))
				move(player.getBody().getPosition().x, player.getBody().getPosition().y);
		}
	}
	private void charge(float x, float y)
	{
		Vector2 vel = new Vector2(x - body.getPosition().x , y - body.getPosition().y);
		vel.setLength(speed*100*chargeMultiPlier);
		this.body.setLinearVelocity(vel);
		charging = true;
	}
	
	private void move(float x, float y)
	{
		Vector2 vel = new Vector2(x - body.getPosition().x , y - body.getPosition().y);
		vel.setLength(speed);
		this.body.setLinearVelocity(vel);
	}
	private void shoot(float x, float y, ArrayList<Bullet> bullets)
	{
		SoundManager.playGunShotSound();
		bullets.add(new Bullet(this.getXByCenter()  , this.getYByCenter()   , x, y, this.body.getWorld(),Constants.BIT_PLAYER, this.getAtk()));
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
		if(charging == true)
		{
			charging = false;
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
			moveX = random.ints(0,2).findFirst().getAsInt();
			moveY = random.ints(0,2).findFirst().getAsInt();
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
		Vector2 vel = new Vector2(velX,velY);
		vel.setLength(speed);
		this.body.setLinearVelocity(vel);
	}
}
