import java.applet.*;
import java.awt.*;
public class slidingSpike extends Spike{
	private int movingDistance;
	private int startingX;
	private boolean movingRight = true;
	private int xChange;
	public slidingSpike(int x, int y, int w, int h, int moveDistance, Player p){
		super(x,y,w,h,p);
		startingX = x;
		movingDistance = moveDistance;
	}
	
	public slidingSpike(int x, int y, int w, int h, int moveDistance, Player p, Applet a, String img){
		super(x,y,w,h,p, a,img);
		startingX = x;
		movingDistance = moveDistance;
	}
	public void paint(Graphics g, boolean paused){
		if(!paused){
			updatePosition();
		}
		if(image!=null){
			transform();
			super.draw();
			return;
		}	
		int[] xValues = {xpos,(xpos+(width/2)),xpos+width};
		int[] yValues = {ypos+height, ypos, ypos+height};
		g.setColor(Color.yellow);
		g.fillPolygon(xValues, yValues, xValues.length);
	}
	private void updatePosition(){
		if(xpos < startingX + movingDistance && movingRight){
			xpos += 2;
			xChange = 2;
			if(xpos >= startingX + movingDistance){
				movingRight = false;
			}
		}
		else if(!movingRight){
			xpos -= 2;
			xChange = -2;
			if(xpos < startingX){
				movingRight = true;
			}
		}
	}
	public void scrollBarrier(int x){
		startingX -= x;
		xpos -= x;
	}
	public int getMovingSpeed(){
		return xChange;
	}
	public boolean isSlidingSpike(){
		return true;
	}
}
