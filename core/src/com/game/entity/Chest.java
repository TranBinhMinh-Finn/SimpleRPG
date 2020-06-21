package com.game.entity;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.game.utils.Constants;

public class Chest extends Entity {
	public static final int FRAME_WIDTH = 16;
	public static final int FRAME_HEIGHT = 16;
	private Texture idle;
	private Animation<TextureRegion>[] openAnimation;
	private int openFrames = 3;
	private float animation_speed = 0.5f;
	private float stateTime;
	private static float scale = 3f;
	public Chest(Vector2 position, World world)
	{
		super(position.x,position.y,(FRAME_WIDTH*scale),(FRAME_HEIGHT*scale),BodyType.StaticBody,"Box",world,Constants.BIT_ENEMY, (short) (Constants.BIT_ENEMY | Constants.BIT_PLAYER), (short) 0);
		idle = new Texture("Entity/chest.png");
		openAnimation = new Animation[openFrames+7];
		TextureRegion[][] openSpriteSheet = TextureRegion.split(new Texture("Entity/chestSprite.png"), FRAME_WIDTH,FRAME_HEIGHT );
		openAnimation[openFrames] = new Animation<TextureRegion>(animation_speed, openSpriteSheet[0]);
	}
	public boolean open;
	public boolean done = false;
	public void render(float del,SpriteBatch batch)
	{
		if(open)
		{
			if(stateTime < 1f)
				stateTime += del;
			else 
				done = true;
			batch.draw(openAnimation[openFrames].getKeyFrame(stateTime, true), this.getXByPixels(), this.getYByPixels(),frameWidth,frameHeight);
		}
		else
			batch.draw(idle,this.getXByPixels(),this.getYByPixels(),frameWidth,frameHeight);
	}
	public boolean touching = false;
	public void contactHandle(Object object)
	{
		if(object instanceof Player)
			touching = true;
	}
	public void endContact(Object object)
	{
		if(object instanceof Player)
			touching = false;
	}
}
