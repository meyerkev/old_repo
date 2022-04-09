import java.awt.*;
import java.applet.*;
public class RisingPlatform extends Barrier{
	private int maximumHeight;
	private int startingY;
	private boolean rising = true;
	public RisingPlatform(int x, int y, int w, int h, int maxHeight, Player p){
		super(x,y,w,h,p);
		maximumHeight = maxHeight;
		startingY = y;
	}
	public RisingPlatform(int x, int y, int w, int h, int maxHeight, Player p, Applet app,String img){
		super(x,y,w,h,p,app,img);
		maximumHeight = maxHeight;
		startingY = y;
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
		g.setColor(Color.gray);
		g.fillRect(xpos, ypos, width, height);
	}
	private void updatePosition(){
		if(ypos > startingY - maximumHeight && rising){
			ypos -= 1;
			if(ypos <= startingY - maximumHeight){
				rising = false;
			}
		}
		else if(!rising){
			ypos += 1;
			if(ypos >= startingY){
				rising = true;
			}
		}
	}
	public boolean onBarrier(){
		if(p.getX() + p.getWidth() >= xpos && p.getX() <= (xpos + width)
			&& p.getY() + p.getHeight() >= ypos - 5 && p.getY() + getHeight() <= ypos + 5 && !p.getRising()){
			p.setSpring(ypos);
			return true;
		}
		return false;
	}
	public boolean isRisingPlatform(){
		return true;
	}
	public boolean isRising(){
		return rising;
	}
}
