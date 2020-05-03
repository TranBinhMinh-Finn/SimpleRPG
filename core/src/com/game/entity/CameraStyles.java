package com.game.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class CameraStyles {
	public static void lockOnTarget(Camera camera , float x , float y) {
		Vector3 position = camera.position;
		position.x = x;
		position.y = y;
		camera.position.set(position);
		camera.update();
	}
}
