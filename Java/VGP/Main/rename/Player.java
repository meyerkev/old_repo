import java.awt.*;
import java.util.*;
import java.applet.*;

public class Player extends GameObject{
	//status
	private boolean up = false;
	private boolean down = false;
	// landed on Springboard or jumping
	public boolean dead = false;
	//reference to barrier;
	private Vector<Barrier> barrier;
	//starting screen or groundheight
	//rate of rise and fall
	private int direction = 1;

	//constructor
	public Player(int x,int y,int w, int h, int groundHeight){
		super(x,y,w,h);
		barrier = new Vector<Barrier>();
	}
	
	public Player(int x,int y,int w, int h, int groundHeight, Applet a, String img){
		super(x,y,w,h,a,img);
		barrier = new Vector<Barrier>();
	}
	/*have player begin jumping if on ground or other Barrier
	 * current height = ypos of player when jump;
	 */
	
	/*set springHeight and tell player to spring
	 * 
	 * param springHeight = height to pring to
	 * currentHeight = currentHeight;
	 */
	
	/*draw player
	 * param Graphics g = g2d;
	 */
	public void paint(Graphics g, boolean paused){	
		if(image!=null){
			super.draw();
			return;
		}else if(image != null){
			load("Player2.png");
			transform();
			super.draw();
			if(direction == 1){
				load("Player2.png");
			}else if(direction == -1){
				load("Player.png");
				
			}
		}
		//draw
		
	}
	/*When jumps, adjust height, check if landed, and stop if y;
	 * 
	 */
	
	//}
	public boolean getRising(){
		return up;
	}
	
	public boolean getFalling(){
		return down;
	}

	public void addBarrier(Barrier b){
		if(b !=null) {
			barrier.add(b);
		}
	}

	public void driftUp(){	
		Barrier b = null;
		for(Barrier a: barrier){
			if(a.checkPlayerCollision()){
				b = a;
			}
		}
		up = true;
		down = false;
		if (getRising()&& b == null){
			ypos -= 2;
		}else if (getRising()&&!b.below()){
			ypos -=2;
		}
	}
	public void driftDown(){	
		Barrier b = null;
		for(Barrier a: barrier){
			if(a.checkPlayerCollision()){
				b = a;
			}
		}
		up = false;
		down = true;
		if(getFalling() &&  b == null){
			ypos += 2;
		}else if(getFalling() &&  !b.above()){
			ypos += 2;
		}
	}
	public void faceLeft(){
		load("Player.png");
		direction = -1;
	}
	public void faceRight(){		
		load("Player2.png");
		direction = 1;
	}
	
	public int getDirection(){
		return direction;
	}
	public boolean isDead(){
		return dead;
	}
	
	public void getHurt(){
		dead = true;
	}
}