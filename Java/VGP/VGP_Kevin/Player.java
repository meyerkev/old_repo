import java.awt.*;
import java.util.*;
import java.applet.*;

public class Player extends GameObject{
	//max jump height & max from current
	private int jumpHeight = 30;
	private int originalWidth;
	private int originalHeight;
	private int currentJumpHeight = jumpHeight;
	//height from spring
	private int springHeight;
	//start height
	private int startingHeight;
	//status
	private boolean rising = false;
	private boolean falling = false;
	// landed on Springboard or jumping
	private boolean springing = false;
	private boolean jumping = false;
	public boolean dead = false;
	//reference to barrier;
	private Vector<Barrier> barrier;
	//starting screen or groundheight;
	private int groundHeight;
	private int springSurface;
	//rate of rise and fall
	private int risingFactor;
	private int fallingFactor;
	//amount of time can't be harmed after spike
	private long invincibleTime = 2000;
	//time when hit spike
	private long hitTime;
	//flashes while invincible
	private boolean flashing = false;
	//right + left - 
	private int direction = 1;

	//constructor
	public Player(int x,int y,int w, int h, int groundHeight){
		super(x,y,w,h);
		this.groundHeight = groundHeight;
		barrier = new Vector<Barrier>();
		originalWidth = w;
		originalHeight = h;
	}
	
	public Player(int x,int y,int w, int h, int groundHeight, Applet a, String img){
		super(x,y,w,h,a,img);
		this.groundHeight = groundHeight;
		barrier = new Vector<Barrier>();
		originalWidth = w;
		originalHeight = h;
	}
	/*have player begin jumping if on ground or other Barrier
	 * current height = ypos of player when jump;
	 */
	public void jump(int currentHeight, boolean running){
		boolean onBarrier = false;
		Barrier b = null;
		for (Barrier thisBarrier : barrier){
			if(thisBarrier.onBarrier()){
				onBarrier = true;
				b = thisBarrier;
				
				break;
			}
		}
		if (onBarrier || ypos == groundHeight - height){
			rising = true;
			startingHeight = currentHeight;
			//if onGround correction
			if (startingHeight == 0){
				startingHeight = groundHeight;
			}
			if(running){
				jumpHeight = 120;
			}else{
				jumpHeight = 60;
			}
			risingFactor = 2;
			ypos -= risingFactor;
			currentJumpHeight = jumpHeight;
			fallingFactor = 2;
			jumping = true;
		}
	}
	/*set springHeight and tell player to spring
	 * 
	 * param springHeight = height to pring to
	 * currentHeight = currentHeight;
	 */
	public void spring(int springHeight, int currentHeight){
			this.springHeight = springHeight;
			rising = true;
			startingHeight = currentHeight;
			risingFactor = 4;
			ypos -= risingFactor;
			falling = false;
			jumping = false;
		
	}
	/*draw p[layer
	 * param Graphics g = g2d;
	 */
	public void paint(Graphics g, boolean paused){	
		if(!paused){
			//check height and invinc.
			updatePlayerHeight();
			checkPlayerState();
		}
		if(image!=null & !flashing){
			super.draw();
			return;
		}else if(image != null){
			load("PlayerG.png");
			transform();
			super.draw();
			if(direction == 1){
				load("Player2.png");
			}else if(direction == -1){
				load("Player.png");
				
			}
		}
		//draw
		if(flashing){
			g.setColor(Color.black);
		}else{
			g.setColor(Color.red);
		}
		g.fillRect(xpos,ypos,width,height);
	}
	/*When jumps, adjust height, check if landed, and stop if y;
	 * 
	 */
	private void updatePlayerHeight(){
		boolean onBarrier = false;
		Barrier b = null;
		for (Barrier thisBarrier : barrier){
			if(thisBarrier.onBarrier()){
				onBarrier = true;
				b = thisBarrier;
				break;
			}
		}
		//set max height = jumpHeight;
		int maxHeight = 0;
		if(jumping){
			maxHeight = currentJumpHeight;
		}else if(springing){
			maxHeight = springHeight;
		}
		if(springing && !rising){
			ypos = springSurface - height;
		}
		if(rising && ypos > startingHeight - (height + maxHeight)){
			ypos -= risingFactor;
		}else if(rising && ypos <= startingHeight - (height + maxHeight)){
			ypos += fallingFactor;
			rising = false;
			falling = true;
			springing = false;
		}else if(falling && ypos <= groundHeight-height && !onBarrier){
			ypos += fallingFactor;
			springing = false;
			if(ypos >= groundHeight - height){
				jumping = false;
				falling = false;
				ypos = groundHeight - height;
			}
		}else if(falling && onBarrier){
			falling = false;
			jumping = false;
			ypos = b.getY() -height;
		}else if (!onBarrier && ypos < groundHeight - height){
			falling = true;
		}
	}

	public void checkPlayerState(){
		if(System.currentTimeMillis() < (hitTime + invincibleTime)){
			flashing = !flashing;
		}else{ 
			flashing = false;
		}
	}

	//return jump status
	public boolean getRising(){
		return rising;
	}
	
	public boolean getFalling(){
		return falling;
	}

	public void addBarrier(Barrier b){
		if(b !=null) {
			barrier.add(b);
		}
	}

	public void setSpring(int surface){
		springing = true;
		springSurface = surface;
	}

	public void getHurt(){
		if(System.currentTimeMillis() < (hitTime + invincibleTime)){
			return;			
		}
		if( width <= originalWidth/4){
			hitTime = System.currentTimeMillis();
			die();
			return;
		}else if( width <= originalWidth/4 && image != null){
			hitTime = System.currentTimeMillis();
			width = 0;
			die();
			return;
		}
		width/= 2;
		height /= 2;
		ypos += (height /2);
		hitTime = System.currentTimeMillis();
	}
	
	public void heal(){
		if( width >= ((int)(originalWidth/4))*4){
			hitTime = System.currentTimeMillis();
			width = originalWidth;
			height = originalHeight;
			return;
		}else if( width >= ((int)(originalWidth/4))*4 && image != null){
			hitTime = System.currentTimeMillis();
			width = originalWidth;
			height = originalHeight;
			return;
		}
		width*= 2;
		height *= 2;
		ypos -= (height /2);
		hitTime = System.currentTimeMillis();
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
	
	public void die(){
		dead = true;
	}
}