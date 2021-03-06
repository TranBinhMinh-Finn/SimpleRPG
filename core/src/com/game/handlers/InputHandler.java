package com.game.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.entity.Bullet;
import com.game.entity.Player;
import com.game.utils.Constants;

public class InputHandler {
	public static void keyHandler(float del, Player player)
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
			velX = 1;
			player.flip = false;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			player.flip = true;
			velX = -1;
		}
		Vector2 vel = new Vector2(velX,velY);
		vel.setLength(Player.SPEED*del*50 * player.slowed);
		player.setLinearVelocity(vel);
	}
	
	public static void mouseClickHandler(float del, Player player, ArrayList<Bullet> bullets, OrthographicCamera camera)
	{	
		if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			SoundManager.playGunShotSound();
			float X = Gdx.input.getX();
			float Y = Gdx.input.getY();
			
			Vector3 touchPos = new Vector3(X,Y,0); 
			touchPos = camera.unproject(touchPos);
			bullets.add(new Bullet(player.getXByCenter()  , player.getYByCenter()   , touchPos.x, touchPos.y, player.getBody().getWorld(),Constants.BIT_ENEMY,player.getAtk()));
		}
		if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
		{
			if(GameStateManager.getMap().chest.touching)
			{
				if(!GameStateManager.getMap().chest.open) SoundManager.playChestOpenSound();
				GameStateManager.getMap().chest.open = true;
			}
		}
	}
}
