import java.awt.*;
import java.applet.*;

public class Enemy extends GameObject {

	private int speed = -2;
	private boolean hitBarrier = false;
	private boolean alive = true;
	private int scrollSpeed = 0;
	private int maxScrollDistance = 60;
	private int scrollDistance = 0;
	private int maxRiseDistance ;
	private int riseSpeed = -2;
	private boolean fire = false;
	private long delay = 100;
	private int timer = 0;
	private long lastFire = 0;
	private int screenHeight;
	private Player p;
	
	public Enemy(int x, int y, int w, int h, int screenHeight, Player p){
		super(x,y,w,h);
		maxRiseDistance = ypos-20;
		this.screenHeight = screenHeight;
		this.p = p;
	}
	
	public Enemy(int x, int y, int w, int h, int screenHeight, Player p, Applet a, String img){
		super(x,y,w,h,a,img);
		maxRiseDistance = ypos-20;
		this.screenHeight = screenHeight;
		this.p = p;
	}
	
	private void updatePosition(){
		if(hitBarrier || scrollDistance > maxScrollDistance || scrollDistance < 0){
			speed *= -1;
			hitBarrier = false;
		}
		if(ypos < maxRiseDistance || ypos > screenHeight-height){
			riseSpeed *= -1;
		}
		scrollDistance += speed;
		xpos += speed-scrollSpeed;
		ypos += riseSpeed;
		timer++;
		if(timer > lastFire+delay){
			lastFire += delay;
			fire = true;
		}
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
		   && p.getY()+p.getHeight() >= ypos && p.getY()+p.getHeight() <= ypos+5 && !p.getRising()){
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
		if(fire){
			fire = false;
			return true;
		}
		return false;
	}

}
