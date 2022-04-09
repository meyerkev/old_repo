import java.awt.*;

public class FireBall extends GameObject{
	private static final int xSpeed = 8;
	private static final int ySpeed = 8;
	private boolean alive = true;
	private int xdirection;
	private int ydirection;
	
	public FireBall(int x, int y, int w, int h, int xdir, int ydir){
		super(x,y,w,h);
		xdirection = xdir;
		ydirection = ydir;
	}
	
	public void paint(Graphics g){
		if(!alive){
			return;
		}	
		ypos += ySpeed * ydirection;
		xpos +=(xSpeed * xdirection);
		g.setColor(Color.black);
		g.fillOval(xpos, ypos, width, height);
	}
	
	public void kill(){
		alive = false;
	}
	public void scroll(int s){
		xpos -= s;
	}
	
	public boolean isAlive(){
		return alive;
	}
}