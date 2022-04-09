import java.awt.*;
import java.applet.*;
public class Spike extends Barrier{
	public Spike (int x,int y, int w, int h, Player p){
		super(x,y,w,h,p);
	}
	
	public Spike (int x,int y, int w, int h, Player p, Applet a, String img){
		super(x,y,w,h,p, a,img);
	}
	
	public void paint (Graphics g, boolean paused){
		if(image != null){
			transform();
			super.draw();
			return;
		}
		int[] xValues = {xpos,(xpos + width/2), xpos +width};
		int[] yValues = {ypos + height, ypos, ypos+ height};
		g.setColor(Color.yellow);
		g.fillPolygon(xValues, yValues, xValues.length);
	}
	
	public boolean isSpike(){
		return true;
	}
	
	public void spikePlayer(){
		p.getHurt();
	}
}