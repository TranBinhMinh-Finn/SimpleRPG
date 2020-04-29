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
	public Bullet(int x , int y , int tx , int ty) {
		super(x,y);
		ty = Gdx.graphics.getHeight() - ty + 1;
		remove = false;
		if(texture == null) texture = new Texture("bullet.png");
		speedVector = new Vector2(tx - x , ty - y);
		speedVector.setLength(1f);
	}
	
	public void update(float deltaTime ) { 
		x += deltaTime * speedVector.x * SPEED;
		y += deltaTime * speedVector.y * SPEED;
		if(y >= Gdx.graphics.getHeight()) remove = true;
		if(x >= Gdx.graphics.getWidth()) remove = true;
		if(y < 0) remove = true;
		if(x < 0) remove = true;
	}
	public void render(SpriteBatch batch) {
		
		batch.draw(texture,x,y);
		
	}
}
