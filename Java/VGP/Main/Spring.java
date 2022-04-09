import java.awt.*;
import java.applet.*;
public class Spring extends Barrier{
	private int springFactor;
	
	private boolean loading = false;
	private boolean releasing = false;
	
	private int maxHeight;
	private int minHeight;
	
	private int compactFactor = 1;
	private int bounceFactor = 3;
	
	public Spring(int x, int y,int w, int h, int s, Player p){
		super(x,y,w,h,p);
		maxHeight = y;
		minHeight = y + 10;
		springFactor = s;
	}
	
	public void paint(Graphics g, boolean paused){
		if(!paused){
			updateSpring();
		}
		g.setColor(Color.green);
		g.fillRoundRect(xpos, ypos, width, height, 20, 20);
	}
	
	private void updateSpring(){
		if (loading && ypos <= minHeight){
			ypos += compactFactor;
		}else if(loading){
			loading = false;
			releasing = true;
		}else if(releasing && ypos > maxHeight){
			ypos -= bounceFactor;
		}else if(releasing){
			ypos = maxHeight;
			releasing = false;
			p.setSpring(height);
			p.spring(springFactor,ypos);
		}
	}
	
	public void springPlayer(){
		if(!loading && !releasing){
			loading = true;
		}
	}
	
	public boolean onBarrrier(){
		if(p.getX() + p.getWidth() >= xpos && p.getX() <= (xpos + width) && p.getY() + p.getHeight() >= ypos && p.getY() + p.getHeight() <= ypos + 5 && !p.getRising()){
			p.setSpring(ypos);
			return true;
		}
		return false;
	}
	
	public boolean isSpring(){
		return true;
	}
}