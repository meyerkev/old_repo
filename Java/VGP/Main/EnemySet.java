import java.util.*;
import java.awt.*;
import java.applet.*;

public class EnemySet {
	
	// The set of Barriers in the game.
	private Vector<Enemy> enemies;
	
	public Vector<Enemy> getEnemies(){
		return enemies;
	}
	
	public EnemySet(int screenWidth, int screenHeight, Player p, Applet app){
		enemies = new Vector<Enemy>();
		int size = 30;
		enemies.add(new Enemy(600,240,size,size,300, p, app, "enemy.png"));
		enemies.add(new Enemy(650,240,size,size,300, p, app, "enemy.png"));
		enemies.add(new Enemy(700,240,size,size,300, p, app, "enemy.png"));
		enemies.add(new Enemy(750,240,size,size,300, p, app, "enemy.png"));
		//add powerups here

	}
	
	/**
	 * See if the Player has collided with any of the Barriers.
	 * 
	 * @param movingRight Is the Player moving right.
	 * @param movingLeft Is the Player moving left.
	 * 
	 * @return true if there is a collision between the Player and any Barrier, false otherwise.
	 */
	public boolean checkCollisions(Main m){
		Vector<Enemy> del = new Vector<Enemy>();
		
		for(Enemy e : enemies){
			if(!e.isAlive())
				del.add(e);
		}
		for(Enemy e : del){
			enemies.remove(e);
		}

		
		for(Enemy e : enemies){
			if(e.checkPlayerCollision(m)) {
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
		for(Enemy e : enemies){
			e.scrollEnemy(speed);
		}
	}
	
	public Enemy onEnemy(){
		for(Enemy e : enemies){
			if(e.onEnemy()){
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Draw the PowerUps.
	 * 
	 * @param g The Graphics object created by the Main class.
	 */
	public void paint(Graphics g, boolean paused){
		for(Enemy e : enemies){
			e.setGraphics((Graphics2D)g);
			e.transform();
			e.paint(g, paused);
		}
	}
	

}
