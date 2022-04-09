import java.util.*;
import java.awt.*;
import java.applet.*;

public class BarrierSet {
	
	// The set of Barriers in the game.
	private Vector<Barrier> barriers;
	
	public BarrierSet(int screenWidth, int screenHeight, Player p, Applet app){
		barriers = new Vector<Barrier>();
		for (int i =0; i < 26; i++){
			barriers.add(new risingSpike(490 + 10 * i, 170,10,10, 150, p, app, "knife.png"));
		}
		barriers.add(new Barrier(screenWidth/2+70,screenHeight-20,50,20,p, Color.red));
		barriers.add(new Barrier(screenWidth/2+140,screenHeight-100,30,100,p, app, "backwall.png"));
		barriers.add(new Barrier(440,screenHeight-240,40,240,p, app, "backwall.png"));
		barriers.add(new Spike(100, screenHeight -20, 10, 20,p, app, "knife.png"));
		barriers.add(new Spring(200, screenHeight -40, 40, 20,250,p));
		barriers.add(new Spring(400, screenHeight -180, 40, 20,150,p));
		barriers.add(new Spring(700, screenHeight -40, 40, 20,450,p));
		barriers.add(new SlidingPlatform(200, 180,140,10, 250, p, app, "backwall.png"));
		barriers.add(new SlidingPlatform(500, 100,140,10, 100, p, app, "backwall.png"));
		barriers.add(new RisingPlatform(820, 140,120,10, 100, p, app, "backwall.png"));
		//barriers.add(new Barrier(800, 80,140,10, p, app, "backwall.png"));
		barriers.add(new Barrier(750, 80,10,200, p, app, "backwall.png"));
		barriers.add(new Barrier(580, -100,50,150, p, app, "backwall.png"));
		barriers.add(new Healer(600, screenHeight -20, 10, 20,p));
		barriers.add(new Spike(1000, screenHeight -20, 10, 20,p, app, "knife.png"));
		barriers.add(new Barrier(440,screenHeight-240,40,240,p, app, "backwall.png"));
		barriers.add(new Barrier(1990,0,10,screenHeight,p, app, "backwall.png"));
		barriers.add(new Barrier(80,0,10,100,p, app, "backwall.png"));
		barriers.add(new Barrier(940,140,100,10,p, app, "backwall.png"));
		barriers.add(new Barrier(940,-200,100,290,p, app, "backwall.png"));
		barriers.add(new Barrier(760,90,60,190,p, app, "backwall.png"));
		barriers.add(new Barrier(1040,90,10,60,p, app, "backwall.png"));
		barriers.add(new Barrier(940,210,100,10,p, app, "backwall.png"));
		barriers.add(new Barrier(1040,300,10,-90,p, app, "backwall.png"));
		
		//add barriers here
		
		for (int i =0; i < 24; i++){
			barriers.add(new slidingSpike(5 + 10 * i, 100,10,10, 150, p, app, "knife.png"));
		}
		
		for(Barrier thisBarrier : barriers){
			p.addBarrier(thisBarrier);
		}
		for (int i = 0; i < 18; i++){
			barriers.add(new Spike(760 + 10 * i, 80, 10, 10, p, app, "knife.png"));
		}
		for (int i = 0; i < 10; i++){
			barriers.add(new Spike(850 + 10 * i, screenHeight-10, 10, 10, p, app, "knife.png"));
		}
		for (int i =0; i < 10; i++){
			barriers.add(new slidingSpike(760 + 10 * i, 200,10,10, 150, p, app, "knife.png"));
		}
		for (int i = 0; i < 95; i++){
			barriers.add(new Spike(1050 + 10 * i, 290, 10, 10, p, app, "knife.png"));
		}
		for (int i = 0; i < 95; i++){
			barriers.add(new Spike(1050 + 10 * i, 300, 10, 10, p, app, "knife.png"));
		}
	}
	
	/**
	 * See if the Player has collided with any of the Barriers.
	 * 
	 * @param movingRight Is the Player moving right.
	 * @param movingLeft Is the Player moving left.
	 * 
	 * @return true if there is a collision between the Player and any Barrier, false otherwise.
	 */
	public boolean checkCollisions(boolean movingRight, boolean movingLeft){
		for(Barrier thisBarrier : barriers){
			if((thisBarrier.checkPlayerCollision() && movingRight  && thisBarrier.onLeft()) || 
					(thisBarrier.checkPlayerCollision() && movingLeft && thisBarrier.onRight())){
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
	public void scrollBarriers(int speed){
		for(Barrier thisBarrier : barriers){
			thisBarrier.scrollBarrier(speed);
		}
	}
	
	/**
	 * Check if the Player is on any barrier. If the Player is on a SpringBoard, inform the Player.
	 * 
	 * @return The Barrier the Player is currently on.
	 */
	public Barrier onBarrier(){
		for(Barrier thisBarrier : barriers){
			if(thisBarrier.onBarrier()){
				if(thisBarrier.isSpring()){
					((Spring)thisBarrier).springPlayer();
				}
				else if(thisBarrier.isSpike())
					((Spike)thisBarrier).spikePlayer();
				else if(thisBarrier.isHealer())
					((Healer)thisBarrier).healPlayer();
				return thisBarrier;
			}
		}
		return null;
	}
	
	/**
	 * Draw the Barriers.
	 * 
	 * @param g The Graphics object created by the Main class.
	 */
	public void paint(Graphics g, boolean paused){
		for(Barrier thisBarrier : barriers){
			thisBarrier.setGraphics((Graphics2D)g);
			thisBarrier.transform();
			thisBarrier.paint(g, paused);
		}
	}
	
	public Vector<Barrier> getBarriers(){
		return barriers;
	}
	

}
