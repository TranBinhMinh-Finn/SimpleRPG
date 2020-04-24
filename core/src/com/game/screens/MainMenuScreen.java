package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.game.SimpleRPG;


public class MainMenuScreen implements Screen {

	private static final int BUTTON_WIDTH = 1036/5;
	private static final int BUTTON_HEIGHT = 512/5;
	public static final int EXIT_BUTTON_X = 100;
	public static final int EXIT_BUTTON_Y = 100;
	SimpleRPG game;
	
	Texture playButtonActive;
	Texture playButtonInactive;
	Texture exitButtonActive;
	Texture exitButtonInactive;
	public MainMenuScreen(SimpleRPG game) {
		this.game = game;
		playButtonActive = new Texture("playButtonActive.png");
		playButtonInactive = new Texture("playButtonInactive.png");
		exitButtonActive = new Texture("exitButtonActive.png");
		exitButtonInactive = new Texture("exitButtonInactive.png");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.5f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		int x = SimpleRPG.WIDTH/2 - BUTTON_WIDTH/2;
		int y_exit = 75;
		int y_start = 275;
		//game.batch.draw(playButtonActive,100,400,BUTTON_WIDTH,BUTTON_HEIGHT);
		if(Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX()> x && SimpleRPG.HEIGHT - Gdx.input.getY() < y_exit + BUTTON_HEIGHT && SimpleRPG.HEIGHT - Gdx.input.getY()> y_exit)
		{
			game.batch.draw(exitButtonActive,x,y_exit,BUTTON_WIDTH,BUTTON_HEIGHT);
			if(Gdx.input.isTouched())
				Gdx.app.exit();
		}
		else
			game.batch.draw(exitButtonInactive,x,y_exit,BUTTON_WIDTH,BUTTON_HEIGHT);
		if(Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX()> x && SimpleRPG.HEIGHT - Gdx.input.getY() < y_start + BUTTON_HEIGHT && SimpleRPG.HEIGHT - Gdx.input.getY()> y_start)
		{	
			game.batch.draw(playButtonActive,x,y_start,BUTTON_WIDTH,BUTTON_HEIGHT);
			if(Gdx.input.isTouched())
			{
				this.dispose();
				//game.setScreen(new MainGameScreen(game));
			}
		}
		else
			game.batch.draw(playButtonInactive,x,y_start,BUTTON_WIDTH,BUTTON_HEIGHT);
		game.batch.end();
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
		
		
	}
	
}
