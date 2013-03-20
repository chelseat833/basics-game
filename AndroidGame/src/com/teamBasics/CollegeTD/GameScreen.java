package com.teamBasics.CollegeTD;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.teamBasics.framework.Game;
import com.teamBasics.framework.Graphics;
import com.teamBasics.framework.Image;
import com.teamBasics.framework.Graphics.ImageFormat;
import com.teamBasics.framework.Input.TouchEvent;
import com.teamBasics.framework.Screen;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;
	
	enum TowerType {
		reddit, starbucks, pencil, none
	}
	
	
	// Variable Setup
	private String descriptionText = "";
	private String scoreText = "0";
	private String livesText;
	private String cashText = "$0";
	TowerType towerType = TowerType.none;
	private int Xtower = 0;
	private int Ytower = 0;
	private int Xbox = -50;
	private int Ybox = -50;
	private int towerNumber = -10;
	Paint paintInit, paintMenu, paintDescriptionText, paintHUBText;

	//Levels
	private static Level CurrentLevel;
	private ArrayList<Level> levelArray = new ArrayList<Level>(20);
	
	private int leftSideTowerPixel = 734 + 8;
	private int rightSideTowerPixel = leftSideTowerPixel + 40;
	
	public GameScreen(Game game) {
		super(game);
		Assets.option0 = Assets.blank;
		Assets.option1 = Assets.blank;
		Assets.option2 = Assets.blank;
		for(int i = 0; i < 12; i++){
			levelArray.add(i, ( new Level(i+1) ));
		}
	
		// Must update CurrentLevel to next level when user has defeated all enemies.
		CurrentLevel = levelArray.get(0);	
		
		// Defining a paint object
		// Start Text
		paintInit = new Paint();
		paintInit.setTextSize(30);
		paintInit.setTextAlign(Paint.Align.CENTER);
		paintInit.setAntiAlias(true);
		paintInit.setColor(Color.GRAY);

		// Back button (options menu)
		paintMenu = new Paint();
		paintMenu.setTextSize(100);
		paintMenu.setTextAlign(Paint.Align.CENTER);
		paintMenu.setAntiAlias(true);
		paintMenu.setColor(Color.LTGRAY);
		
		// Description Text
		paintDescriptionText = new Paint();
		paintDescriptionText.setTextSize(20);
		paintDescriptionText.setTextAlign(Paint.Align.LEFT);
		paintDescriptionText.setAntiAlias(true);
		paintDescriptionText.setColor(Color.CYAN);
		
		// HUB Text
		paintHUBText = new Paint();
		paintHUBText.setTextSize(20);
		paintHUBText.setTextAlign(Paint.Align.RIGHT);
		paintHUBText.setAntiAlias(true);
		paintHUBText.setColor(Color.CYAN);

	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List<TouchEvent> touchEvents) {
		// This example starts with a "Ready" screen.
		// When the user touches the screen, the game begins.
		// state now becomes GameState.Running.
		// Now the updateRunning() method will be called!
		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		if(CurrentLevel.isLevelComplete() == true){
			CurrentLevel = levelArray.get(CurrentLevel.getLevel_number()-1);
		}
		Graphics sel = game.getGraphics();

		// 1. All touch input is handled here:
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			int X = event.x;
			int Y = event.y;
			Xbox = cordFix(X);
			Ybox = cordFix(Y);	
			// Handles Touch PRESS
			if (event.type == TouchEvent.TOUCH_DOWN) {
					
				// Display Selected Item on touch_down(press)
				
				// Reddit Tower
				if(inBounds(event, leftSideTowerPixel, 103, 40, 40)){
					Assets.selectItem = Assets.redditTower;
					descriptionText = "This is the reddit tower!";
					towerType = TowerType.reddit;
					Xtower = -50;
					Ytower = -50;
				}
				// Pencil Tower
				else if(inBounds(event, leftSideTowerPixel, 163, 40, 40)){
					Assets.selectItem = Assets.pencilTower;
					descriptionText = "This is the pencil tower!";
					towerType = TowerType.pencil;
					Xtower = -50;
					Ytower = -50;
				}
				// Starbucks Tower
				else if(inBounds(event, leftSideTowerPixel, 223, 40, 40)){
					Assets.selectItem = Assets.cokeTower;
					descriptionText = "This is the coke tower!";
					towerType = TowerType.starbucks;
					Xtower = -50;
					Ytower = -50;
				}
				
				// Sleep Upgrade
				else if(inBounds(event, leftSideTowerPixel, 283, 40, 40)){
					Assets.selectItem = Assets.sleepUp;
					descriptionText = "This is the sleep upgrade!";
				}
				// Social Upgrade
				else if(inBounds(event, leftSideTowerPixel, 343, 40, 40)){
					Assets.selectItem = Assets.socialUp;
					descriptionText = "This is the social upgrade!";
				}
				// Academic Upgrade
				else if(inBounds(event, leftSideTowerPixel, 403, 40, 40)){
					Assets.selectItem = Assets.academicUp;
					descriptionText = "This is the academic upgrade!";
				}
				else {
					towerType = TowerType.none;
					
				}

			}

			if (event.type == MotionEvent.ACTION_MOVE) {
				// Sets the X and Y coords of the tower to the touch even ones
				Xtower = X;
				Ytower = Y;
				// This section uses the same subroutine as the final drop does to display the box where the tile will be located
				// The tower is drawn when all of the other game screen images are updated.
				Xbox = cordFix(Xtower);
				Ybox = cordFix(Ytower);
				
			}
			
			// Handles touch RELEASE
			if (event.type == TouchEvent.TOUCH_UP) {
				
				if(inBounds(event, 5, 1, 34, 33)){
					pause();
				}
				
				//towerNumber = -1; Messed up the code for some reason haha
				towerPlacement(X, Y);
				touchTowerSel(X, Y); // Checks if the touch was on top of a tower.
				upgradeTowerSel(X, Y); // THIS PART WILL TAKE CARE OF SELECTING THE UPGRADES //
				
				
				towerType = TowerType.none;
			}
		}
		
		// 2. Check miscellaneous events like death:
		if (CurrentLevel.getLivesLeft() == 0) {
			state = GameState.GameOver;
		}

		// 3. Call individual update() methods here.
		// This is where all the game updates happens

		//CurrentLevel.updateBorderTiles();
		CurrentLevel.updatePathTiles();
		CurrentLevel.update();
		CurrentLevel.checkCash();
	}

	private int validTowerUpgrade(int Xloc, int Yloc) { // WILL RETURN THE INDEX OF THE TOWER
		ArrayList<Tower> towerList = CurrentLevel.getTowers(); // get list of already placed towers
		for(int i = 0; i < towerList.size(); i++) { // check to make sure that the tower is not already in that location
			if( towerList.get(i).getPosX() == Xloc && towerList.get(i).getPosY() == Yloc) {
				descriptionText = "Select the upgrade to the right.";
				towerType = TowerType.none;
				return i;
				
			}
		}
		return -1;
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 0, 0, 800, 240)) {

					if (!inBounds(event, 0, 0, 35, 35)) {
						resume();
					}
				}

				if (inBounds(event, 0, 240, 800, 240)) {
					nullify();
					goToMenu();
				}
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (inBounds(event, 0, 0, 800, 480)) {
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		
		// Draw Main Game Screen
		g.drawImage(Assets.gamescreen, 0, 0);
		
		// Draw custom background
		//g.drawImage(Assets.space, 0, 41);
		//g.drawImage(Assets.space, 0, 200);
		//g.drawImage(Assets.space, 340, 41);
		//g.drawImage(Assets.space, 340, 200);
		g.drawImage(Assets.ltDirtCenter, 0, 41);
		g.drawImage(Assets.ltDirtCenter, 256, 41);
		g.drawImage(Assets.ltDirtCenter, 0, 210);
		g.drawImage(Assets.ltDirtCenter, 256, 210);
		g.drawImage(Assets.ltDirtRight, 512, 41);
		g.drawImage(Assets.ltDirtRight, 512, 210);
		
		// Paint Tiles
		//CurrentLevel.paintBorderTiles(g);
		CurrentLevel.paintPathTiles(g);

		// Secondly, draw the UI above the game elements.
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver) {
			drawGameOverUI();
		}
		
	}

	private void nullify() {

		// Set all variables to null. You will be recreating them in the
		// constructor.
		paintInit = null;
		//level1 = null;
		CurrentLevel=null;

		// Call garbage collector to clean up memory.
		System.gc();
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawARGB(80, 0, 0, 0);
		g.drawString("Tap to Start!", 400, 240, paintInit);

	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		
		// Tower sprites
		g.drawImage(Assets.redditTower, leftSideTowerPixel, 103);
		g.drawImage(Assets.pencilTower, leftSideTowerPixel, 163);
		g.drawImage(Assets.cokeTower, leftSideTowerPixel, 223);
		
		// Draw towers that are being dragged
		// 
		if (towerType == TowerType.reddit) {
			g.drawImage(Assets.redditTower, Xtower, Ytower);
		}
		else if (towerType == TowerType.starbucks) {
			g.drawImage(Assets.cokeTower, Xtower, Ytower);
		}
		else if (towerType == TowerType.pencil) {
			g.drawImage(Assets.pencilTower, Xtower, Ytower);
		}
		// Draw the Highlighted box where the tower will be placed
		
		
		// Upgrade sprites
		g.drawImage(Assets.sleepUp, leftSideTowerPixel, 283);
		g.drawImage(Assets.socialUp, leftSideTowerPixel, 343);
		g.drawImage(Assets.academicUp, leftSideTowerPixel, 403);
		
		g.drawImage(Assets.selectItem, 49, 423);
		g.drawImage(Assets.option0, 468, 418);
		g.drawImage(Assets.option1, 548, 418);
		g.drawImage(Assets.option2, 628, 418);
		g.drawString(descriptionText, 160, 439, paintDescriptionText);
		
		scoreText = "" + CurrentLevel.getScore();
		g.drawString(scoreText, 290, 26, paintHUBText);
		livesText = "" + CurrentLevel.getLivesLeft();
		g.drawString(livesText, 516, 26, paintHUBText);
		cashText = "$" + CurrentLevel.getCash(); 
		g.drawString(cashText, 695, 26, paintHUBText);
		
		CurrentLevel.draw(g);
		if(towerType != TowerType.none && (Xtower > 0 && Xtower < 701 && Ytower > 21 && Ytower < 339)){
			g.drawImage(Assets.towerBox, Xbox, Ybox);
		}
	}
	
	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 400, 165, paintMenu);
		g.drawString("Menu", 400, 360, paintMenu);

	}
	private int cordFix(int cord) {
		int tempCord = cord/40;
		int modCord = cord%40;
		
		if(modCord > 20) {
			tempCord += 1;
		}
		
		return tempCord*40;
	}
	
	private int cordFixTow(int cord) {
		int tempCord = (cord-20)/40;
		int modCord = (cord-20)%40;
		
		if(modCord > 20) {
			tempCord += 1;
		}
		
		return tempCord*40;
	}
	
	
	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER.", 400, 240, paintMenu);
		g.drawString("Try Again!", 400, 290, paintInit);

	}

	private boolean validPlacement(int Xloc, int Yloc) {
		boolean ret = true;
		ArrayList<PathTile> tilePath = CurrentLevel.getTilearrayPath();
		for(int i = 0; i < tilePath.size(); i++) { // check the map tiles to make sure that it is not a stone or dirt tile
			if(tilePath.get(i).getTileX() == Xloc && tilePath.get(i).getTileY() == Yloc && (tilePath.get(i).type2 == 's' || tilePath.get(i).type2 == 'w' || tilePath.get(i).type2 == 'a' || tilePath.get(i).type2 == 'd' || tilePath.get(i).type2 == 'x' || tilePath.get(i).type2 == 'z' || tilePath.get(i).type2 == 'q' || tilePath.get(i).type2 == 'c' || tilePath.get(i).type2 == 'e' || tilePath.get(i).type2 == 'r' && towerType != TowerType.none)){ // check if the stone tile X and Y coords are the same
				descriptionText = "Towers cannot be on path";
				return false;
			}
		}
		
		ArrayList<Tower> towerList = CurrentLevel.getTowers(); // get list of already placed towers
		for(int i = 0; i < towerList.size(); i++) { // check to make sure that the tower is not already in that location
			if( towerList.get(i).getPosX() == Xloc && towerList.get(i).getPosY() == Yloc && towerType != TowerType.none) {
				descriptionText = "Towers cannot overlap.";
				return false;				
			}
		}		
		
		
		return ret;
	}
	
	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;

	}

	@Override
	public void resume() {
		if (state == GameState.Paused)
			state = GameState.Running;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

	private void goToMenu() {
		// TODO Auto-generated method stub
		game.setScreen(new MainMenuScreen(game));
	}
	
	public static Level getCurrentLevel(){
		return CurrentLevel;
	}
	
	private void touchTowerSel(int X, int Y) {
		// THIS WILL BE THE SECTION FOR TOWER UPGRADES BASED ON THE TOUCH_SCREEN
		// FIRST TAKE THE TOUCH AND SEE IF IT IS LET UP ON TOP OF A TOWER
		if(towerType == TowerType.none) { // only run if the tower is not being placed.
			if((X > 468 && X < 528 && Y > 418 && Y < 468 || X > 548 && X < 608 && Y > 418 && Y < 468 || X > 628 && X < 688 && Y > 418 && Y < 468) && towerNumber >= 0) {
				// do nothing, touch was inside the upgrade. Code for this part is below.
				descriptionText = "It gets here";
			} else {
			towerNumber = validTowerUpgrade(cordFixTow(X), cordFixTow(Y));
			if(towerNumber >= 0) {
				ArrayList<Tower> towerList = CurrentLevel.getTowers();
				descriptionText = "Select upgrade for tower# " + (towerNumber+1);
				if(towerList.get(towerNumber) instanceof RedditTower) { // MAKE SURE TO UPDATE ACTUAL UPGRADE IMAGES
					Assets.selectItem = Assets.redditTower;
					Assets.option0 = Assets.lowCost;
					Assets.option1 = Assets.rangeExtend;
					Assets.option2 = Assets.lowCooldown;
				} else if (towerList.get(towerNumber) instanceof PencilTower) {
					Assets.selectItem = Assets.pencilTower;
					Assets.option0 = Assets.lowCost;
					Assets.option1 = Assets.rangeExtend;
					Assets.option2 = Assets.lowCooldown;
				} else if (towerList.get(towerNumber) instanceof StarbucksTower) {
					Assets.selectItem = Assets.starbucksTower;
					Assets.option0 = Assets.lowCost;
					Assets.option1 = Assets.rangeExtend;
					Assets.option2 = Assets.lowCooldown;
				}
			} else {
				Assets.selectItem = Assets.blank;
				Assets.option0 = Assets.blank;
				Assets.option1 = Assets.blank;
				Assets.option2 = Assets.blank;
				descriptionText = ""; // clear description text when nothing is selected.
				towerNumber = -50;
			}
			}
			towerType = TowerType.none;
		}
	}

	private void upgradeTowerSel(int X, int Y) {
	if(towerNumber >= 0) {
		if((X > 468 && X < 528 && Y > 418 && Y < 468 || X > 548 && X < 608 && Y > 418 && Y < 468 || X > 628 && X < 688 && Y > 418 && Y < 468)) {
			ArrayList<Tower> towerList = CurrentLevel.getTowers();
			towerList.get(towerNumber).setLevel(towerList.get(towerNumber).getLevel()+1);
			if(X > 468 && X < 528 && Y > 418 && Y < 468) {
				// Cash Down upgrade
				if(towerList.get(towerNumber).getLevelup_cost() >= CurrentLevel.getCash()){
					towerList.get(towerNumber).setDamage(towerList.get(towerNumber).getDamage()+5); // update to reflect not allowing to go past 0;
					descriptionText = "The damage has increased.";
					// Cash subtracted from game screen
					CurrentLevel.setCash(CurrentLevel.getCash()-towerList.get(towerNumber).getLevelup_cost());
				} else {
					descriptionText = "Insufficient funds!";
				}
			}
			else if(X > 548 && X < 608 && Y > 418 && Y < 468) {
				// Longer Range
			if(towerList.get(towerNumber).getLevelup_cost() <= CurrentLevel.getCash()){
				towerList.get(towerNumber).setRange(towerList.get(towerNumber).getRange()+10);
				descriptionText = "Range has been extended.";
				// Cash subtracted from game screen
				CurrentLevel.setCash(CurrentLevel.getCash()-towerList.get(towerNumber).getLevelup_cost());
			} else {
				descriptionText = "Insufficient funds!";
			}
		}
		else if(X > 628 && X < 688 && Y > 418 && Y < 468) {
				// Lower cooldown
			if(towerList.get(towerNumber).getLevelup_cost() <= CurrentLevel.getCash()){
				if(towerList.get(towerNumber).getCooldown() >= 100) {
				towerList.get(towerNumber).setCooldown(towerList.get(towerNumber).getCooldown()-100);
				descriptionText = "Cooldown has been lowered.";
				// Cash subtracted from game screen
				CurrentLevel.setCash(CurrentLevel.getCash()-towerList.get(towerNumber).getLevelup_cost());
				} else {
					descriptionText = "Max Level Achieved";
				}
			} else {
				descriptionText = "Insufficient funds!";
			}
		}				
			towerNumber = -1;
			Assets.option0 = Assets.blank;
			Assets.option1 = Assets.blank;
			Assets.option2 = Assets.blank;
			Assets.selectItem = Assets.blank;
			//descriptionText = "Upgrade should have happened";
		}
	}
	}
	private void towerPlacement(int X, int Y) {
		if(Xtower > 0 && Xtower < 701 && Ytower > 21 && Ytower < 339) {  // CHANGE TO ACTUAL VALUES
			// THIS SECTION OF THE CODE WILL ALLOW THE TOWERS TO BE DRAGGED AND PLACED.
			//placeTower(Xtower, Ytower);
			Xtower = cordFix(Xtower);
			Ytower = cordFix(Ytower);
			Xbox = -50;
			Ybox = -50;
			
			if(towerType != TowerType.none && validPlacement(Xtower, Ytower)) {
			
				if (towerType == TowerType.reddit) {
					RedditTower temp = new RedditTower(Xtower, Ytower);
					if(CurrentLevel.getCash()>=temp.cost) {
						CurrentLevel.addTower(temp);
						CurrentLevel.setCash(CurrentLevel.getCash()-temp.cost);
					} else {
						descriptionText = "Insufficient funds!";
					}
				}
				else if (towerType == TowerType.starbucks) {
			
					StarbucksTower temp = new StarbucksTower(Xtower, Ytower);
					if(CurrentLevel.getCash()>=temp.cost) {
						CurrentLevel.addTower(temp);
						CurrentLevel.setCash(CurrentLevel.getCash()-temp.cost);	
					} else {
						descriptionText = "Insufficient funds!";
					}
				}
				else if (towerType == TowerType.pencil) {
			
					PencilTower temp = new PencilTower(Xtower, Ytower);
					if(CurrentLevel.getCash()>=temp.cost) {
						CurrentLevel.addTower(temp);
						CurrentLevel.setCash(CurrentLevel.getCash()-temp.cost);
					} else {
						descriptionText = "Insufficient funds!";
					}
				}
			}
		} else {
			// invalid tower placement
			towerType = TowerType.none;
		}
	}
}

