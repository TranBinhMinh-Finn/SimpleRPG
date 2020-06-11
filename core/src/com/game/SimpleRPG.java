package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.game.screens.MainMenuScreen;

public class SimpleRPG extends Game {
	public SpriteBatch batch;
	public Sprite sprite ;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public BitmapFont font_black, font_white;
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("UI/Awkward.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 100;
		font_black = generator.generateFont(parameter);
		font_black.setColor(0f, 0f, 0f, 1f);
		parameter.size = 200;
		font_white = generator.generateFont(parameter);
		font_white.setColor(1f, 1f, 1f, 1f);
		generator.dispose();
		
	}

	@Override
	public void render () {
		super.render();
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//sprite.getTexture().dispose();
	}
}
