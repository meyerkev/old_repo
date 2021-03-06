import java.awt.*;
import java.applet.*;

public class Enemy extends GameObject {

	protected int speed = -2;
	protected boolean hitBarrier = false;
	protected boolean alive = true;
	protected int scrollSpeed = 0;
	protected int maxScrollDistance = 20;
	protected int scrollDistance = 0;
	protected int maxRiseDistance ;
	protected int riseSpeed = -2;
	protected boolean fire = false;
	protected long delay = 5000;
	protected long timer = 0;
	protected long lastFire = 0;
	protected int screenHeight;
	protected Player p;
	protected int initY;
	protected boolean Visible;
	protected boolean isKi;
	protected long last;

	public Enemy(int x, int y, int w, int h, int screenHeight, Player p){
		super(x,y,w,h);
		maxRiseDistance = ypos - 20;
		this.screenHeight = screenHeight;
		this.p = p;
	}

	public Enemy(int x, int y, int w, int h, int screenHeight, Player p, Applet a, String img){
		super(x,y,w,h,a,img);
		initY = ypos + 10;
		maxRiseDistance = ypos-10;
		this.screenHeight = screenHeight;
		this.p = p;
	}

	private void updatePosition(){
		if(!isKigani()){
			if(hitBarrier || scrollDistance > maxScrollDistance || scrollDistance < 0){
				speed *= -1;
				hitBarrier = false;
			}
			if(ypos < maxRiseDistance || ypos > screenHeight-height || ypos > initY){
				riseSpeed *= -1;
			}
			scrollDistance += speed;
			xpos += speed-scrollSpeed;
			ypos += riseSpeed;
		}
		timer++;
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
			p.getHurt();
			return true;
		}
		return false;

	}

	public void paint(Graphics g, boolean paused){
		if(isAlive()){
			if(!paused){
				updatePosition();
			}

			if (!(p.getX()+50<getX() && p.getX()-25>getX()) || isKigani()){
				if(image!=null){
					transform();
					super.draw();
					return;
				}
				g.setColor(Color.cyan);
				g.fillRect(xpos, ypos, width, height);
			}
			//draw();

		}

	}
	public void setLast(){
		last = System.currentTimeMillis();
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

//	public boolean fire() {
//	if(isKigani()){
//	return true;
//	}
//	return false;
//	}
	public boolean isKigani(){
		return false;
	}
	public boolean fire(){
		return fire;
	}


	public boolean isVisible(Player p){
		return isKigani()||getX()<p.getX()+p.getWidth() + 150 && getX()+getWidth()>p.getX() - 50&&getY()<p.getY()+ p.getHeight() + 100 &&getY()+getWidth()>p.getY() - 100 ;
	}
}
