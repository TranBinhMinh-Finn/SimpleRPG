package com.game.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.Player;
import com.game.map.Map;
import com.game.map.River;
import com.game.map.Room;
import com.game.map.Slow;
import com.game.map.Wall;

public class MapParserUtil {
	public static void parseTiledObjectLayer(World world, MapObjects objects,float mapScale,Class<?> objectClass, Map map) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		for(MapObject object: objects)
		{
			Shape shape;
			if(object instanceof PolylineMapObject)
			{
				shape = createPolyline((PolylineMapObject) object, mapScale);
			}
			else if(object instanceof PolygonMapObject)
			{
				shape  = createPolygon((PolygonMapObject)object,mapScale);
			}
			else if(object instanceof RectangleMapObject)
			{
				shape = createRectangle((RectangleMapObject) object, mapScale);
			}
			else continue;
			
			if(objectClass == Room.class)
			{
				 RectangleMapObject r = (RectangleMapObject) object;
				 Vector2 position = new Vector2();
				 r.getRectangle().getPosition(position);
				 position.setLength(position.len()*mapScale);
				 Vector2 size = new Vector2();
				 r.getRectangle().getSize(size);
				 size.setLength(size.len()*mapScale);
				 Room room = new Room(shape,position, size,world);
				 map.add(room);
			 }
			else if(objectClass == Wall.class)
			 {
				 Wall wall = new Wall(shape,world);
				 map.add(wall);
			 }
			else if(objectClass == River.class)
			 {
				 River river = new River(shape,world);
				 map.add(river);
			 }
			else if(objectClass == Slow.class)
			 {
				RectangleMapObject r = (RectangleMapObject) object;
				Vector2 position = new Vector2();
				r.getRectangle().getPosition(position);
				position.setLength(position.len()*mapScale);
				Vector2 size = new Vector2();
				r.getRectangle().getSize(size);
				size.setLength(size.len()*mapScale);
				Slow slow = new Slow(shape,position, size,world);
				map.add(slow);
			 }
			else
			{
				RectangleMapObject r = (RectangleMapObject) object;
				Vector2 position = new Vector2();
				r.getRectangle().getCenter(position);
				position.setLength(position.len()*mapScale);
				if(objectClass == Player.class)
				{
					 map.setSpawnPoint(position);
				}
				else
				{
					Constructor<?> constructor = objectClass.getConstructor(Vector2.class, World.class);
					Object obj = constructor.newInstance(new Object[] {position,world});
					map.add(obj, objectClass);
				}
				
			}
			 shape.dispose();
		}
	}
	private static ChainShape createPolyline(PolylineMapObject polyline,float mapScale)
	{
		float[] vertices = polyline.getPolyline().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];
		
		for(int i=0;i<worldVertices.length;++i)
		{
			worldVertices[i] = new Vector2(vertices[i*2]/ Constants.BOX2D_SCALE * mapScale, vertices[i*2+1]/ Constants.BOX2D_SCALE * mapScale);
		}
		ChainShape chainShape = new ChainShape();
		chainShape.createChain(worldVertices);
		return chainShape;
	}
	private static ChainShape createPolygon(PolygonMapObject polygon,float mapScale)
	{
		float[] vertices = polygon.getPolygon().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length / 2];
		
		for(int i=0;i<vertices.length / 2;++i)
		{
			worldVertices[i] = new Vector2(vertices[i*2]/ Constants.BOX2D_SCALE * mapScale, vertices[i*2+1]/ Constants.BOX2D_SCALE * mapScale);
		}
		if(worldVertices[worldVertices.length - 1] != worldVertices[0])
		{
			worldVertices = Arrays.copyOf(worldVertices, worldVertices.length + 1);
			worldVertices[worldVertices.length - 1] = worldVertices[0];
		}
			
		ChainShape chainShape = new ChainShape();
		chainShape.createChain(worldVertices);
		return chainShape;
	}
	private static ChainShape createRectangle(RectangleMapObject rectangle,float mapScale)
	{
		Rectangle r = rectangle.getRectangle();
		Vector2 center = new Vector2();
		r.getCenter(center);
		float[] vertices = {r.getX(), r.getY(),
							- r.getX()+center.x*2,r.getY(),
							- r.getX()+center.x*2, - r.getY()+center.y*2,
							r.getX(), - r.getY()+center.y*2,
							r.getX(), r.getY()
							};
		Vector2[] worldVertices = new Vector2[vertices.length / 2];
		
		for(int i=0;i<worldVertices.length;++i)
		{
			//System.out.println(vertices[i*2] + " " + vertices[i*2+1]);
			worldVertices[i] = new Vector2(vertices[i*2]/ Constants.BOX2D_SCALE * mapScale, vertices[i*2+1]/ Constants.BOX2D_SCALE * mapScale);
		}
		//System.out.println("------------------");
		ChainShape chainShape = new ChainShape();
		chainShape.createChain(worldVertices);
		return chainShape;
	}
}
