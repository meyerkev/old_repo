import java.awt.*;
import java.applet.*;
public class Star extends GameObject{
	private boolean visible;
	public Star(int x, int y, int w, int h){
		super(x,y,w,h);
		visible = true;
	}
	public Star(int x, int y, int w, int h, Applet a, String img){
		super(x,y,w,h, a, img);
		visible = true;
	}
	public void scrollStar(int speed){
		if(image != null){
			xpos -= speed;
		}else{
			xpos -= speed;
		}
	}
	public void setInSight(boolean insight){
		visible = insight;
	}
	public void paintStar(Graphics g){
		if(visible && image == null){
			g.setColor(Color.white);
			g.fillOval(xpos, ypos, width, height);
		}else if(visible && image != null){
			super.draw();
			return;
		}
	}
}