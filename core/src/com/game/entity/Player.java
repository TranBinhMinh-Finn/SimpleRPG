package com.game.entity;

import com.badlogic.gdx.physics.box2d.World;
import com.game.Constants;
import com.game.SimpleRPG;

public class Player extends Mob {
	SimpleRPG game ;
	public static final int FRAME_WIDTH = 16;
	public static final int FRAME_HEIGHT = 16;
	public static final int IDLE_FRAME_NUMBER = 6;
	public static final int RUN_FRAME_NUMBER = 6;
	public static final int scale = 4;
	public static final float CHAR_ANIMATION_SPEED = 0.5f;
	public static final float SPEED = 50;
	public static final int player_hp = 100;
	public static final int player_atk = 10;
	float stateTime;
	
	
	//this.importIdleAnimation("knight_idle_spritesheet.png", FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
	//this.importRunAnimation("knight_run_spritesheet.png", FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);

	
	public Player(SimpleRPG game, float x, float y, World world)
	{
		super(x, y, FRAME_WIDTH*scale, FRAME_HEIGHT*scale,(FRAME_WIDTH - 4)*scale, (FRAME_HEIGHT - 3)*scale, world, player_hp, player_atk, SPEED, CHAR_ANIMATION_SPEED, Constants.BIT_PLAYER, Constants.BIT_ENEMY, (short)0);
		this.importIdleAnimation("knight_idle_spritesheet.png", IDLE_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.importRunAnimation("knight_run_spritesheet.png", RUN_FRAME_NUMBER, FRAME_WIDTH, FRAME_HEIGHT);
		this.game = game;
	}
	
	public void render(float del)
	{
		if(this.body.getLinearVelocity().len()!=0)
			this.render(del,runAnimation,runFrames,game.batch);
		else
			this.render(del,idleAnimation,idleFrames,game.batch);
		
		
	}
}
/* if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
System.out.println("Player position : " + Gdx.input.getX() + " " + (Gdx.graphics.getHeight() - Gdx.input.getY() + 1));
bullets.add(new Bullet(x + CHAR_WIDTH*scale/2 , y + CHAR_HEIGHT*scale/2 , Gdx.input.getX(), Gdx.input.getY()));
}

boolean pressedKey = false;
if(Gdx.input.isKeyPressed(Keys.UP))
{
pressedKey = true;
y += SPEED*del;
//if(y+CHAR_WIDTH*scale>SimpleRPG.HEIGHT)y = SimpleRPG.HEIGHT-CHAR_HEIGHT*scale;

}
if(Gdx.input.isKeyPressed(Keys.DOWN))
{
pressedKey = true;
y -= SPEED*del;
//if(y<0)y=0;
}
if(Gdx.input.isKeyPressed(Keys.RIGHT))
{
flip = false;
pressedKey = true;
x += SPEED*del;
//if(x+CHAR_WIDTH*scale>SimpleRPG.WIDTH)x = SimpleRPG.WIDTH-CHAR_WIDTH*scale;
}
if(Gdx.input.isKeyPressed(Keys.LEFT))
{
pressedKey = true;
flip = true;
x -= SPEED*del;
//if(x<0)x=0;
}


stateTime +=  del*5;
if(pressedKey)
render(del,run,runFrames);
else
render(del,idle,idleFrames);


>>>>>>> bigdemon2 */
