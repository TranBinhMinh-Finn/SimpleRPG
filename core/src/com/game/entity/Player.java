package com.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.SimpleRPG;
import com.game.screens.MainGameScreen;

public class Player {
	SimpleRPG game ;
	public static final int CHAR_WIDTH = 16;
	public static final int CHAR_HEIGHT = 16;
	public static final float CHAR_ANIMATION_SPEED = 0.5f;
	
	Animation<TextureRegion>[] run;
	Animation<TextureRegion>[] idle;
	int runFrames, idleFrames;
	int x;
	int y;
	float stateTime;
	public Player(SimpleRPG game,int x,int y) {
		this.game = game;
		stateTime = 0;
		//y = SimpleRPG.HEIGHT/2 - CHAR_HEIGHT/2;
		//x = SimpleRPG.WIDTH/2  - CHAR_WIDTH/2;
		this.x = x;
		this.y = y;
		runFrames = 6;
		run = new Animation[runFrames+7];
		TextureRegion[][] runSpriteSheet = TextureRegion.split(new Texture("knight_run_spritesheet.png"), CHAR_WIDTH, CHAR_HEIGHT);
		run[runFrames] = new Animation<TextureRegion>(CHAR_ANIMATION_SPEED, runSpriteSheet[0]);
		idleFrames = 6;
		idle = new Animation[idleFrames+7];
		TextureRegion[][] idleSpriteSheet = TextureRegion.split(new Texture("knight_idle_spritesheet.png"), CHAR_WIDTH, CHAR_HEIGHT);
		idle[idleFrames] = new Animation<TextureRegion>(CHAR_ANIMATION_SPEED, idleSpriteSheet[0]);
	}
	public void render(float del)
	{
		boolean flip = false;
		boolean pressedKey = false;
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			pressedKey = true;
			y += MainGameScreen.SPEED*del;
			if(y+CHAR_WIDTH*3>SimpleRPG.HEIGHT)
				y = SimpleRPG.HEIGHT-CHAR_HEIGHT*3;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			pressedKey = true;
			y -= MainGameScreen.SPEED*del;
			if(y<0)
				y=0;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			flip = false;
			pressedKey = true;
			x += MainGameScreen.SPEED*del;
			if(x+CHAR_WIDTH*3>SimpleRPG.WIDTH)
				x = SimpleRPG.WIDTH-CHAR_WIDTH*3;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			pressedKey = true;
			flip = true;
			x -= MainGameScreen.SPEED*del;
			if(x<0)
				x=0;
		}
		stateTime +=  del*5;
		game.batch.begin();
		if(pressedKey)
			game.batch.draw( run[runFrames].getKeyFrame(stateTime, true), flip?x+CHAR_WIDTH*3:x, y,0,0,CHAR_WIDTH*3,CHAR_HEIGHT*3,flip?-1:1,1,0);
		else
			game.batch.draw( idle[idleFrames].getKeyFrame(stateTime, true), flip?x+CHAR_WIDTH*3:x, y,0,0,CHAR_WIDTH*3,CHAR_HEIGHT*3,flip?-1:1,1,0);
		game.batch.end();
		
		
	}
	
}
