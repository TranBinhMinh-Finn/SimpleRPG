package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.game.SimpleRPG;
import com.game.entity.Player;
public class MainGameScreen implements Screen {

	SimpleRPG game;
	Player danchoi1;
	Player danchoi2;
	public MainGameScreen(SimpleRPG game)
	{
		this.game = game;
		danchoi1 = new Player(game,0,0);
		danchoi2 = new Player(game,Player.CHAR_WIDTH*Player.scale,Player.CHAR_HEIGHT*Player.scale);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.2f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float del = Gdx.graphics.getDeltaTime();
		danchoi1.inputQuery(del);
		danchoi2.inputQuery(del);
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
