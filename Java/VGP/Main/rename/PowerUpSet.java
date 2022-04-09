import java.util.*;
import java.awt.*;
import java.applet.*;

public class PowerUpSet {
	
	// The set of Barriers in the game.
	private Vector<PowerUp> powerups;
	int size = 30;
	
	public PowerUpSet(int screenWidth, int screenHeight, Player p, Applet app){
		powerups = new Vector<PowerUp>();

////		powerups.add(new pHeal(50,50,30,30, p, app, "coin.gif"));	
//		for (int i = 0; i< 8; i++){
//			powerups.add(new PowerUp(500 + 30*i,60,30,30, p, app, "ammo.png"));	
//		}
//		powerups.add(new PowerUp(2000,270,30,30, p, app, "ammo.png"));	
//		powerups.add(new PowerUp(600,250,30,30, p, app, "ammo.png"));
//		createPowerups(screenHeight, app, p);
		//add powerups here

	}
	public Vector<PowerUp> getPowerups(){
		return powerups;
	}
	
	/**PowerU
	 * See if the Player has collided with any of the Barriers.
	 * 
	 * @param movingRight Is the Player moving right.
	 * @param movingLeft Is the Player moving left.
	 * 
	 * @return true if there is a collision between the Player and any Barrier, false otherwise.
	 */
	public boolean checkCollisions(Main m, Player p1){
		for(PowerUp p : powerups){
			if(p.checkPlayerCollision(m)) {
				if(!p.isGone()){
					powerups.remove(p);
				}else if(p.isGone()){
					powerups.remove(p);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Have the Barriers move along with the scene.
	 * 
	 * @param speed The rate at which the Barriers are moving.
	 */
	public void scroll(int speed){
		for(PowerUp p: powerups){
			p.scroll(speed);
		}
	}
	
	/**
	 * Draw the PowerUps.
	 * 
	 * @param g The Graphics object created by the Main class.
	 */
	public void paint(Graphics g, boolean paused){
		for(PowerUp p: powerups){
			p.setGraphics((Graphics2D)g);
			p.transform();
			p.paint(g, paused);
		}
	}
	
	public void createPowerups(int screenHeight, Applet app, Player p){
		int b = (int)(60*Math.random() + 40);
		for(int a = 0; a < b; a ++){
			powerups.add(new PowerUp(400+(int)(Math.random()*9600 - size), (int)(Math.random()*(screenHeight - size-60)) +60, size,size,p,app,"ammo.png"));
			for( int i = 0; i<powerups.size() - 1; i++){
				if((powerups.get(i).getBounds()).intersects(powerups.get(powerups.size()-1).getBounds())||(powerups.get(i).getExtendedBounds()).intersects(p.getBounds())){
					powerups.remove(powerups.size()-1);
					i = powerups.size();
				}
			}

		}
	}

}
