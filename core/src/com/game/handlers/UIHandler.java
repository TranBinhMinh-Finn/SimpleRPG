package com.game.handlers;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.SimpleRPG;
import com.game.entity.Player;
import com.game.screens.MainGameScreen;
import com.game.screens.MainMenuScreen;

public class UIHandler {
	private static final int BUTTON_WIDTH = 823/2;
	private static final int BUTTON_HEIGHT = 314/2;
	private static Stage stage;
	private static Image healthBarUI;
	private static Image healthBar;
	static OrthographicCamera camera;
	private static final float healthBarScale = 240;
	private static final float scale = 5;
	
	static Texture button;
	static Texture background;
	static OrthographicCamera gameOverCamera;
	static SpriteBatch batch;
	private static BitmapFont font_black, font_end;
	
	public static void init()
	{
		camera = new OrthographicCamera(SimpleRPG.WIDTH ,SimpleRPG.HEIGHT);
		stage = new Stage(new FitViewport(SimpleRPG.WIDTH ,SimpleRPG.HEIGHT, camera));
		
		//Gdx.input.setInputProcessor(stage);
		
		Texture texture = new Texture("UI/healthUI.png");
		healthBarUI = new Image(texture);
		healthBarUI.setPosition(30, SimpleRPG.HEIGHT - 100);
		healthBarUI.setScale(scale);
		stage.addActor(healthBarUI);
		
		texture = new Texture("UI/healthBar.png");
		healthBar = new Image(texture);
		healthBar.setPosition(115, SimpleRPG.HEIGHT - 65);
		healthBar.setScale(scale);
		healthBar.setScaleX(healthBarScale);
		stage.addActor(healthBar);
	
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("UI/Awkward.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 100;
		font_black = generator.generateFont(parameter);
		font_black.setColor(0f, 0f, 0f, 1f);
		parameter.size = 200;
		font_end = generator.generateFont(parameter);
		generator.dispose();
		
		gameOverCamera = new OrthographicCamera(SimpleRPG.WIDTH ,SimpleRPG.HEIGHT);
		gameOverCamera.translate(camera.viewportWidth/2 , camera.viewportHeight/2);
		
		batch = new SpriteBatch();
		button = new Texture("UI/MenuButton.png");
		background = new Texture("UI/MenuBackground.png");
		batch.setProjectionMatrix(camera.combined);
	}
	
	public static void update(float delta, Player player)
	{
		float healthPercentage = (float)player.getHP() / Player.player_hp;
		
		if(healthPercentage < 0)
			healthPercentage = 0;
		healthBar.setScaleX(healthBarScale * healthPercentage); 
		stage.act(delta);
		if(player.getHP() <= 0)
		{
			stage.getBatch().setColor(Color.GRAY);
		}
	}
	private static  float X,Y;
	private static  float x,y;
	static Vector3 touchPos;
	public static void update(float delta)
	{
		
		x = SimpleRPG.WIDTH/2 - BUTTON_WIDTH/2;
		y = SimpleRPG.HEIGHT/2 - BUTTON_HEIGHT/2;	
		
		X = Gdx.input.getX();
		Y = Gdx.input.getY();
		
		
		touchPos = new Vector3(X,Y,0); 
		touchPos = camera.unproject(touchPos);
		X = touchPos.x;
		Y = touchPos.y;
		
		if(X < SimpleRPG.WIDTH/2 - BUTTON_WIDTH/2 + BUTTON_WIDTH && X> x && Y < y + BUTTON_HEIGHT && Y> y)
		{
			if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
			{
				SoundManager.playButtonSound();
				GameStateManager.returnToMenu();
			}
		}
		
	}
	public static void gameOverRender(String s, Color color)
	{
		batch.begin();
		batch.draw(button,x,y,BUTTON_WIDTH,BUTTON_HEIGHT);
		font_end.setColor(color);
		font_end.draw(batch, s, SimpleRPG.WIDTH/2 - 230 ,y + 250);
		font_black.draw(batch, "Menu", x + 150 ,y + 100);
		batch.end();
	}
	public static void render(float delta)
	{
		stage.draw();
	
	}
}
