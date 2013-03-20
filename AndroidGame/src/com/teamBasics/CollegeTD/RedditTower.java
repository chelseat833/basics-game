package com.teamBasics.CollegeTD;

import com.teamBasics.framework.Graphics;

public class RedditTower extends Tower {

	public RedditTower(int posX, int posY) {
		super(posX, posY);
		size = 40;
		level = 1;
		cooldown = 1000;
		damage = 20;
		range = 75;
		cost = 50;
		levelup_cost= 25;
		cash_back=(int) (cost*0.8);
	}
	
	public void draw(Graphics g) {
		if(level == 1) {
			g.drawImage(Assets.redditTower, posX, posY);
			cooldown = 1000; // probably bad form to put this here, but its easier than updating 3 times elsewhere
			damage = 20;	// cannot just change these values in the constructor because thats only for when the object is made
			range = 75;
		} else if(level == 2) {
			g.drawImage(Assets.redditTower, posX, posY);
			cooldown = 500;
			damage = 40;
			range = 150;
		} else {
			g.drawImage(Assets.redditTower, posX, posY);
			cooldown = 300;
			damage = 60;
			range = 225;
		}
	}

	@Override
	public RedditProjectile fire(Enemy e) {
		RedditProjectile projectile = new RedditProjectile(e, this, center.x, center.y);
		return projectile;
	}
	
	
}
