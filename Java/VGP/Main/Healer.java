import java.awt.Color;
import java.awt.Graphics;
import java.applet.*;
public class Healer extends Barrier{
	public Healer(int x,int y, int w, int h, Player p){
		super(x,y,w,h,p);
	}
	
	public void paint (Graphics g, boolean paused){
		int[] xValues = {xpos,(xpos + width/2), xpos +width};
		int[] yValues = {ypos + height, ypos, ypos+ height};
		g.setColor(Color.orange);
		g.fillPolygon(xValues, yValues, xValues.length);
	}
	
	public boolean isHealer(){
		return true;
	}
	
	public void healPlayer(){
		p.heal();
	}
}