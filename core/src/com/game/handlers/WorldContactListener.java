package com.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.entity.Entity;

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
		
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
		
	}
	
}
