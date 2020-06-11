package com.game.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.game.SimpleRPG;
import com.game.entity.Bullet;
import com.game.entity.Enemy;
import com.game.entity.Player;
import com.game.map.Map;
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
	private static ArrayList<Enemy> monsterList ;
	
	//map and map renderer
	private static Map map;
	private static OrthographicCamera camera;
	static boolean inSlow;
	public static void init(SpriteBatch batch) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		GameStateManager.batch = batch;
		world = new World(new Vector2(0f,0f),false);
		world.setContactListener(new WorldContactListener());
		
		map = new Map(world);
	
		player = new Player(map.spawnPoint.x,map.spawnPoint.y,world);
		monsterList = new ArrayList<Enemy>();
		for(int i=1;i<=map.enemyListSize;++i)
			monsterList.add(map.enemyList[i]);
		bullets = new ArrayList<Bullet>();
		
		camera = new OrthographicCamera(SimpleRPG.WIDTH , SimpleRPG.HEIGHT);
		camera.translate(camera.viewportWidth/2 , camera.viewportHeight/2);
		//Setting up Box2D Debug Renderer
		renderer = new Box2DDebugRenderer();
		
		UIHandler.init();
	}
	public static void update(float del)
	{	
		// removing dead mobs / bullets landed
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		ArrayList<Enemy> mobsToRemove = new ArrayList<Enemy>();
		for(Bullet i : bullets) 
		{
			//i.update(del);
			if(i.remove == true)
			{
				bulletsToRemove.add(i);
				i.dispose();
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
		//System.out.println(player.getXByPixels() + " " + player.getYByCenter());
	}
	public static void render(float del)
	{
		
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
		
		batch.end(); 
		
		map.renderWallLayer(); // renders the wall layer of the map
		
		debugMatrix = new Matrix4(camera.combined);     
		debugMatrix.scale(Constants.BOX2D_SCALE, Constants.BOX2D_SCALE, 1);
		//renderer.render(world,debugMatrix);  //renders the debug Box2D world
		
		UIHandler.render(del);
	}
	public static void dispose()
	{
		world.dispose();
	}
}
