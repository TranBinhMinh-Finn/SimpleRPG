package com.game.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.game.entity.Bullet;
import com.game.entity.Player;

public class InputHandler {
	Player player;
	public InputHandler(Player player)
	{
		this.player = player;
	}
	public void keyHandler(float del)
	{
		float velX = 0;
		float velY = 0;
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			velY = 1;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			velY = -1;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			player.flip = false;
			velX = 1;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			player.flip = true;
			velX = -1;
		}
		Vector2 vel = new Vector2(velX,velY);
		vel.setLength(Player.SPEED*del*50);
		player.body.setLinearVelocity(vel);
	}
	
	public void mouseClickHandler(float del, ArrayList<Bullet> bullets)
	{	
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			bullets.add(new Bullet(player.getXByCenter()  , player.getYByCenter()   , Gdx.input.getX(), Gdx.input.getY(), player.body.getWorld()));
		}
	}
}
