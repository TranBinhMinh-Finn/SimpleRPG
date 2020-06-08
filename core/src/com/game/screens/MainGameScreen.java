package com.game.screens;

import java.lang.reflect.InvocationTargetException;
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
import com.game.entity.Bullet;
import com.game.entity.Enemy;
import com.game.entity.Mob;
import com.game.entity.Player;
import com.game.handlers.CameraStyles;
import com.game.handlers.GameStateHandler;
import com.game.handlers.InputHandler;
import com.game.handlers.WorldContactListener;
import com.game.map.Map;
import com.game.utils.Constants;

public class MainGameScreen implements Screen {
	private SimpleRPG game;
	// box2DWorld variables
	private World world;
	private float timeStep = 1f/60f;
	private int velocityIterations = 6, positionIterations = 3;
	private Box2DDebugRenderer renderer;
	private Matrix4 debugMatrix;
	
	//in game objects
	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Enemy> monsterList ;
	
	//map and map renderer
	private Map map;
	private OrthographicCamera camera;
	
	
	
	public MainGameScreen(SimpleRPG game) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		this.game = game;
		world = new World(new Vector2(0f,0f),false);
		world.setContactListener(new WorldContactListener());
		
		map = new Map(world);
	
		player = new Player(game,map.spawnPoint.x,map.spawnPoint.y,world);
		monsterList = new ArrayList<Enemy>();
		for(int i=1;i<=map.enemyListSize;++i)
			monsterList.add(map.enemyList[i]);
		bullets = new ArrayList<Bullet>();
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		camera.translate(camera.viewportWidth/2 , camera.viewportHeight/2);
		
		//Setting up Box2D Debug Renderer
		renderer = new Box2DDebugRenderer();
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		float del = Gdx.graphics.getDeltaTime();
		
		Gdx.gl.glClearColor(0.15f, 0.05f, 0.05f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// removing dead mobs / bullets landed
		
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		ArrayList<Mob> mobsToRemove = new ArrayList<Mob>();
		for(Bullet i : bullets) {
			//i.update(del);
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
				map.getRoom(i.currentRoomId).enemyCount--;
				mobsToRemove.add(i);
			}
		}
		
		
		bullets.removeAll(bulletsToRemove);
		monsterList.removeAll(mobsToRemove);
		
		// calls the input handler
		
		InputHandler.keyHandler(del,player);
		InputHandler.mouseClickHandler(del,player, bullets, camera);
		GameStateHandler.update(map,player);
		world.step(timeStep, velocityIterations, positionIterations); //box2DWorld steps
		
		
		CameraStyles.cameraUpdate(camera, map, player); //camera movements
		 
		map.setCamera(camera);
		map.renderGroundLayer(); // renders the ground layer of the map
		for(Enemy i : monsterList)
		{	 //renders mobs
			if(i.type == Enemy.melee)
				i.actionQuery(player, del);
			if(i.type == Enemy.ranged)
				i.actionQuery(player,bullets, del);
		}
		game.batch.begin(); 
	
		game.batch.setProjectionMatrix(camera.combined);
		
		for(Bullet i : bullets) {
			i.render(game.batch);   //renders bullets
		}
		player.render(del,game.batch);
		
		for(Enemy i : monsterList)
		{	 //renders mobs
			i.render(del,game.batch);
		}
		
		game.batch.end(); 
		
		map.renderWallLayer(); // renders the wall layer of the map
		
		debugMatrix = new Matrix4(camera.combined);     
		debugMatrix.scale(Constants.BOX2D_SCALE, Constants.BOX2D_SCALE, 1);
		//renderer.render(world,debugMatrix);  //renders the debug Box2D world
		
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
		world.dispose();
		
		
	}

}
