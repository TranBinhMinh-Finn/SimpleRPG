package com.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.game.SimpleRPG;
import com.game.entity.Big_Demon;
import com.game.entity.Bullet;
import com.game.entity.CameraStyles;
import com.game.entity.Player;
public class MainGameScreen implements Screen {
	SimpleRPG game;
	Player danchoi1;
	ArrayList<Bullet> bullets;
	Big_Demon quai1;
	float scale = 3;
	final float GAME_WORLD_WIDTH = scale * 1725 ; /// map width
	final float GAME_WORLD_HEIGHT =scale * 1600; /// map height
	
	OrthographicCamera camera;
	Texture rec = new Texture("bullet.png");
	public MainGameScreen(SimpleRPG game)
	{
		this.game = game;
		bullets = new ArrayList<Bullet>();
		danchoi1 = new Player(game,901,4579);
		quai1 = new Big_Demon(game,800,800);
		
		float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
		this.game.sprite = new Sprite(new Texture(Gdx.files.internal("map1.png")));
		this.game.sprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		camera = new OrthographicCamera(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		camera.translate(camera.viewportWidth/2 , camera.viewportHeight/2);
		
		
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
		///end bullet code  
		
		///camera test
		CameraStyles.lockOnTarget(camera, danchoi1.x , danchoi1.y);
		/// end camera test
		
		game.batch.begin(); /// begin here -------------------------------------
		game.sprite.draw(game.batch);
		
		for(Bullet i : bullets) {
			i.render(game.batch);
		}
		
		danchoi1.inputQuery(del,bullets);
		quai1.actionQuery(del);
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.draw(rec , 0, 0 , 16 * scale , 16 * scale);
		game.batch.end(); /// end here ----------------------------------------------
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
