package com.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.game.SimpleRPG;
import com.game.entity.Bullet;
import com.game.entity.Player;
import com.game.entity.*;
public class MainGameScreen implements Screen {

	SimpleRPG game;
	Player danchoi1;
	ArrayList<Bullet> bullets;
	Big_Demon quai1;
	public MainGameScreen(SimpleRPG game)
	{
		this.game = game;
		bullets = new ArrayList<Bullet>();
		danchoi1 = new Player(game,0,0);
		quai1 = new Big_Demon(game,800,800);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		float del = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.5f, 0.2f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// bullet testing ...
		
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		for(Bullet i : bullets) {
			i.update(del);
			if(i.remove == true) {
				bulletsToRemove.add(i);
			}
		}
		bullets.removeAll(bulletsToRemove);
		// entity behavior code 
		game.batch.begin();
		
		for(Bullet i : bullets) {
			i.render(game.batch);
		}
		danchoi1.inputQuery(del , bullets);
		
		game.batch.end();
		
		quai1.actionQuery(del);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
