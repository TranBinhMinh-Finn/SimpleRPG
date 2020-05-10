package com.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.game.SimpleRPG;
import com.game.entity.Big_Demon;
import com.game.entity.Bullet;
import com.game.entity.Player;
public class MainGameScreen implements Screen {

	SimpleRPG game;
	World world;
	Player danchoi1;
	ArrayList<Bullet> bullets;
	Big_Demon quai1;
	
	float timeStep = 1f/60f;
	int velocityIterations = 6, positionIterations = 3;
	
	
	Box2DDebugRenderer renderer;
	Matrix4 debugMatrix;
	OrthographicCamera cam;
	
	public MainGameScreen(SimpleRPG game)
	{
		this.game = game;
		world = new World(new Vector2(0,0),false);
		bullets = new ArrayList<Bullet>();
		danchoi1 = new Player(game,0,0,world);
		quai1 = new Big_Demon(game,100,100,world);
		
		float w = Gdx.graphics.getWidth();                                      
		float h = Gdx.graphics.getHeight();                         
		cam = new OrthographicCamera(w*500,h*500);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		debugMatrix = new Matrix4(cam.combined);
		debugMatrix.scale(100, 100, 1);
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
		// entity behavior code 
		
		game.batch.begin();
		danchoi1.inputQuery(del,bullets);
		quai1.actionQuery(del);
		
		//renderer.render(world,debugMatrix);
		world.step(timeStep, velocityIterations, positionIterations);
		
		for(Bullet i : bullets) {
			i.render(game.batch);
		}
		danchoi1.render(del);
		quai1.render(del);
		
		
		
		
		game.batch.end();
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
