package com.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Bullet extends Entity{
	public static final float SPEED = 500;
	public static final float BULLET_WIDTH = 4;
	public static final float BULLET_HEIGHT = 12;
	private static Texture texture;
	private static TextureRegion region;
	public boolean remove ;
	Vector2 vel;
	public Bullet(float x , float y , float target_x , float target_y, World world) {
		super(x, y, BULLET_WIDTH, BULLET_HEIGHT, BodyType.DynamicBody, "Box", world);
		target_y = Gdx.graphics.getHeight() - target_y + 1;
		remove = false;
		if(texture == null) texture = new Texture("bullet.png");
		if(region == null) region = new TextureRegion(texture);
		vel = new Vector2(target_x - x , target_y - y);
		vel.setLength(SPEED*Entity.BOX2D_SCALE);
		body.setLinearVelocity(vel);
	}

	public void update(float deltaTime ) { 
		/*x += deltaTime * speedVector.x ;
		y += deltaTime * speedVector.y ;*/
		body.setLinearVelocity(vel);
		if(this.getYByCenter() >= Gdx.graphics.getHeight()) remove = true;
		if(this.getXByCenter() >= Gdx.graphics.getWidth()) remove = true;
		if(this.getYByCenter() < 0) remove = true;
		if(this.getXByCenter() < 0) remove = true;
	}
	public void render(SpriteBatch batch) {
		
		batch.draw(texture,this.getXByPixels(),this.getYByPixels());
	}
}
