import java.awt.*;
import java.applet.*;
public class SlidingPlatform extends Barrier{
	private int movingDistance;
	private int startingX;
	private boolean movingRight = true;
	private int xChange;
	
	public SlidingPlatform(int x, int y,int w,int h, int md, Player p){
		super(x,y,w,h,p);
		startingX = x;
		movingDistance  = md;
	}
	
	public SlidingPlatform(int x, int y,int w,int h, int md, Player p, Applet a, String img){
		super(x,y,w,h,p, a,img);
		startingX = x;
		movingDistance  = md;
	}
	
	public void paint(Graphics g, boolean paused){		
		if(!paused){
			updatePosition();
		}if(image!=null){
			transform();
			super.draw();
			return;
		}
		g.setColor(Color.gray);
		g.fillRect(xpos,ypos,width,height);		
	}
	
	public void updatePosition(){
		if(xpos < startingX + movingDistance && movingRight){
			xpos += 2;
			xChange = 2;
			if(xpos >= startingX + movingDistance){
				movingRight = false;
			}
		}else if(!movingRight){
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
	
	public boolean isSlidingPlatform(){
		return true;
	}
	
}