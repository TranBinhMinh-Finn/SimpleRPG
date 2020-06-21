package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.SimpleRPG;
import com.game.handlers.SoundManager;
import com.game.utils.Constants;


public class OptionsScreen implements Screen {

	private static final int BUTTON_WIDTH = 823/2;
	private static final int BUTTON_HEIGHT = 314/2;
	public static final int EASY_BUTTON_Y = SimpleRPG.HEIGHT/2 - 50;
	public static final int HARD_BUTTON_Y = SimpleRPG.HEIGHT/2 - 200;
	public static final int EXIT_BUTTON_Y = SimpleRPG.HEIGHT/2 - 350;
	SimpleRPG game;
	
	Texture button;
	Texture background;
	OrthographicCamera camera;
	Stage stage;
	public OptionsScreen(SimpleRPG game) {
		this.game = game;
		camera = new OrthographicCamera(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		camera.translate(camera.viewportWidth/2 , camera.viewportHeight/2);
		stage = new Stage(new FitViewport(SimpleRPG.WIDTH, SimpleRPG.HEIGHT, camera));
		
		button = new Texture("UI/MenuButton.png");
		background = new Texture("UI/MenuBackground.png");
		game.batch.setProjectionMatrix(camera.combined);
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	private float X,Y;
	private Vector3 touchPos;
	private float x,y_easy, y_exit,y_hard;
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.5f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		game.batch.begin();
		game.batch.draw(background, 0 , 0, SimpleRPG.WIDTH, SimpleRPG.HEIGHT);
		x = SimpleRPG.WIDTH/2 - BUTTON_WIDTH/2;
		y_exit = EXIT_BUTTON_Y;
		y_easy = EASY_BUTTON_Y;
		y_hard = HARD_BUTTON_Y;
		X = Gdx.input.getX();
		Y = Gdx.input.getY();
		
		
		touchPos = new Vector3(X,Y,0); 
		touchPos = camera.unproject(touchPos);
		X = touchPos.x;
		Y = touchPos.y;
		
		if(X < x + BUTTON_WIDTH && X> x && Y < y_exit + BUTTON_HEIGHT && Y> y_exit)
		{
			this.dispose();
			if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
			{
				game.setScreen(new MainMenuScreen(game));
				SoundManager.playButtonSound();
			}
		}
		
		if(X < x + BUTTON_WIDTH && X> x && Y < y_easy + BUTTON_HEIGHT && Y > y_easy)
		{	
			if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
			{
				SoundManager.playButtonSound();
				Constants.DIFFICULTY_MULTIPLIER = Constants.EASY_MULTIPLIER;
			}
		}
		if(X < x + BUTTON_WIDTH && X> x && Y < y_hard + BUTTON_HEIGHT && Y > y_hard)
		{	
			if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
			{
				SoundManager.playButtonSound();
				Constants.DIFFICULTY_MULTIPLIER = Constants.HARD_MULTIPLIER;
			}
		}
		game.batch.draw(button,x,y_easy,BUTTON_WIDTH,BUTTON_HEIGHT);
		game.batch.draw(button,x,y_hard,BUTTON_WIDTH,BUTTON_HEIGHT);
		game.batch.draw(button,x,y_exit,BUTTON_WIDTH,BUTTON_HEIGHT);
		game.font_white.draw(game.batch, "Choose Difficulty", SimpleRPG.WIDTH/2 - 320 ,EASY_BUTTON_Y + 250);
		
		game.font_black.draw(game.batch, "Easy", SimpleRPG.WIDTH/2 - 40 ,EASY_BUTTON_Y + 100);
		game.font_black.draw(game.batch, "Hard", SimpleRPG.WIDTH/2 - 40 ,HARD_BUTTON_Y + 100);
		game.font_black.draw(game.batch, "Return", SimpleRPG.WIDTH/2 - 45 ,EXIT_BUTTON_Y + 100);
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
