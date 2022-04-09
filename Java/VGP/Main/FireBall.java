import java.awt.*;
import java.applet.*;

public class FireBall extends GameObject{
	private int yInc = 3;
	private static final int xSpeed = 4;
	private static final int maxHeight = 30;
	private boolean alive = true;
	private int direction;
	
	public FireBall(int x, int y, int w, int h, int dir){
		super(x,y,w,h);
		ypos = y - 20;
		direction = dir;
	}
	
	public void paint(Graphics g){
		if(!alive){
			return;
		}
		if(ypos < 300 - maxHeight){
			yInc = 3;
		}else if(ypos + height > 300 ){
			yInc =- 3;
		}
		
		ypos += yInc;
		xpos +=(xSpeed * direction);
		g.setColor(Color.orange);
		g.fillOval(xpos, ypos, width, height);
	}
	
	public void kill(){
		alive = false;
	}
	public boolean isAlive(){
		return alive;
	}
}