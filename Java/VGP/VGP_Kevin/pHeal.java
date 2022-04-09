import java.awt.Color;
import java.awt.Graphics;
import java.applet.*;
public class pHeal extends PowerUp{
	public pHeal(int x,int y, int w, int h, Player p, Applet a, String img){
		super(x,y,w,h,p, a, img);
	}
	public pHeal(int x,int y, int w, int h, Player p){
		super(x,y,w,h,p);
	}
	
//	public void paint (Graphics g, boolean paused){
//		int[] xValues = {xpos,(xpos + width/2), xpos +width};
//		int[] yValues = {ypos + height, ypos, ypos+ height};
//		g.setColor(Color.orange);
//		g.fillPolygon(xValues, yValues, xValues.length);
//	}
	
	public boolean ispHeal(){
		return true;
	}
	
	public void healPlayer(){
		p.heal();
	}
}