package com.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.SimpleRPG;

public class Player extends Mob {
	SimpleRPG game ;
	public static final int FRAME_WIDTH = 16;
	public static final int FRAME_HEIGHT = 16;
	public static final int IDLE_FRAME_NUMBER = 6;
	public static final int RUN_FRAME_NUMBER = 6;
	public static final int scale = 4;
	public static final float CHAR_ANIMATION_SPEED = 0.5f;
	public static final float SPEED = 50;
	public static final int player_hp = 100;
	public static final int player_atk = 10;
	float stateTime;
	
	
	//this.importIdleAnimation("knight_idle_spritesheet.png", FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
	//this.importRunAnimation("knight_run_spritesheet.png", FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);

	
	public Player(SimpleRPG game, float x, float y, World world)
	{
		super(x, y, FRAME_WIDTH*scale, FRAME_HEIGHT*scale,(FRAME_WIDTH - 2)*scale, (FRAME_HEIGHT - 2)*scale, world, player_hp, player_atk, SPEED, CHAR_ANIMATION_SPEED);
		this.importIdleAnimation("knight_idle_spritesheet.png", IDLE_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.importRunAnimation("knight_run_spritesheet.png", RUN_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.game = game;
	}
	public void inputQuery(float del,ArrayList<Bullet> bullets)
	{
		
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			bullets.add(new Bullet(this.getXByCenter()  , this.getYByCenter()   , Gdx.input.getX(), Gdx.input.getY(), this.body.getWorld()));
		}

		
		float velX = 0;
		float velY = 0;
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			velY = 1;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			velY = -1;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			flip = false;
			velX = 1;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			flip = true;
			velX = -1;
		}
		vel = new Vector2(velX,velY);
		vel.setLength(SPEED*del*50);
		this.body.setLinearVelocity(vel);
		
	}
	public void render(float del)
	{
		if(vel.len()!=0)
			this.render(del,runAnimation,runFrames,game.batch);
		else
			this.render(del,idleAnimation,idleFrames,game.batch);
	}
}
