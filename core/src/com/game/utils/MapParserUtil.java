package com.game.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.map.River;
import com.game.map.Wall;

public class MapParserUtil {
	public static void parseTiledObjectLayer(World world, MapObjects objects,float mapScale,String objectType)
	{
		for(MapObject object: objects)
		{
			Shape shape;
			 if(object instanceof PolylineMapObject)
			 {
				 shape = createPolyline((PolylineMapObject) object, mapScale);
				 if(objectType == "Wall")
				 {
					 Wall wall = new Wall(shape,world);
				 }
				 shape.dispose();
			 }
			 if(object instanceof PolygonMapObject)
			 {
				 shape  = createPolygon((PolygonMapObject)object,mapScale);
				 if(objectType == "River")
				 {
					 River river = new River(shape,world);
				 }
				 shape.dispose();
			 }
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
		
		for(int i=0;i<worldVertices.length;++i)
		{
			worldVertices[i] = new Vector2(vertices[i*2]/ Constants.BOX2D_SCALE * mapScale, vertices[i*2+1]/ Constants.BOX2D_SCALE * mapScale);
		}
		ChainShape chainShape = new ChainShape();
		chainShape.createChain(worldVertices);
		return chainShape;
	}
}
