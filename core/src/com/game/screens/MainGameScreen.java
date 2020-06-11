package com.game.screens;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.SimpleRPG;

import com.game.handlers.GameStateManager;

public class MainGameScreen implements Screen {
	// box2DWorld variables
	
	public SpriteBatch batch;
	public MainGameScreen(SimpleRPG game) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		batch = new SpriteBatch();
		GameStateManager.init(game.batch);	
	}
	
	@Override
	public void show() {
		
		
	}

	@Override
	public void render(float delta) {
		float del = Gdx.graphics.getDeltaTime();
		
		Gdx.gl.glClearColor(0.15f, 0.05f, 0.05f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		GameStateManager.update(del);
		GameStateManager.render(del);
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		
		
	}

	@Override
	public void dispose() {
		GameStateManager.dispose();
	}

}
