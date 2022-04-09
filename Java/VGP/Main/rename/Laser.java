import java.awt.*;
import java.applet.*;

public class Laser extends GameObject{

	private boolean alive = true;
	private static final int speed = 6;


	public Laser(int x, int y, int w, int h, Applet app, String filename){
		super(x,y+10,w,h,app, filename);
	}
	public void paint(Graphics g, boolean paused){
		if(isAlive()){	
			if(!paused){
				ypos += speed;
			}
			try{
				if(image!=null){
					transform();
					super.draw();
				}
			}catch(Exception e){
				g.setColor(Color.yellow);
				g.fillRect(xpos, ypos, width, height);
				int[] xValues = {xpos-2,(xpos + width/2), xpos +width+2};
				int[] yValues = {ypos + height, ypos +height + 5, ypos+ height};
				g.setColor(Color.darkGray);
				g.fillPolygon(xValues, yValues, xValues.length);

			}
		}
		//draw();

	}
	public void kill(){
		alive = false;
	}

	public boolean isAlive(){
		return alive;
	}
	public void scroll(int scrollDistance){
		xpos+= -scrollDistance;
	}
}