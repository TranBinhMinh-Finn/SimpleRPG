package com.game.map;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.Chest;
import com.game.entity.Player;
import com.game.entity.enemy.Big_Demon;
import com.game.entity.enemy.Chort;
import com.game.entity.enemy.Enemy;
import com.game.entity.enemy.Imp;
import com.game.entity.enemy.Wogol;
import com.game.utils.MapParserUtil;

public class Map {
	Wall[] wallList;
	River[] riverList;
	Room[] roomList;
	public Slow[] slowList;
	public Enemy[] enemyList;
	ArrayList<KeyPoint> keyPoint;
	public Chest chest;
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
		keyPoint = new ArrayList<KeyPoint>();
		map = new TmxMapLoader().load("Maps/dungeonMap.tmx");
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
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("Imp").getObjects(),mapScale, Imp.class,this);
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("KeyPoint").getObjects(),mapScale, KeyPoint.class,this);
		MapParserUtil.parseTiledObjectLayer(world, map.getLayers().get("Chest").getObjects(),mapScale, Chest.class,this);
		organizeMobs();
		organizeKeyPoints();
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
	static final float inf = (float) Math.pow(2,30);
	public void organizeKeyPoints()
	{
		for(KeyPoint i:keyPoint)
		{
			for(KeyPoint j:keyPoint)
			{
				if(i==j) continue;
				if(!i.rayCast(j.getPosition()))
				{
					i.getAdjacent().add(keyPoint.indexOf(j));
				}
			}
			//System.out.println(i.getPosition().x + " "  + i.getPosition().y);
		}
	}
	public ArrayList<Vector2>findPath(Vector2 pointA, Vector2 pointB)
	{
		ArrayList<Vector2> path = new ArrayList<Vector2>();
		ArrayList<Vector2> returnPath = new ArrayList<Vector2>();
		int trace[] = new int[200];
		final float f[] = new float[200];
		PriorityQueue<Integer> q = new PriorityQueue<Integer>(1000, new Comparator<Integer>() {
			public int compare(Integer p,Integer q)
			{
				if(f[p] - f[q]<0f)
					return -1;
				if(f[p] - f[q]>0f)
					return 1;
				return 0;
			}
		});
		for(int i=0;i<keyPoint.size();++i)
		{
			if(!keyPoint.get(i).rayCast(pointA))
			{
				f[i] = pointA.dst(keyPoint.get(i).getPosition());
				q.add(i);
			}
			else
				f[i] = inf;
			trace[i] = i;
		}
		while(!q.isEmpty())
		{
			int u = q.poll();
			if(!keyPoint.get(u).rayCast(pointB))
			{
				do
				{
					path.add(keyPoint.get(u).getPosition());
					u = trace[u];
				}while(trace[u]!=u);
				break;
			}
			for(Integer i: keyPoint.get(u).getAdjacent())
			{
				if(f[i]> f[u] + keyPoint.get(u).getPosition().dst(keyPoint.get(i).getPosition()))
				{
					f[i] = f[u] + keyPoint.get(u).getPosition().dst(keyPoint.get(i).getPosition());
					trace[i] = u;
					q.add(i);
				}
			}
		}
		for(int i = path.size()-1; i>=0; --i)
			returnPath.add(path.get(i));
		return returnPath;
		
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
		if(object instanceof Enemy)
		{
			Enemy enemy = (Enemy) object;
			enemyListSize++;
			enemyList[enemyListSize]=enemy;
		}
		if(object instanceof KeyPoint)
			keyPoint.add((KeyPoint)object);
		if(object instanceof Chest)
			chest = (Chest) object;
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
	public Batch getBatch()
	{
		return tmr.getBatch();
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
