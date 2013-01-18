package com.teamBasics.CollegeTD;

import com.teamBasics.framework.Game;
import com.teamBasics.framework.Graphics;
import com.teamBasics.framework.Screen;
import com.teamBasics.framework.Graphics.ImageFormat;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game) {

		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.menu = g.newImage("mainMenu.png", ImageFormat.RGB565);
		Assets.background = g.newImage("grass.png", ImageFormat.RGB565);
		// Assets.character = g.newImage("character.png", ImageFormat.ARGB4444);
		// Assets.character2 = g.newImage("character2.png",
		// ImageFormat.ARGB4444);
		// Assets.character3 = g.newImage("character3.png",
		// ImageFormat.ARGB4444);
		// Assets.characterJump = g.newImage("jumped.png",
		// ImageFormat.ARGB4444);
		// Assets.characterDown = g.newImage("down.png", ImageFormat.ARGB4444);

		// Assets.heliboy = g.newImage("heliboy.png", ImageFormat.ARGB4444);
		// Assets.heliboy2 = g.newImage("heliboy2.png", ImageFormat.ARGB4444);
		// Assets.heliboy3 = g.newImage("heliboy3.png", ImageFormat.ARGB4444);
		// Assets.heliboy4 = g.newImage("heliboy4.png", ImageFormat.ARGB4444);
		// Assets.heliboy5 = g.newImage("heliboy5.png", ImageFormat.ARGB4444);

		// Border outline
		Assets.tiledirt = g.newImage("tiledirt.png", ImageFormat.RGB565);
		Assets.tilegrassTop = g.newImage("tilegrasstop.png", ImageFormat.RGB565);
		Assets.tilegrassBot = g.newImage("tilegrassbot.png", ImageFormat.RGB565);
		Assets.tilegrassLeft = g.newImage("tilegrassleft.png", ImageFormat.RGB565);
		Assets.tilegrassRight = g.newImage("tilegrassright.png", ImageFormat.RGB565);

		// Dirt Path Walkways
		Assets.dirtCenter = g.newImage("dirt_center.png", ImageFormat.RGB565);

		Assets.dirtTop = g.newImage("dirt_top.png", ImageFormat.RGB565);
		Assets.dirtBottom = g.newImage("dirt_bottom.png", ImageFormat.RGB565);
		Assets.dirtLeft = g.newImage("dirt_left.png", ImageFormat.RGB565);
		Assets.dirtRight = g.newImage("dirt_right.png", ImageFormat.RGB565);

		Assets.dirtLBC = g.newImage("dirt_LBC.png", ImageFormat.RGB565);
		Assets.dirtLTC = g.newImage("dirt_LTC.png", ImageFormat.RGB565);
		Assets.dirtRBC = g.newImage("dirt_RBC.png", ImageFormat.RGB565);
		Assets.dirtRTC = g.newImage("dirt_RTC.png", ImageFormat.RGB565);

		

		Assets.button = g.newImage("button.jpg", ImageFormat.RGB565);

		// This is how you would load a sound if you had one.
		// Assets.click = game.getAudio().createSound("explode.ogg");

		game.setScreen(new MainMenuScreen(game));
		Thread thread = new Thread();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.splash, 0, 0);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}
}