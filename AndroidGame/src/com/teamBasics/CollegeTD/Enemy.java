//Probably not the best to be passing the whole map to this class,
//but it will work temporarily until we come up with a better fix

package com.teamBasics.CollegeTD;

import java.util.ArrayList;

import com.teamBasics.framework.Graphics;

import android.graphics.Rect;

public abstract class Enemy {
	//posX, posY=top left corner of enemy sprite
	protected int x, y, speed;  					// add power later on
	protected int movementX;
	protected int movementY;
	protected int size;
	protected int score;							//score enemy gives to player when it dies
	protected int cash;								//cash enemy gives to user when it dies
	public Rect r = new Rect(0, 0, 0, 0);				//collision box
	protected int health;
	protected boolean visible, kamakazi, dead;			//kamakazi means enemy reached end of map
	private ArrayList<PathTile> tilearrayPath;
	
	
	public Enemy(int posX, int posY, ArrayList<PathTile> tilearrayPath) {
		this.x = posX;
		this.y = posY;
		setVisible(false);
		r.set(posX, posY, posX+size, posY+size);
		kamakazi = false;
		dead = false;
		this.tilearrayPath = tilearrayPath;
	}
	
	// Behavioral Methods
	public void pathMove() {
		int next_tileY, next_tileX;
		if(y+(size/2) >= 360 || x+(size/2) >= 680) {
			if(visible==true) {		//means its the first time through
				kamakazi = true;
			}
			visible = false;
			dead = true;
			//die but don't add to user cash
		}
		else {
			if(dead) {
				visible = false;
			}
			else {
				visible = true;
				if(movementY > 0) {
					next_tileY = y+speed+size;	
					next_tileX = x+(size/2);
				}
				else if(movementY < 0) {
					next_tileY = y+speed-size;	
					next_tileX = x+(size/2);
				}
				else if(movementX < 0 ){
					next_tileY = y+(size/2);
					next_tileX = x+speed-size;			
				}
				else {
					next_tileY = y+(size/2);
					next_tileX = x+speed+size;
				}
				
				if(next_tileX <680 && next_tileY < 360) {
					PathTile next = searchArray(next_tileX, next_tileY);
					if(next == null) {
						movementY=0;
						movementX=0;
					}
					else if(next != null && next.type2 != 's') {
						if(movementY != 0) {
							movementY=0;
							movementX=horizantal_Search();
						}
						else {
							movementY=vertical_Search();
							movementX=0;
						}
					}
				}
				y+=movementY;
				x+=movementX;
				r.set(x, y, x+size, y+size);
			}
		}
	}
	
	public PathTile searchArray(int next_tileX, int next_tileY) {
		PathTile within=null;
		for(int i=0; i<tilearrayPath.size(); i++) {
			PathTile tile = tilearrayPath.get(i);
			if(tile.getTileX() <= next_tileX && tile.getTileX()+20 >= next_tileX) {
				if(tile.getTileY() <= next_tileY && tile.getTileY()+20 >= next_tileY) {
					within = tile;
				}
			}
		}
		return within;
	}
	
	public int horizantal_Search() {
		int left_tiles = x-20;
		int right_tiles = x+20;
		while(left_tiles >=0 && right_tiles <=680) {
			PathTile left = searchArray(left_tiles, y);
			PathTile right = searchArray(right_tiles, y);
			if(left.type2 != 's') {
				return speed;
			}
			else if(right.type2 != 's') {
				return speed*(-1);
			}
			left_tiles -=20;
			right_tiles += 20;
		}
		return 0;
	}
	
	public int vertical_Search() {
		int above_tiles = y-20;
		int below_tiles = y+20;
		while(above_tiles >= 40 && below_tiles <=360) {
			PathTile above = searchArray(x, above_tiles);
			PathTile below = searchArray(x, below_tiles);
			if(above.type2 != 's') {
				return speed;
			}
			else if(below.type2 != 's') {
				return speed*(-1);
			}
			above_tiles -=20;
			below_tiles += 20;
		}
		return 0;
	}
	
	public abstract void draw(Graphics g);
	public abstract void checkCollision();
	public abstract void update();

	public void die() {
		visible = false;
		dead = true;
		// Remove from screen
		// Increase User Cash Value
	}

	public void attack() {

	}

	public int getPosX() {
		return x;
	}

	public void setPosX(int posX) {
		this.x = posX;
	}

	public int getPosY() {
		return y;
	}

	public void setPosY(int posY) {
		this.y = posY;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isKamakazi() {
		return kamakazi;
	}

	public void setKamakazi(boolean kamakazi) {
		this.kamakazi = kamakazi;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
