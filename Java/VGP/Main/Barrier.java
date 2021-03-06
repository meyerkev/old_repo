import java.awt.*;
import java.applet.*;


public class Barrier extends GameObject {
	
	// A reference to the Player object.
	protected Player p;
	protected Color clr;
	private boolean left = false;
	private boolean right = false;
	
	
	public Barrier(int x, int y, int w, int h, Player newP){
		super(x,y,w,h);
		p = newP;
		clr = Color.gray;
	}
	
	public Barrier(int x, int y, int w, int h, Player newP, Color c){
		super(x,y,w,h);
		p = newP;
		clr = c;
	}
	public Barrier(int x, int y, int w, int h, Player newP, Applet a, String img){
		super(x,y,w,h,a,img);
		p = newP;
		clr = Color.gray;
	}
	public Barrier(int x, int y, int w, int h, Player newP, Color c, Applet a, String img){
		super(x,y,w,h,a,img);
		p = newP;
		clr = c;
	}

	
	/**
	 * Test if the Player has collided with the Barrier.
	 * 
	 * @return true if there is a collision, false otherwise.
	 */
	public boolean checkPlayerCollision() {
		if(p.getBounds().intersects(getBounds())){
			return true;
		}
		return false;
	}
	
	/**
	 * Move the barrier so it appears that the Player is moving.
	 * 
	 * @param x The change in the x position of the Barrier.
	 */
	public void scrollBarrier(int x){
		xpos -= x;
	}
	
	/**
	 * If the player is over the barrier and is not still rising due to a jump from the ground,
	 * return true. Otherwise return false.
	 * 
	 * @return true If the Player is on the barrier, false otherwise.
	 */
	public boolean onBarrier() {
		if(p.getX()+p.getWidth() >= xpos && p.getX() <= (xpos + width)
		   && ( p.getY()+p.getHeight() >= ypos && p.getY()+p.getHeight() <= ypos+5 && !p.getRising())) {
			return true;
		}
		return false;
			
	}
	
	/**
	 * Check if the Player is on the left of the Barrier.
	 * 
	 * @return true if the Player is on the left of the Barrier, false otherwise.
	 */
	public boolean onLeft() {
		if(p.getX()+p.getWidth() <= xpos+6){
			return true;
		}
		return false;
	}
	
	/**
	 * Check if player is to the right of this barrier. 
	 * We need the -5 due to imprecise collision points.
	 * 
	 * @return true if player is to the right of this barrier, false otherwise.
	 */
	public boolean onRight() {
		if(p.getX() >= (xpos+width)-6){
			return true;
		}
		return false;
	}
	
	/**
	 * Draw the barrier on the Graphics object created in the Main class.
	 * 
	 * @param g The Graphics object.
	 */
	public void paint(Graphics g, boolean paused) {
		if(image!=null){
			transform();
			super.draw();
			return;
		}
		g.setColor(clr);
		g.fillRect(xpos, ypos, width, height);
	}
	

	/**
	 * Is this Barrier a SpringBoard. Always returns false since not all basic Barriers are SpringBoards.
	 *  
	 * @return false.
	 */
	public boolean isSpring() {
		return false;
	}
	
	public boolean isSpike(){
		return false;
	}
	public boolean isSlidingPlatform(){
		return false;
	}
	public boolean isRisingPlatform() {
		return false;
	}
	public boolean isHealer(){
		return false;
	}
}
