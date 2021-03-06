import java.awt.*;
import java.applet.*;

public class Laser extends GameObject{

	private boolean alive = true;
	private static final int speed = 3;
	private int direction;

	public Laser(int x, int y, int w, int h, int dir){
		super(x,y,w,h);
		ypos = y - 10;
		direction = dir;
	}
	public void paint(Graphics g){
		if(!alive){
			return;
		}
		xpos +=(speed * direction);
		g.setColor(Color.pink);
		g.fillRect(xpos, ypos, width, height);
	}
	public void kill(){
		alive = false;
	}
	
	public boolean isAlive(){
		return alive;
	}
}