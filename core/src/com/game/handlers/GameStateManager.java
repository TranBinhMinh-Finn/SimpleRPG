package com.game.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.game.SimpleRPG;
import com.game.entity.Bullet;
import com.game.entity.Entity;
import com.game.entity.Player;
import com.game.entity.enemy.Enemy;
import com.game.map.Map;
import com.game.screens.MainMenuScreen;
import com.game.utils.Constants;

public class GameStateManager {
	
	private static SpriteBatch batch;
	// box2DWorld variables
	private static World world;
	private static float timeStep = 1f/60f;
	private static int velocityIterations = 6, positionIterations = 3;
	private static Box2DDebugRenderer renderer;
	private static Matrix4 debugMatrix;
		
	//in game objects
	private static Player player;
	private static ArrayList<Bullet> bullets;
	private static ArrayList<Bullet> bulletsEffects;
	private static ArrayList<Enemy> monsterList ;
	
	//map and map renderer
	private static Map map;
	private static OrthographicCamera camera;
	static boolean inSlow;
	static SimpleRPG game;
	public static void init(SimpleRPG game) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		GameStateManager.game = game;
		GameStateManager.batch = new SpriteBatch();
		world = new World(new Vector2(0f,0f),false);
		world.setContactListener(new WorldContactListener());
		line = new ArrayList<Vector2>() ;
		map = new Map(world);
		
		player = new Player(map.spawnPoint.x,map.spawnPoint.y,world);
		monsterList = new ArrayList<Enemy>();
		for(int i=1;i<=map.enemyListSize;++i)
			monsterList.add(map.enemyList[i]);
		bullets = new ArrayList<Bullet>();
		bulletsEffects =  new ArrayList<Bullet>();
		camera = new OrthographicCamera(SimpleRPG.WIDTH , SimpleRPG.HEIGHT);
		camera.translate(camera.viewportWidth/2 , camera.viewportHeight/2);
		//Setting up Box2D Debug Renderer
		renderer = new Box2DDebugRenderer();
		SoundManager.init();
		UIHandler.init();
		SoundManager.playBGM();
		
		
	}
	private static ArrayList<Bullet> bulletsToRemove;
	private static ArrayList<Bullet> effectsToRemove ;
	private static ArrayList<Enemy> mobsToRemove;
	
	private static boolean gameOver()
	{
		if(player.currentRoomId == 4)
			return true;
		if(player.getHP()<=0)
			return true;
		return false;
	}
	public static ArrayList<Vector2> line;
	public static void update(float del)
	{	
		// removing dead mobs / bullets landed
		//line.clear();
		if(gameOver())
		{
			UIHandler.update(del,player);
			UIHandler.update(del);
			SoundManager.stopBGM();
			SoundManager.playGameOverBGM();
			return ;
		}
		bulletsToRemove = new ArrayList<Bullet>();
		effectsToRemove = new ArrayList<Bullet>();
		mobsToRemove = new ArrayList<Enemy>();
		for(Bullet i : bullets) 
		{
			//i.update(del);
			if(i.remove == true)
			{
				bulletsToRemove.add(i);
				i.dispose();
				bulletsEffects.add(i);
			}
		}
		for(Bullet i : bulletsEffects) 
		{
			//i.update(del);
			if(i.removeEffect == true)
			{
				effectsToRemove.add(i);
			}
		}
		for(Enemy i : monsterList)
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
		bulletsEffects.removeAll(effectsToRemove);
		// calls the input handler
				
		InputHandler.keyHandler(del,player);
		InputHandler.mouseClickHandler(del,player, bullets, camera);
		player.slowed = 1f;
		for(int i=1;i<=map.slowListSize;++i)
		{
			if(map.getSlow(i).inSlowRange(player.getXByPixels(),player.getYByPixels()))
			{
				player.slowed = 0.5f;
				break;
			}
		}
		for(Enemy i : monsterList) // Enemy behavior
		{	
			if(i.type == Enemy.melee)
				i.actionQuery(player, del);
			if(i.type == Enemy.ranged)
				i.actionQuery(player,bullets, del);
		}
		world.step(timeStep, velocityIterations, positionIterations); //box2DWorld steps
				
		CameraStyles.cameraUpdate(camera, map, player); //camera movements	 
		map.setCamera(camera);
		
		UIHandler.update(del,player);
		//System.out.println("Player:" + player.getBody().getPosition().x + " " + player.getBody().getPosition().y);
	}
	public static void returnToMenu()
	{
		if(gameOver())
			SoundManager.stopGameOverBGM();
		game.getScreen().dispose();
		game.setScreen(new MainMenuScreen(game));
		
	}
	public static void render(float del)
	{
		if(gameOver())
		{
			batch.setColor(Color.GRAY);
			map.getBatch().setColor(Color.GRAY);
		}
		map.renderGroundLayer(); // renders the ground layer of the map
		batch.begin(); 
		
		batch.setProjectionMatrix(camera.combined);
		
		for(Bullet i : bullets) {
			i.render(batch);   //renders bullets
		}			
		player.render(del,batch);
		
		for(Enemy i : monsterList)
		{	 //renders mobs
			i.render(del,batch);
		}
		for(Bullet i : bulletsEffects) {
			i.renderEffect(batch,del);   //renders bullets effects
		}
		batch.end(); 
		
		map.renderWallLayer(); // renders the wall layer of the map
		
		
		
		if(gameOver())
		{
			UIHandler.gameOverRender();
		}
		else
			UIHandler.render(del);
		
		/*debugMatrix = new Matrix4(camera.combined);     
		debugMatrix.scale(Constants.BOX2D_SCALE, Constants.BOX2D_SCALE, 1);
		renderer.render(world,debugMatrix);  //renders the debug Box2D world
		*/
		/*
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setProjectionMatrix(debugMatrix);
		if(!line.isEmpty())
		for(int i=0;i<line.size()-2;++i)
		{
			shapeRender(line.get(i),line.get(i+1));
		}
		shapeRenderer.end();*/
	}
	static ShapeRenderer shapeRenderer = new ShapeRenderer();
	public static Player getPlayer()
	{
		return player;
	}
	public static Map getMap()
	{
		return map;
	}
	public static void shapeRender(Vector2 rayStart, Vector2 rayEnd)
	{	
		shapeRenderer.line(rayStart,rayEnd);
		shapeRenderer.setColor(Color.BLUE);
		
	}
	public static void dispose()
	{
		world.dispose();
	}
}
