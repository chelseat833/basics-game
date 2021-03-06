package com.teamBasics.CollegeTD;

import com.teamBasics.framework.Graphics;

public class RedditProjectile extends Projectile {

	public RedditProjectile(Enemy target, RedditTower tower, int startX, int startY) {
		super(target, tower, startX, startY);
		size = 5;
		speed = 8;
		maxRange = 275;
		Assets.laser.play(.3f);
	}

	@Override
	public void update() {
		if(visible){
		super.move();
		super.checkCollision();
		}
	}

	@Override
	// add checks for which projectile to shoot
	public void draw(Graphics g) {
		if((target.getPosX()>x-15 && target.getPosY()>y-15) || (target.getPosX()<x+15 && target.getPosY()<y+15))
			g.drawImage(Assets.redditProjectile2,x,y);
		else if((target.getPosX()<x+15 && target.getPosY()>y-15) || (target.getPosX()>x-15 && target.getPosY()<y+15))
			g.drawImage(Assets.redditProjectile,x,y);
		else
			g.drawImage(Assets.redditProjectile3,x,y);
	}
	
}
