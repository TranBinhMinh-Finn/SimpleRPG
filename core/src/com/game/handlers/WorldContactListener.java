package com.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.entity.Entity;
import com.game.entity.Player;
import com.game.map.Slow;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if(a == null || b == null) return ;
		if(a.getUserData() == null || b.getUserData() == null) return ;
		
		Object objectA = a.getUserData();
		Object objectB = b.getUserData();
		if(objectA instanceof Entity)
		{
			Entity entityA = (Entity) objectA;
			entityA.contactHandle(objectB);
		}
		if(objectB instanceof Entity)
		{
			Entity entityB = (Entity) objectB;
			entityB.contactHandle(objectA);
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if(a == null || b == null) return ;
		if(a.getUserData() == null || b.getUserData() == null) return ;
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if(a == null || b == null) return ;
		if(a.getUserData() == null || b.getUserData() == null) return ;
		/*Object objectA = a.getUserData();
		Object objectB = b.getUserData();
		if(objectA instanceof Slow)
		{
			Player player = (Player) objectB;
			player.slowed = 0.5f;
			contact.setEnabled(false);
			return ;
		}
		if(objectB instanceof Slow)
		{
			Player player = (Player) objectA;
			player.slowed = 0.5f;
			contact.setEnabled(false);
			return ;
		}
		contact.setEnabled(true);*/
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
		
	}
	
}
