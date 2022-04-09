import java.util.*;
import java.awt.*;
import java.applet.*;

public class PowerUpSet {
	
	// The set of Barriers in the game.
	private Vector<PowerUp> powerups;
	
	public PowerUpSet(int screenWidth, int screenHeight, Player p, Applet app){
		powerups = new Vector<PowerUp>();

		powerups.add(new pHeal(50,50,30,30, p, app, "coin.gif"));	
		for (int i = 0; i< 8; i++){
			powerups.add(new PowerUp(500 + 30*i,60,30,30, p, app, "coin.gif"));	
		}
		powerups.add(new PowerUp(390,270,30,30, p, app, "coin.gif"));	
		powerups.add(new PowerUp(600,250,30,30, p, app, "coin.gif"));
		powerups.add(new pHeal(980,270,30,30, p, app, "coin.gif"));	
		powerups.add(new Winner(1140,170,30,30, p, app, "coin.gif"));	
		//add powerups here

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
					if((p.ispHeal())){
						p1.heal();
					}else if(p.isWinner()){
						p1.win();
					}
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
	

}
