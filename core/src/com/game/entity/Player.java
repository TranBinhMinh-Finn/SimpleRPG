package com.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.SimpleRPG;
import com.game.screens.MainGameScreen;

public class Player extends Entity {
	SimpleRPG game ;
	public static final int CHAR_WIDTH = 16;
	public static final int CHAR_HEIGHT = 16;
	public static final int scale = 4;
	public static final float CHAR_ANIMATION_SPEED = 0.5f;
	public static final float SPEED = 400;
	Animation<TextureRegion>[] run;
	Animation<TextureRegion>[] idle;
	int runFrames, idleFrames;
	float stateTime;
	boolean flip = false;
	public Player(SimpleRPG game,float x,float y) {
		super(x,y);
		this.game = game;
		stateTime = 0;
		// starts at center of the screen
		//y = SimpleRPG.HEIGHT/2 - CHAR_HEIGHT/2;
		//x = SimpleRPG.WIDTH/2  - CHAR_WIDTH/2;
		this.x = x;
		this.y = y;
		
		//importing idle animation
		idleFrames = 6;
		idle = new Animation[idleFrames+7];
		TextureRegion[][] idleSpriteSheet = TextureRegion.split(new Texture("knight_idle_spritesheet.png"), CHAR_WIDTH, CHAR_HEIGHT);
		idle[idleFrames] = new Animation<TextureRegion>(CHAR_ANIMATION_SPEED, idleSpriteSheet[0]);
		
		//importing run animation
		runFrames = 6;
		run = new Animation[runFrames+7] ;
		TextureRegion[][] runSpriteSheet = TextureRegion.split(new Texture("knight_run_spritesheet.png"), CHAR_WIDTH, CHAR_HEIGHT);
		run[runFrames] = new Animation<TextureRegion>(CHAR_ANIMATION_SPEED, runSpriteSheet[0]);
		
		
		
	}
	public void inputQuery(float del, ArrayList<Bullet> bullets)
	{
		boolean pressedKey = false;
		if(Gdx.input.isKeyPressed(Keys.SPACE)) {
			bullets.add(new Bullet(x , y));
		}
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			pressedKey = true;
			y += SPEED*del;
			if(y+CHAR_WIDTH*scale>SimpleRPG.HEIGHT)
				y = SimpleRPG.HEIGHT-CHAR_HEIGHT*scale;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			pressedKey = true;
			y -= SPEED*del;
			if(y<0)
				y=0;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			flip = false;
			pressedKey = true;
			x += SPEED*del;
			if(x+CHAR_WIDTH*scale>SimpleRPG.WIDTH)
				x = SimpleRPG.WIDTH-CHAR_WIDTH*scale;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			pressedKey = true;
			flip = true;
			x -= SPEED*del;
			if(x<0)
				x=0;
		}
		stateTime +=  del*5;
		if(pressedKey)
			render(del,run,runFrames);
		else
			render(del,idle,idleFrames);
		
	}
	public void render(float del, Animation<TextureRegion>[] anim,int animFrames)
	{
		game.batch.draw( anim[animFrames].getKeyFrame(stateTime, true), flip?x+CHAR_WIDTH*scale:x, y,0,0,
														CHAR_WIDTH*scale,CHAR_HEIGHT*scale,flip?-1:1,1,0);
	}
	
}
