import java.util.*;
import java.awt.*;
import java.applet.*;


public class BarrierSet {

	// The set of Barriers in the game.
	private Vector<Barrier> barriers;
	public BarrierSet(int screenWidth, int screenHeight, Player p, Applet app){
		barriers = new Vector<Barrier>();
		barriers.add(new Beach(0,0,screenWidth,50,p, app, "beach.png"));
		//barriers.add(new Barrier(220,360,50,100,p, app, "log.png"));
		barriers.add(new Beach(0,screenHeight,screenWidth,1,p, app, "beach.png"));
		//barriers.add(new Barrier(0,screenHeight-50,screenWidth,50,p));
		createBarriers(screenHeight, app, p);
		for(Barrier thisBarrier : barriers){
			p.addBarrier(thisBarrier);
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

	public void createBarriers(int screenHeight, Applet app, Player p){
		int b = (int)(30*Math.random() + 10);
		for(int a = 0; a < b; a ++){
			barriers.add(new Barrier(200+(int)(Math.random()*9800), (int)(Math.random()*(screenHeight - 150)) +50, 10 ,100,p,app,"log2.png"));
			for( int i = 0; i<(barriers.size() - 1); i++){
				if((barriers.get(i).getExtendedBounds()).intersects(barriers.get(barriers.size()-1).getBounds())&& !barriers.get(i).isBeach()||(barriers.get(i).getBounds()).intersects(barriers.get(barriers.size()-1).getBounds())){
					barriers.remove(barriers.size()-1);
					i = barriers.size();
				}
			}
		}

	}


}
