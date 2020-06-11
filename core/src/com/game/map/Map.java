package com.game.map;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.Big_Demon;
import com.game.entity.Chort;
import com.game.entity.Enemy;
import com.game.entity.Player;
import com.game.entity.Wogol;
import com.game.utils.MapParserUtil;

public class Map {
	private ArrayList<Wall> walls;
	private ArrayList<River> rivers;
	private ArrayList<Room> rooms;
	
	Wall[] wallList;
	River[] riverList;
	Room[] roomList;
	public Slow[] slowList;
	public Enemy[] enemyList;
	int wallListSize;
	int riverListSize;
	public int roomListSize;
	public int slowListSize;
	public int enemyListSize;
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;
	float mapScale = 3;
	private int[] wallIndices;
	private int[] groundIndices;
	public Vector2 spawnPoint;
	public Map(World world) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		wallList = new Wall[100];
		riverList = new River[100] ;
		roomList = new Room[100];
		slowList = new Slow[100];
		enemyList= new Enemy[100];
		spawnPoint = new Vector2();
		map = new TmxMapLoader().load("Maps/map.tmx");
		tmr = new OrthogonalTiledMapRenderer(map,mapScale);
		wallIndices = new int[] {
				map.getLayers().getIndex("WallTiles"),
		};
		groundIndices = new int[] {
				map.getLayers().getIndex("GroundTiles"),
		};
		
		
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("Wall").getObjects(),mapScale, Wall.class,this);
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("River").getObjects(),mapScale, River.class,this);
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("Slow").getObjects(),mapScale, Slow.class,this);
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("Room").getObjects(),mapScale, Room.class,this);
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("SpawnPoint").getObjects(),mapScale, Player.class,this);
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("BigDemon").getObjects(),mapScale, Big_Demon.class,this);
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("Wogol").getObjects(),mapScale, Wogol.class,this);
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("Chort").getObjects(),mapScale, Chort.class,this);
		organizeMobs();
	}
	void organizeMobs()
	{
		for(int i=1;i<=enemyListSize;++i)
		{
			for(int j=1;j<=roomListSize;++j)
			{
				if(roomList[j].inRoom(enemyList[i].getXByPixels(), enemyList[i].getYByPixels()))
				{
					enemyList[i].currentRoomId = j;
					roomList[j].enemyCount++;
				}
			}
		}
	}
	public void setSpawnPoint(Vector2 spawnPoint)
	{
		this.spawnPoint = new Vector2(spawnPoint);
	}
	public void add(Wall wall)
	{
		wallListSize++;
		wallList[wallListSize]=wall;
	}
	public void add(River river)
	{
		riverListSize++;
		riverList[riverListSize]=river;
	}
	public void add(Room room)
	{
		roomListSize++;
		roomList[roomListSize]=room;
	}
	public void add(Slow slow)
	{
		slowListSize++;
		slowList[slowListSize]=slow;
	}
	public void add(Object object, Class<?> objectClass)
	{
		Enemy enemy = (Enemy) object;
		enemyListSize++;
		enemyList[enemyListSize]=enemy;
	}
	public Room getRoom(int id)
	{
		return roomList[id];
	}
	public Slow getSlow(int id)
	{
		return slowList[id];
	}
	public void setCamera(OrthographicCamera camera)
	{
		tmr.setView(camera);
	}
	public void renderGroundLayer()
	{
		tmr.render(groundIndices);
	}
	public void renderWallLayer()
	{
		tmr.render(wallIndices);
	}
}
