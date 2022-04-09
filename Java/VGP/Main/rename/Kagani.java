import java.applet.*;
import java.awt.Color;
import java.awt.Graphics;
public class Kagani extends Enemy {
	int size = 50;
//	protected int speed = -2;
//	protected boolean hitBarrier = false;
//	protected boolean alive = true;
//	protected int scrollSpeed = 0;
//	protected int maxScrollDistance = 20;
//	protected int scrollDistance = 0;
//	protected int maxRiseDistance ;
//	protected int riseSpeed = -2;
//	protected boolean fire = false;
//	protected long delay = 100;
//	protected int timer = 0;
//	protected long lastFire = 0;
//	protected int screenHeight;
//	protected Player p;
//	protected int initY;

	public Kagani(int x, int size,int screenHeight, Player p, Applet app){
		super(x,0,size,size, screenHeight, p,app,"Kagani.jpg");
		last = System.currentTimeMillis();
	}
	private void updatePosition(){
//		timer++;
//		if(timer > lastFire+delay){
//		lastFire += delay;
//		fire = true;
//		}
	}

	public boolean checkPlayerCollision(Main m) {
		if(p.getBounds().intersects(getBounds())){
			p.getHurt();
			return true;
		}
		return false;
	}

	public boolean onEnemy() {
		if(p.getX()+p.getWidth() >= xpos && p.getX() <= (xpos + width)
				&& p.getY()+p.getHeight() >= ypos && p.getY()+p.getHeight() <= ypos+5){
			kill();
			return true;
		}
		return false;

	}

	public void paint(Graphics g, boolean paused){
		if(isAlive()){
			if(!paused){
				updatePosition();
			}

			if(image != null){
				transform();
				super.draw();
				return;
			}

			g.setColor(Color.cyan);
			g.fillRect(xpos, ypos, width, height);

			//draw();

		}

	}
	

	public void barrier() {
		hitBarrier = true;
	}

	public void kill() {
		alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setScrollSpeed(int s){
		scrollSpeed = s;
	}

	public int getDirection() {
		return -1;
	}

	public void scrollEnemy(int scrollDistance){
		xpos -= scrollDistance;
	}

	public boolean fire() {
		boolean a = (System.currentTimeMillis() > (last + delay));
		if(a){
			last = System.currentTimeMillis();
			//System.out.println("yeah2");
		}
		return a;
	}
	public boolean isKigani(){
		return true;
	}
	//update laser to be fired by Kagani
	//give spears to Kagani- use laser and image of spear
	//stop the dancing Kagani
	//use laser
	//set hidden enemies

}
