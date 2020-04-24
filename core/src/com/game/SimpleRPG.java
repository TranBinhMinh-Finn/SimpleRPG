package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.screens.MainMenuScreen;

public class SimpleRPG extends Game {
	public SpriteBatch batch;
	
	public static final int WIDTH = 720;
	public static final int HEIGHT = 480;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	
	}
}
