package com.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
	private static Sound gunShot;
	private static Sound BGM;
	private static Sound explosion;
	private static Sound gameOver;
	private static Sound chestOpen;
	private static Sound button;
	private static Sound win;
	public static void init()
	{
		gunShot = Gdx.audio.newSound(Gdx.files.internal("Audio/gunshot.wav"));
		explosion = Gdx.audio.newSound(Gdx.files.internal("Audio/explosion.wav"));
		BGM = Gdx.audio.newSound(Gdx.files.internal("Audio/BGM.ogg"));
		gameOver = Gdx.audio.newSound(Gdx.files.internal("Audio/gameover.ogg"));
		chestOpen = Gdx.audio.newSound(Gdx.files.internal("Audio/chest-opening.mp3"));
		button = Gdx.audio.newSound(Gdx.files.internal("Audio/button.wav"));
		win = Gdx.audio.newSound(Gdx.files.internal("Audio/win.ogg"));
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
	public static void playChestOpenSound()
	{
		chestOpen.play();
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
	public static void playButtonSound()
	{
		button.play(1f, 1f, 0f);
	}
	public static void playWinBGM()
	{
		if(playing)
			return;
		playing = true;	
		win.loop();
	}
	public static void stopWinBGM()
	{
		win.stop();
	}
}
