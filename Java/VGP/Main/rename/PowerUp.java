import java.awt.*;
import java.applet.*;

public class PowerUp extends GameObject {
	
	// A reference to the Player object.
	protected Player p;
	protected Color clr;
	protected boolean gone;
	
	public PowerUp(int x, int y, int w, int h, Player newP){
		super(x,y,w,h);
		p = newP;
		clr = Color.gray;
	}
	
	public PowerUp(int x, int y, int w, int h, Player newP, Applet a, String img){
		super(x,y,w,h,a,img);
		p = newP;
		clr = Color.yellow;
	}

	
	public PowerUp(int x, int y, int w, int h, Player newP, Color c){
		super(x,y,w,h);
		p = newP;
		clr = c;
	}

	
	/**
	 * Test if the Player has collided with the Barrier.
	 * 
	 * @return true if there is a collision, false otherwise.
	 */
	public boolean checkPlayerCollision(Main m) {
		if(p.getBounds().intersects(getBounds())){
			m.addAmmo();
			return true;
		}
		return false;
	}
	
	/**
	 * Move the barrier so it appears that the Player is moving.
	 * 
	 * @param x The change in the x position of the Barrier.
	 */
	public void scroll(int x){
		xpos -= x;
	}
	
	/**
	 * Draw the barrier on the Graphics object created in the Main class.
	 * 
	 * @param g The Graphics object.
	 */
	public void paint(Graphics g, boolean paused) {
		if(image != null){
			super.draw();
			return;
		}
		g.setColor(clr);
		g.fillOval(xpos, ypos, width, height);
	}
	
	public boolean isGone() {
		return gone;
	}

}
