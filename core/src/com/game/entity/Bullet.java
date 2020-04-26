package com.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity{
	public static final float SPEED = 500;
	private static Texture texture;
	public boolean remove ;
	float distance;
	Vector2 speedVector;
	public Bullet(float x , float y , float tx , float ty) {
		super(x,y);
		remove = false;
		if(texture == null) texture = new Texture("bullet.png");
		speedVector = new Vector2(tx-x , ty-y);
		distance = position.dst(speedVector);
		speedVector.x /= distance;
		speedVector.y /= distance;
	}
	
	public void update(float deltaTime ) {
		position.x +=deltaTime * speedVector.x * SPEED;
		position.y +=deltaTime * speedVector.y * SPEED;
		if(position.y > Gdx.graphics.getHeight()) remove = true;
	}
	public void render(SpriteBatch batch) {
		batch.draw(texture,position.x,position.y);
	}
}
