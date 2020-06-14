package com.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.enemy.Enemy;
import com.game.utils.Constants;

public class Mob extends Entity {
	protected float hp;
	private float atk;
	public int currentRoomId;
	protected Animation<TextureRegion>[] runAnimation;
	protected Animation<TextureRegion>[] idleAnimation;
	protected float animation_speed;
	protected float speed;
	protected int runFrames;
	protected int idleFrames;
	protected float stateTime;
	public boolean flip = false;
	public Mob(float x, float y, float w, float h, World world, int hp,int atk,float speed,float animation_speed, short cBits, short mBits, short gIndex) {
		super(x, y, w, h, BodyType.DynamicBody, "Box", world, cBits, mBits, gIndex);
		this.hp = hp * Constants.DIFFICULTY_MULTIPLIER;
		this.atk = atk * Constants.DIFFICULTY_MULTIPLIER;
		this.speed = speed * Constants.DIFFICULTY_MULTIPLIER;
		this.animation_speed = animation_speed;
	}
	public Mob(float x, float y, float w, float h,float box2DWidth, float box2DHeight, World world, int hp,int atk,float speed,float animation_speed, short cBits, short mBits, short gIndex) {
		super(x, y, w, h, box2DWidth, box2DHeight, BodyType.DynamicBody, "Box", world, cBits, mBits, gIndex);
		this.hp = hp * Constants.DIFFICULTY_MULTIPLIER;
		this.atk = atk * Constants.DIFFICULTY_MULTIPLIER;
		this.speed = speed * Constants.DIFFICULTY_MULTIPLIER;
		this.animation_speed = animation_speed;
	}
	
	protected void importIdleAnimation(String s, int idleFrames,int frameWidth, int frameHeight)
	{
		this.idleFrames = idleFrames;
		idleAnimation = new Animation[idleFrames+7];
		TextureRegion[][] idleSpriteSheet = TextureRegion.split(new Texture(s), frameWidth, frameHeight);
		idleAnimation[idleFrames] = new Animation<TextureRegion>(animation_speed, idleSpriteSheet[0]);
	}
	protected void importRunAnimation(String s, int runFrames,int frameWidth, int frameHeight)
	{
		this.runFrames = runFrames;
		runAnimation = new Animation[runFrames+7];
		TextureRegion[][] runSpriteSheet = TextureRegion.split(new Texture(s), frameWidth, frameHeight);
		runAnimation[runFrames] = new Animation<TextureRegion>(animation_speed, runSpriteSheet[0]);
	}
	public void render(float del, SpriteBatch batch)
	{
		if(!(this instanceof Player))
		{
		if(this.body.getLinearVelocity().x<0)
			flip = true;
		if(this.body.getLinearVelocity().x>0)
			flip = false;
		}
		if(this.body.getLinearVelocity().len()!=0)
			this.render(del,runAnimation,runFrames,batch);
		else
			this.render(del,idleAnimation,idleFrames,batch);
	}
	
	public void render(float del, Animation<TextureRegion>[] anim,int animFrames,SpriteBatch batch)
	{
		stateTime +=  del*5;
		batch.draw( anim[animFrames].getKeyFrame(stateTime, true), flip?this.getXByPixels()+frameWidth:this.getXByPixels(), this.getYByPixels(),0,0,frameWidth,frameHeight,flip?-1:1,1,0);
	}
	public void contactHandle(Object object)
	{
		
		/*if(object instanceof Entity)
		{
			Entity e = (Entity) object;
			this.body.setLinearVelocity(e.getBody().getLinearVelocity());
		}*/
		if(object instanceof Bullet)
		{
			//System.out.println("Hitted by bullet");
			/*Bullet bullet = (Bullet) object;
			Vector2 force = bullet.body.getLinearVelocity();
			force.setLength(speed*5);
			body.applyForce(force, this.body.getPosition(), true);*/
			hp -= 10;
			if(hp == 0)
				remove = true;
			
		}
		if(object instanceof Enemy)
		{
			Enemy e = (Enemy) object;
			if(e.charging)
			{
				if(this instanceof Player)
				{
					hp -= 30;
					if(hp == 0)
						remove = true;
					//GameOverScreen
				}
				e.charging = false;
				e.body.setLinearVelocity(new Vector2());
				
				/*Vector2 force = e.body.getLinearVelocity();
				force.setLength(speed*100);
				body.applyForce(force, this.body.getPosition(), true);*/
			}
		}
	}
	public float getAtk()
	{
		return atk;
	}
	public float getHP()
	{
		return hp;
	}
}
