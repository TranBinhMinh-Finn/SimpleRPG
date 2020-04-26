package com.game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.SimpleRPG;

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
		if(Gdx.input.isTouched()) {
			
			bullets.add(new Bullet(position.x , position.y , Gdx.input.getX(), Gdx.input.getY()));
			System.out.println(position.x + " " + position.y);
			System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
		}
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			pressedKey = true;
			position.y += SPEED*del;
			if(position.y+CHAR_WIDTH*scale>SimpleRPG.HEIGHT)
				position.y = SimpleRPG.HEIGHT-CHAR_HEIGHT*scale;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			pressedKey = true;
			position.y -= SPEED*del;
			if(position.y<0)
				position.y=0;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			flip = false;
			pressedKey = true;
			position.x += SPEED*del;
			if(position.x+CHAR_WIDTH*scale>SimpleRPG.WIDTH)
				position.x = SimpleRPG.WIDTH-CHAR_WIDTH*scale;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			pressedKey = true;
			flip = true;
			position.x -= SPEED*del;
			if(position.x<0)
				position.x=0;
		}
		stateTime +=  del*5;
		if(pressedKey)
			render(del,run,runFrames);
		else
			render(del,idle,idleFrames);
		
	}
	public void render(float del, Animation<TextureRegion>[] anim,int animFrames)
	{
		game.batch.draw( anim[animFrames].getKeyFrame(stateTime, true), flip?position.x+CHAR_WIDTH*scale:position.x, position.y,0,0,
														CHAR_WIDTH*scale,CHAR_HEIGHT*scale,flip?-1:1,1,0);
	}
	
}
