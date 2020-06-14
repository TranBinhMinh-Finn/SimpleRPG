package com.game.entity.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Chort extends Enemy {
	private static final float scale = 4;
	private static final int FRAME_HEIGHT = 24;
	private static final int FRAME_WIDTH = 16;
	private static final float MOB_ANIMATION_SPEED = 1f;
	private static final float SPEED = 30;
	private static final int MOB_hp = 20;
	private static final int MOB_atk = 5;
	private static final int IDLE_FRAME_NUMBER = 4;
	private static final int RUN_FRAME_NUMBER = 4;
	private static final float RANGE = 100;
	public Chort(float x, float y, World world ) {
		super(x, y,FRAME_WIDTH*scale, FRAME_HEIGHT*scale,(FRAME_WIDTH-4)*scale,(FRAME_HEIGHT-6)*scale, world, MOB_hp, MOB_atk, SPEED, MOB_ANIMATION_SPEED, RANGE, Enemy.melee);
		this.importIdleAnimation("Entity/Enemy/chort_idle.png", IDLE_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.importRunAnimation("Entity/Enemy/chort_run.png", RUN_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.chargeMultiPlier = 1.5f;
	}

	public Chort(Vector2 position, World world ) {
		super(position.x, position.y,FRAME_WIDTH*scale, FRAME_HEIGHT*scale,(FRAME_WIDTH-4)*scale,(FRAME_HEIGHT-6)*scale, world, MOB_hp, MOB_atk, SPEED, MOB_ANIMATION_SPEED, RANGE, Enemy.melee);
		this.importIdleAnimation("Entity/Enemy/chort_idle.png", IDLE_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.importRunAnimation("Entity/Enemy/chort_run.png", RUN_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.chargeMultiPlier = 1.5f;
	}
}
