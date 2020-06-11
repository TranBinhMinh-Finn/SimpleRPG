package com.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.SimpleRPG;
import com.game.entity.Player;

public class UIHandler {
	private static Stage stage;
	private static Image healthBarUI;
	private static Image healthBar;
	static OrthographicCamera camera;
	private static final float healthBarScale = 240;
	private static final float scale = 5;
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
	}
	public static void update(float delta, Player player)
	{
		float healthPercentage = (float)player.getHP() / Player.player_hp;
		if(healthPercentage < 0)
			healthPercentage = 0;
		healthBar.setScaleX(healthBarScale * healthPercentage); 
		stage.act(delta);
	}
	public static void render(float delta)
	{
		stage.draw();
	}
}
