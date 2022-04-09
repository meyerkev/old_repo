import java.awt.Color;
import java.awt.Graphics;
import java.applet.*;
public class Winner extends PowerUp{
	public Winner(int x,int y, int w, int h, Player p, Applet a, String img){
		super(x,y,w,h,p, a, img);
	}
	public Winner(int x,int y, int w, int h, Player p){
		super(x,y,w,h,p);
	}
	
//	public void paint (Graphics g, boolean paused){
//		if(image != null){
//			super.draw();
//		}
//		int[] xValues = {xpos,(xpos + width/2), xpos +width};
//		int[] yValues = {ypos + height, ypos, ypos+ height};
//		g.setColor(Color.orange);
//		g.fillPolygon(xValues, yValues, xValues.length);
//	}
	
	public boolean isWinner(){
		return true;
	}
	
	public void winGame(){
		p.win();
	}
}