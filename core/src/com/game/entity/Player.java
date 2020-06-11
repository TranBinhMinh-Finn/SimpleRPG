package com.game.entity;

import com.badlogic.gdx.physics.box2d.World;
import com.game.SimpleRPG;
import com.game.utils.Constants;

public class Player extends Mob {
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
	public float slowed = 1;
	
	//this.importIdleAnimation("knight_idle_spritesheet.png", FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
	//this.importRunAnimation("knight_run_spritesheet.png", FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);

	
	public Player(float x, float y, World world)
	{
		super(x, y, FRAME_WIDTH*scale, FRAME_HEIGHT*scale,(FRAME_WIDTH - 2)*scale, (FRAME_HEIGHT - 3)*scale, world, player_hp, player_atk, SPEED, CHAR_ANIMATION_SPEED, Constants.BIT_PLAYER, (short)(Constants.BIT_ENEMY|Constants.BIT_WALL|Constants.BIT_BULLET), (short)0);
		this.importIdleAnimation("Entity/Player/knight_idle_spritesheet.png", IDLE_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.importRunAnimation("Entity/Player/knight_run_spritesheet.png", RUN_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
	}
	
}