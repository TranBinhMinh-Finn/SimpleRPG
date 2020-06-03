package com.game.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class CameraStyles {
	public static final float lerp = .08f;
	public static void lockOnTarget(Camera camera , float x , float y) {
		Vector3 position = camera.position;
		position.x = x;
		position.y = y;
		camera.position.set(position);
		camera.update();
	}
	public static void lerpToTarget(Camera camera , float x , float y) {
		// a + (b - a) * lerp factor
		Vector3 position = camera.position;
		position.x = camera.position.x + (x - camera.position.x) * lerp;
		position.y = camera.position.y + (y - camera.position.y) * lerp;
		camera.position.set(position);
		camera.update();
	}
}
