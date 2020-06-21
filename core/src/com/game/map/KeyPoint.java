package com.game.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.Entity;
import com.game.entity.Player;
import com.game.entity.enemy.Enemy;
import com.game.handlers.GameStateManager;
import com.game.utils.Constants;

public class KeyPoint {
	private boolean obstruct = false;
	private RayCastCallback callback;
	private World world;
	private Vector2 position;
	private ArrayList<Integer> adjacent;
	public KeyPoint(Vector2 position, World world)
	{
		this.position = position;
		this.position.x/=Constants.BOX2D_SCALE;
		this.position.y/=Constants.BOX2D_SCALE;
		this.world = world;
		adjacent = new ArrayList<Integer>();
		callback = new RayCastCallback()
		{
			
					@Override
				public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
						if(fixture.getUserData() instanceof Wall || fixture.getUserData() instanceof River)
						{
							obstruct = true;
							return 0;
						}
						return -1;
				}
		};
	}
	public boolean rayCast(Vector2 point2)
	{
		obstruct = false;
		if(position.epsilonEquals(point2))
			return false;
		world.rayCast(callback, position, point2);
		//GameStateManager.line.add(position);
		//GameStateManager.line.add(point2);
		//System.out.println(rayFraction);
		return obstruct;
	}
	public boolean obstructed(Player player)
	{
		return rayCast(player.getBody().getPosition());
	}
	public Vector2 getPosition()
	{
		return position;
	}
	public World getWorld()
	{
		return this.world;
	}
	public ArrayList<Integer> getAdjacent()
	{
		return adjacent;
	}
}
