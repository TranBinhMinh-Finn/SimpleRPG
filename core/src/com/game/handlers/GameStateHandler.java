package com.game.handlers;

import com.game.entity.Player;
import com.game.map.Map;

public class GameStateHandler {
	static boolean inSlow;
	public static void update(Map map, Player player)
	{
		for(int i=1;i<=map.slowListSize;++i)
		{
			if(map.getSlow(i).inSlowRange(player.getXByPixels(),player.getYByPixels()))
			{
				player.slowed = 0.5f;
				return ;
			}
		}
		player.slowed = 1f;
	}
}
