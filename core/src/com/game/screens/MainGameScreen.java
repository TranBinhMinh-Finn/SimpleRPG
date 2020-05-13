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
import com.game.Constants;
import com.game.SimpleRPG;
import com.game.entity.Big_Demon;
import com.game.entity.Bullet;
import com.game.entity.Mob;
import com.game.entity.Player;
import com.game.handlers.WorldContactListener;
public class MainGameScreen implements Screen {

	SimpleRPG game;
	World world;
	Player player;
	ArrayList<Bullet> bullets;
	ArrayList<Mob> monsterList ;
	
	float timeStep = 1f/60f;
	int velocityIterations = 6, positionIterations = 3;
	
	
	Box2DDebugRenderer renderer;
	Matrix4 debugMatrix;
	OrthographicCamera cam;
	
	public MainGameScreen(SimpleRPG game)
	{
		this.game = game;
		world = new World(new Vector2(0f,0f),false);
		bullets = new ArrayList<Bullet>();
		player = new Player(game,0,0,world);
		monsterList = new ArrayList<Mob>();
		monsterList.add(new Big_Demon(game,100,100,world));
		world.setContactListener(new WorldContactListener());
		
		//Setting up Box2D Debug Renderer
		renderer = new Box2DDebugRenderer();
		float w_ = Gdx.graphics.getWidth()/2;                                      
		float h_ = Gdx.graphics.getHeight()/2;                         
		cam = new OrthographicCamera(w_,h_);
		cam.setToOrtho(false, w_ / Constants.BOX2D_SCALE, h_ / Constants.BOX2D_SCALE);
		debugMatrix = new Matrix4(cam.combined);
		debugMatrix.scale(0.5f, 0.5f, 1);
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
		ArrayList mobsToRemove = new ArrayList<Mob>();
		for(Bullet i : bullets) {
			i.update(del);
			if(i.remove == true) {
				bulletsToRemove.add(i);
				i.dispose();
			}
		}
		for(Mob i : monsterList)
		{
			if(i.remove)
			{
				i.dispose();
				mobsToRemove.add(i);
			}
		}
		bullets.removeAll(bulletsToRemove);
		monsterList.removeAll(mobsToRemove);
		// entity behavior code 
		player.inputQuery(del,bullets);
		//quai1.actionQuery(del);
		
		game.batch.begin();
		world.step(timeStep, velocityIterations, positionIterations);
		
		for(Bullet i : bullets) {
			i.render(game.batch);
		}
		player.render(del);
		
		for(Mob i : monsterList)
		{
			Big_Demon tmp  = (Big_Demon) i ;
			tmp.render(del);
		}
		game.batch.end();
		
		//rendering the debug Box2D world
		//renderer.render(world,debugMatrix);
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
