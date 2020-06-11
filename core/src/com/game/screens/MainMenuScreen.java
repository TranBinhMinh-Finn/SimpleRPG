package com.game.screens;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.SimpleRPG;


public class MainMenuScreen implements Screen {

	private static final int BUTTON_WIDTH = 1036/5;
	private static final int BUTTON_HEIGHT = 512/5;
	public static final int START_BUTTON_X = SimpleRPG.WIDTH/2 - BUTTON_WIDTH/2;;
	public static final int START_BUTTON_Y = SimpleRPG.HEIGHT/2 - BUTTON_HEIGHT/2+100;
	public static final int EXIT_BUTTON_X = SimpleRPG.WIDTH/2 - BUTTON_WIDTH/2;;
	public static final int EXIT_BUTTON_Y = SimpleRPG.HEIGHT/2 - BUTTON_HEIGHT/2-100;
	SimpleRPG game;
	
	Texture playButtonActive;
	Texture playButtonInactive;
	Texture exitButtonActive;
	Texture exitButtonInactive;
	OrthographicCamera camera;
	Stage stage;
	public MainMenuScreen(SimpleRPG game) {
		this.game = game;
		camera = new OrthographicCamera(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		stage = new Stage(new FitViewport(SimpleRPG.WIDTH, SimpleRPG.HEIGHT, camera));
		
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
		int y_exit = EXIT_BUTTON_Y;
		int y_start = START_BUTTON_Y;
		//game.batch.draw(playButtonActive,100,400,BUTTON_WIDTH,BUTTON_HEIGHT);
		float X = Gdx.input.getX();
		float Y = Gdx.graphics.getHeight() - Gdx.input.getY();
		if(X < x + BUTTON_WIDTH && X> x && Y < y_exit + BUTTON_HEIGHT && Y> y_exit)
		{
			game.batch.draw(exitButtonActive,x,y_exit,BUTTON_WIDTH,BUTTON_HEIGHT);
			if(Gdx.input.isTouched())
				Gdx.app.exit();
		}
		else
			game.batch.draw(exitButtonInactive,x,y_exit,BUTTON_WIDTH,BUTTON_HEIGHT);
		if(X < x + BUTTON_WIDTH && X> x && Y < y_start + BUTTON_HEIGHT && Y > y_start)
		{	
			game.batch.draw(playButtonActive,x,y_start,BUTTON_WIDTH,BUTTON_HEIGHT);
			if(Gdx.input.isTouched())
			{
				this.dispose();
				try {
					game.setScreen(new MainGameScreen(game));
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
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
