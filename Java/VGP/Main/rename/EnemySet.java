import java.util.*;
import java.awt.*;
import java.applet.*;

public class EnemySet {

	// The set of Barriers in the game.
	private Vector<Enemy> enemies;
	int size = 50;

	public Vector<Enemy> getEnemies(){
		return enemies;
	}

	public EnemySet(int screenWidth, int screenHeight, Player p, Applet app){
		enemies = new Vector<Enemy>();		
		for (int i = 0;   i < 50; i++){
			enemies.add(new Kagani(200*i,size,screenHeight, p, app));
		}
		enemies.add(new Enemy(600,240,size,size,screenHeight, p, app, "hippo2.png"));
		createEnemies(screenHeight, app, p);
//		enemies.add(new Enemy(650,240,size,size,screenHeight, p, app, "enemy.png"));
//		enemies.add(new Enemy(700,240,size,size,screenHeight, p, app, "enemy.png"));
//		enemies.add(new Enemy(750,240,size,size,screenHeight, p, app, "enemy.png"));
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
	public void paint(Graphics g, boolean paused, Player p){
		for(Enemy e : enemies){
			try{
				if(e.isVisible(p)||e.isKigani()){
					e.setGraphics((Graphics2D)g);
					e.transform();
					e.paint(g, paused);
				}
			}catch(ConcurrentModificationException b){	
			}
		}
	}
	public void createEnemies(int screenHeight, Applet app, Player p){
		int b = (int)(60*Math.random() + 40);
		for(int a = 0; a < b; a ++){
			enemies.add(new Enemy(400+(int)(Math.random()*8600 - size), (int)(Math.random()*(screenHeight - 150)) +50, size,size,screenHeight,p,app,"Hippo2.png"));
			for( int i = 0; i<enemies.size() - 1; i++){
				if((enemies.get(i).getBounds()).intersects(enemies.get(enemies.size()-1).getBounds())||(enemies.get(i).getExtendedBounds()).intersects(p.getBounds())){
					enemies.remove(enemies.size()-1);
					i = enemies.size();
				}
			}

		}
	}
}
