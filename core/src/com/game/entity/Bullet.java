package com.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.handlers.SoundManager;
import com.game.utils.Constants;

public class Bullet extends Entity{
	public static final float SPEED = 250;
	public static final float BULLET_WIDTH = 22;
	public static final float BULLET_HEIGHT = 22;
	public static final float scale = 1.5f;
	private static Texture texture;
	private static TextureRegion region;
	Vector2 vel;
	private static Animation<TextureRegion>[] contactAnimation;
	private static int contactFrames = 4;
	private static boolean init = false;
	static float animation_speed = 0.5f;
	public float damage;
	float landingX, landingY;
	static float duration = 2f;
	float stateTime;
	public boolean removeEffect;
	private static void init()
	{
		if(init) return ;
		init = true;
		texture = new Texture("Entity/bullet.png");
		region = new TextureRegion(texture);
		contactAnimation = new Animation[contactFrames+7];
		TextureRegion[][] spriteSheet = TextureRegion.split(new Texture("Entity/bulletSprite.png"), (int)BULLET_WIDTH, (int)BULLET_HEIGHT);
		contactAnimation[contactFrames] = new Animation<TextureRegion>(animation_speed, spriteSheet[0]);
	}
	public Bullet(float x , float y , float target_x , float target_y, World world, short targetType, float damage) {
		super(x, y, BULLET_WIDTH * scale, BULLET_HEIGHT * scale, (BULLET_WIDTH-4)*scale, (BULLET_HEIGHT-4)*scale, BodyType.DynamicBody, "Box", world, Constants.BIT_BULLET, (short)(targetType|Constants.BIT_WALL), (short)0);
		//target_y = Gdx.graphics.getHeight() - target_y + 1;
		this.damage = damage;
		remove = false;
		init();
		vel = new Vector2(target_x - x , target_y - y);
		vel.setLength(SPEED);
		body.setLinearVelocity(vel);
	}
	
	public void contactHandle(Object object)
	{
		SoundManager.playExplosionSound();
		body.setLinearVelocity(new Vector2(0,0));
		landingX = this.getXByPixels();
		landingY = this.getYByPixels();
		remove = true;
	}
	public void render(SpriteBatch batch) {	
		
		batch.draw(texture,this.getXByPixels(),this.getYByPixels(),frameWidth,frameHeight);
	}
	public void renderEffect(SpriteBatch batch, float del)
	{
		stateTime +=  del*5;
		if(stateTime > duration)
		{
			removeEffect = true;
			return ;
		}
		batch.draw( contactAnimation[contactFrames].getKeyFrame(stateTime, true), landingX, landingY,frameWidth,frameHeight);
	}
}
