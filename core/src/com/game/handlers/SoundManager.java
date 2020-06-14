package com.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	private static Sound gunShot;
	private static Sound BGM;
	private static Sound explosion;
	private static Sound gameOver;
	static void init()
	{
		gunShot = Gdx.audio.newSound(Gdx.files.internal("Audio/gunshot.wav"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("Audio/explosion.wav"));
		BGM = Gdx.audio.newSound(Gdx.files.internal("Audio/BGM.ogg"));
		gameOver = Gdx.audio.newSound(Gdx.files.internal("Audio/gameover.ogg"));
	}
	public static void playGunShotSound()
	{
		//gunShot.play();
		gunShot.play(1f, 0.5f, 0f);
	}
	public static void playExplosionSound()
	{
		explosion.play(1f, 1f, 0f);
	}
	public static void playBGM()
	{
		BGM.loop();
	}
	public static void stopBGM()
	{
		BGM.stop();
	}
	static boolean playing = false;
	public static void playGameOverBGM()
	{
		if(playing)
			return;
		playing = true;	
		gameOver.loop();
	}
	public static void stopGameOverBGM()
	{
		gameOver.stop();
	}
}
