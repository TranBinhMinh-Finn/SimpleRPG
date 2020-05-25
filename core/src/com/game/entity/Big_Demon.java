package com.game.entity;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.Constants;
import com.game.SimpleRPG;

public class Big_Demon extends Mob {
	SimpleRPG game;
	private static final float scale = 4;
	private static final int FRAME_HEIGHT = 36;
	private static final int FRAME_WIDTH = 32;
	private static final float MOB_ANIMATION_SPEED = 1f;
	private static final float SPEED = 30;
	private static final int MOB_hp = 200;
	private static final int MOB_atk = 5;
	private static final int IDLE_FRAME_NUMBER = 4;
	private static final int RUN_FRAME_NUMBER = 4;
	
	public Big_Demon(SimpleRPG game, float x, float y, World world ) {
		super(x, y,FRAME_WIDTH*scale, FRAME_HEIGHT*scale,(FRAME_WIDTH-4)*scale,(FRAME_HEIGHT-6)*scale, world, MOB_hp, MOB_atk, SPEED, MOB_ANIMATION_SPEED, Constants.BIT_ENEMY,(short) (Constants.BIT_BULLET | Constants.BIT_PLAYER | Constants.BIT_ENEMY), (short) 0);
		this.importIdleAnimation("big_demon_idle.png", IDLE_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.importRunAnimation("big_demon_run.png", RUN_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.game = game;
	}


	private int lastActionX = 0,lastActionY = 0, lastRepeats = 0;
	private static final int limitActions = 100;
	public void randomAtionQuery(float del)
	{
		int moveX;
		int moveY;
		Random random = new Random();
		if(lastRepeats < limitActions)
		{
			lastRepeats+=random.ints(0,limitActions/10).findFirst().getAsInt();
			moveX = lastActionX;
			moveY = lastActionY;
		}
		else
		{
			moveX = random.ints(0,5).findFirst().getAsInt();
			moveY = random.ints(0,5).findFirst().getAsInt();
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
		vel.setLength(SPEED);
		this.body.setLinearVelocity(vel);
	}
	public void render(float del)
	{
		this.body.setLinearVelocity(new Vector2(0,0));
		if(vel.len()!=0)
			this.render(del,runAnimation,runFrames,game.batch);
		else
			this.render(del,idleAnimation,idleFrames,game.batch);
	}
}
