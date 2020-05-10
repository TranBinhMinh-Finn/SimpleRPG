package com.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Mob extends Entity {
	Vector2 vel;
	int hp;
	int atk;
	Animation<TextureRegion>[] runAnimation;
	Animation<TextureRegion>[] idleAnimation;
	float animation_speed;
	float speed;
	int runFrames;
	int idleFrames;
	float stateTime;
	boolean flip = false;
	public Mob(float x, float y, float w, float h, World world, int hp,int atk,float speed,float animation_speed) {
		super(x, y, w, h, BodyType.DynamicBody, "Box", world);
		vel = new Vector2(0,0);
		this.hp = hp;
		this.atk = atk;
		this.speed = speed;
		this.animation_speed = animation_speed;
	}
	public Mob(float x, float y, float w, float h,float box2DWidth, float box2DHeight, World world, int hp,int atk,float speed,float animation_speed) {
		super(x, y, w, h, box2DWidth, box2DHeight, BodyType.DynamicBody, "Box", world);
		vel = new Vector2(0,0);
		this.hp = hp;
		this.atk = atk;
		this.speed = speed;
		this.animation_speed = animation_speed;
	}
	void importIdleAnimation(String s, int idleFrames,int frameWidth, int frameHeight)
	{
		this.idleFrames = idleFrames;
		idleAnimation = new Animation[idleFrames+7];
		TextureRegion[][] idleSpriteSheet = TextureRegion.split(new Texture(s), frameWidth, frameHeight);
		idleAnimation[idleFrames] = new Animation<TextureRegion>(animation_speed, idleSpriteSheet[0]);
	}
	void importRunAnimation(String s, int runFrames,int frameWidth, int frameHeight)
	{
		this.runFrames = runFrames;
		runAnimation = new Animation[runFrames+7];
		TextureRegion[][] runSpriteSheet = TextureRegion.split(new Texture(s), frameWidth, frameHeight);
		runAnimation[runFrames] = new Animation<TextureRegion>(animation_speed, runSpriteSheet[0]);
	}
	public void render(float del, Animation<TextureRegion>[] anim,int animFrames,SpriteBatch batch)
	{
		stateTime +=  del*5;
		batch.draw( anim[animFrames].getKeyFrame(stateTime, true), flip?this.getXByPixels()+frameWidth:this.getXByPixels(), this.getYByPixels(),0,0,frameWidth,frameHeight,flip?-1:1,1,0);
	}
}
