package com.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
	public static final int SPEED = 500;
	public static final int DEFAULT_Y = 40;
	private static Texture texture;
	int x , y;
	public boolean remove = false;
	
	public Bullet(int x , int y) {
		this.x = x;
		this.y = DEFAULT_Y;
		if(texture == null) texture = new Texture("bullet.png");
	}
	
	public void update(float deltaTime) {
		y += SPEED * deltaTime;
		if(y > Gdx.graphics.getHeight()) remove = true;
	}
	public void render(SpriteBatch batch) {
		batch.draw(texture,x,y);
	}
}
