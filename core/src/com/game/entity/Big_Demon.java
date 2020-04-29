package com.game.entity;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.SimpleRPG;

public class Big_Demon extends Entity {
	SimpleRPG game;
	public static final int scale = 4;
	public static final int MOB_HEIGHT = 36;
	public static final int MOB_WIDTH = 34;
	public static final float MOB_ANIMATION_SPEED = 1f;
	public static final float SPEED = 200;
	Animation<TextureRegion>[] run;
	Animation<TextureRegion>[] idle;
	int runFrames, idleFrames;
	int x;
	int y;
	float stateTime;
	boolean flip = false;
	public Big_Demon(SimpleRPG game,int x, int y) {
		super(x,y);
		this.x = x;
		this.y = y;
		stateTime = 0;
		this.game = game;
		//import idle animation
		idleFrames = 4;
		idle = new Animation[idleFrames+7];
		TextureRegion[][] idleSpriteSheet = TextureRegion.split(new Texture("big_demon_idle.png"), MOB_WIDTH, MOB_HEIGHT);
		idle[idleFrames] = new Animation<TextureRegion>(MOB_ANIMATION_SPEED, idleSpriteSheet[0]);
		
		//importing run animation
		runFrames = 4;
		run = new Animation[runFrames+7];
		TextureRegion[][] runSpriteSheet = TextureRegion.split(new Texture("big_demon_run.png"), MOB_WIDTH, MOB_HEIGHT);
		run[runFrames] = new Animation<TextureRegion>(MOB_ANIMATION_SPEED, runSpriteSheet[0]);
		
	}
	private int lastActionX = 0,lastActionY = 0, lastRepeats = 0;
	public static final int limitActions = 100;
	public void actionQuery(float del)
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
		switch(moveY)
		{
			case 1:
			{
				y += SPEED*del;
				if(y+MOB_HEIGHT*scale>SimpleRPG.HEIGHT)
					y = SimpleRPG.HEIGHT-MOB_HEIGHT*scale;
				break;
			}
			case 2:
			{
				y -= SPEED*del;
				if(y<0)
					y=0;
				break;
			}
		}
		switch(moveX)
		{
			case 1:
			{
				flip = false;
				x += SPEED*del;
				if(x+MOB_WIDTH*scale>SimpleRPG.WIDTH)
					x = SimpleRPG.WIDTH-MOB_WIDTH*scale;
				break;
			}
			case 2:
			{
				flip = true;
				x -= SPEED*del;
				if(x<0)
					x=0;
				break;
			}
		}
		stateTime +=  del*5;
		if(moveX>0 || moveY>0)
			render(del,run,runFrames);
		else
			render(del,idle,idleFrames);
	}
	private void render(float del, Animation<TextureRegion>[] anim,int animFrames)
	{
		//game.batch.begin();
		
		game.batch.draw( anim[animFrames].getKeyFrame(stateTime, true), flip?x+MOB_WIDTH*scale:x, y,0,0,MOB_WIDTH*scale,MOB_HEIGHT*scale,flip?-1:1,1,0);
		
		//game.batch.end();
	}
}
