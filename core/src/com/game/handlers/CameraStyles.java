package com.game.handlers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.game.SimpleRPG;
import com.game.entity.Player;
import com.game.map.Map;
import com.game.map.Room;

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
	public static void cameraUpdate(Camera camera, Map map, Player player)
	{
		CameraStyles.lerpToTarget(camera, player.getXByPixels() , player.getYByPixels());
		player.currentRoomId = 0;
		for(int i = 1 ; i <= map.roomListSize ; ++i) {
			Room room =  map.getRoom(i);
			if(room.inRoom(player.getXByPixels() , player.getYByPixels())== true) 
			{
				//System.out.println("check in room :" + i);
				player.currentRoomId = i;
				if(room.size.x <= SimpleRPG.WIDTH && room.size.y <= SimpleRPG.HEIGHT) {
					CameraStyles.lerpToTarget(camera, room.bottomLeft.x + room.size.x/2, room.bottomLeft.y + room.size.y/2);
				}else {
					float py = SimpleRPG.HEIGHT/2;
					float px = SimpleRPG.WIDTH/2;
					float qx = room.bottomLeft.x + room.size.x - px;
					float qy = room.bottomLeft.y + room.size.y - py;
					px += room.bottomLeft.x;
					py += room.bottomLeft.y;
					if(camera.position.x < px) CameraStyles.lerpToTarget(camera,px , camera.position.y);
					if(camera.position.x > qx) CameraStyles.lerpToTarget(camera,qx , camera.position.y);
					if(camera.position.y < py) CameraStyles.lerpToTarget(camera,camera.position.x , py);
					if(camera.position.y > qy) CameraStyles.lerpToTarget(camera,camera.position.x , qy);
					
				}
			}
		}
	}
}
