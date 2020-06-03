package com.game.screens;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.game.Constants;
import com.game.SimpleRPG;
import com.game.entity.Big_Demon;
import com.game.entity.Bullet;
import com.game.entity.Mob;
import com.game.entity.Player;
import com.game.handlers.InputHandler;
import com.game.handlers.WorldContactListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.entity.CameraStyles;
import com.game.entity.Room;

public class MainGameScreen implements Screen {
	SimpleRPG game;
	World world;
	Player player;
	ArrayList<Bullet> bullets;
	ArrayList<Mob> monsterList ;
	float timeStep = 1f/60f;
	int velocityIterations = 6, positionIterations = 3;
	Box2DDebugRenderer renderer;
	Matrix4 debugMatrix;
	OrthographicCamera cam;
	InputHandler inputHandler;

	float scale = 3;
	final float GAME_WORLD_WIDTH = scale * 1725 ; /// map width
	final float GAME_WORLD_HEIGHT =scale * 1600; /// map height
	Room[] rooms;
	OrthographicCamera camera;
	Texture rec = new Texture("bullet.png");
	public MainGameScreen(SimpleRPG game)
	{
		this.game = game;
		world = new World(new Vector2(0f,0f),false);
		bullets = new ArrayList<Bullet>();
		player = new Player(game,901,4579,world);
		monsterList = new ArrayList<Mob>();
		monsterList.add(new Big_Demon(game,800,800,world));
		world.setContactListener(new WorldContactListener());
		//Setting up inputHandler
		inputHandler = new InputHandler(player);
		
		
		// danchoi1 = new Player(game,901,4579);
		// quai1 = new Big_Demon(game,800,800);
		
		float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
		this.game.sprite = new Sprite(new Texture(Gdx.files.internal("map1.png")));
		this.game.sprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		camera = new OrthographicCamera(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		camera.translate(camera.viewportWidth/2 , camera.viewportHeight/2);
		
		//Setting up Box2D Debug Renderer
		renderer = new Box2DDebugRenderer();
		float w_ = Gdx.graphics.getWidth()/2;                                      
		float h_ = Gdx.graphics.getHeight()/2;                         
		//cam = new OrthographicCamera(w_,h_);
		cam = camera;
		//cam.setToOrtho(false, w_ / Constants.BOX2D_SCALE, h_ / Constants.BOX2D_SCALE);
		debugMatrix = new Matrix4(cam.combined);
		debugMatrix.scale(2f, 2f, 1);
		
		
		rooms = new Room[7];
		rooms[0] = new Room();
		rooms[1] = new Room( 800, 4475 , 252 + 32 , 217 + 32);
		rooms[2] = new Room(29, 1942-16,1773+32,1700+32);
		rooms[3] = new Room(2655 ,2726-16, 1003+32 , 938+32);
		rooms[4] = new Room( 409, 40-16, 1017+32 ,1004+32);
		rooms[5] = new Room(2287, 67-16,1759+32 , 1740+32);
		rooms[6] = new Room( 4830 ,760-16,288+32, 255+32);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		float del = Gdx.graphics.getDeltaTime();
		
		Gdx.gl.glClearColor(0.5f, 0.2f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// bullet testing ...
		
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		ArrayList<Mob> mobsToRemove = new ArrayList<Mob>();
		for(Bullet i : bullets) {
			//i.update(del);
			if(i.remove == true) {
				bulletsToRemove.add(i);
				i.dispose();
			}
		}
		for(Mob i : monsterList)
		{
			if(i.remove)
			{
				i.dispose();
				mobsToRemove.add(i);
			}
		}
		bullets.removeAll(bulletsToRemove);
		monsterList.removeAll(mobsToRemove);
		// entity behavior code 
		inputHandler.keyHandler(del);
		inputHandler.mouseClickHandler(del, bullets);
		
		world.step(timeStep, velocityIterations, positionIterations);
		///end bullet code  
		
		///camera test
		CameraStyles.lerpToTarget(camera, player.getXByPixels() , player.getYByPixels());
		/// end camera test
		
		game.batch.begin(); /// begin here -------------------------------------
		game.sprite.draw(game.batch);
		
		for(Bullet i : bullets) {
			i.render(game.batch);
		}
		player.render(del);
		
		for(Mob i : monsterList)
		{
			Big_Demon tmp  = (Big_Demon) i ;
			tmp.render(del);
		}
		
		//rendering the debug Box2D world
		//renderer.render(world,debugMatrix);
		for(int i = 1 ; i <= 6 ; ++i) {
			if(rooms[i].inRoom(player.getXByPixels() , player.getYByPixels())== true) {
				//System.out.println("check in room :" + i);
				if(rooms[i].size.x <= game.WIDTH && rooms[i].size.y <= game.HEIGHT) {
					CameraStyles.lerpToTarget(camera, rooms[i].bottomLeft.x + rooms[i].size.x/2, rooms[i].bottomLeft.y + rooms[i].size.y/2);
				}else {
					float py =game.HEIGHT/2;
					float px = game.WIDTH/2;
					float qx = rooms[i].bottomLeft.x + rooms[i].size.x - px;
					float qy = rooms[i].bottomLeft.y + rooms[i].size.y - py;
					px += rooms[i].bottomLeft.x;
					py += rooms[i].bottomLeft.y;
					if(camera.position.x < px) CameraStyles.lerpToTarget(camera,px , camera.position.y);
					if(camera.position.x > qx) CameraStyles.lerpToTarget(camera,qx , camera.position.y);
					if(camera.position.y < py) CameraStyles.lerpToTarget(camera,camera.position.x , py);
					if(camera.position.y > qy) CameraStyles.lerpToTarget(camera,camera.position.x , qy);
					
				}
			}
		}
		
		game.batch.setProjectionMatrix(camera.combined);
		/*game.batch.draw(rec , 800, 4475 , 252 + 32 , 217 + 32); 
		game.batch.draw(rec ,29, 1942,1773+32,1700+32);
		game.batch.draw(rec , 2655 ,2726-16, 1003+32 , 938+32);
		game.batch.draw(rec, 409, 40, 1017+32 ,1004+32);
		game.batch.draw(rec, 2287, 67-16,1759+32 , 1740+32);
		game.batch.draw(rec , 4830 ,760-16,288+32, 255+32);*/
		game.batch.end(); /// end here ----------------------------------------------
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
/*
 * room 1 : 784 4491 1036 4708
 * room 2 : 29 1958  1802 3658
 * room 3 : 2655 2710 3658 3648
 * room 4 : 409 24 1426 1028 
 * room 5 : 2287 35 4046 1775
 * room 6 : 4830 744 5118 999
 */
