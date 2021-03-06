import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
public class Main extends Applet implements Runnable, KeyListener{
	private int scrollSpeed;
	private int screenHeight = 500;
	private int screenWidth = 1000;
	private int gamePosition;
	private int speed;
	private Star [] stars ;
	private static final int rightBarrier = 10000;
	public static final int win = 9900;
	private BarrierSet barriers;
	private PowerUpSet powerups;
	private Font pausedFont = new Font("Sans Serif", Font.BOLD,24);
	private Font normalFont = new Font("Sans Serif", Font.PLAIN,12);
	private long flashTime = 500;
	private boolean flashing = true;
	private long pausePressedTime;
	private static boolean paused = false;
	private static boolean lost = false;
	private Player p;
	private Thread th;
	private boolean left = false;
	private boolean right = false;
	private boolean running = false;
	private boolean up = false;
	private boolean down = false;
	private Graphics2D g2d;
	private BufferedImage backbuffer;
	private int bullets = 200;
	private EnemySet enemies;
	private Vector<FireBall> fireballs = new Vector<FireBall>();
	private Vector<Laser> lasers = new Vector<Laser>();
	//private MidiSequence bgMusic;
	private SoundClip laserSound;
	private SoundClip fireSound;
	private Color bgc = new Color(99,89,49);
	private boolean go;
	private SoundClip hitSound;
	private long startTime;
	private long time = 300000;
	private int score;
	private int kills;
	private boolean won = false;
	private long pausedStartTime;

	public void init(){
		// bgMusic = new MidiSequence("Pink_Panther.mid");
		// bgMusic.setLooping(true);
		// bgMusic.play();
		laserSound = new SoundClip("laser.wav");
		fireSound = new SoundClip("fireball.wav");
		hitSound = new SoundClip("hit.wav");
		resize(screenWidth, screenHeight);
		setBackground(bgc);
		backbuffer = new BufferedImage(rightBarrier, screenHeight, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		int size = 100;
		int size2 = size/2;
		gamePosition = screenWidth/4-size/2;
		speed = 25;
		p = new Player(screenWidth/4-size/2,screenHeight/2 - size2/2, size, size2, getSize().height, this, "raft.jpg");
		enemies = new EnemySet(getSize().width,getSize().height, p, this);
		barriers = new BarrierSet(rightBarrier,getSize().height, p, this);
		powerups = new PowerUpSet(getSize().width,getSize().height, p, this);		
		try{
			for(Barrier thisBarrier: barriers.getBarriers()){
				for(Enemy enemy: enemies.getEnemies()){
					if(enemy.getBounds().intersects(thisBarrier.getBounds())&& !thisBarrier.isBeach()){
						enemy.kill();
					}
				}
				for(PowerUp powerup: powerups.getPowerups()){	
					if(powerup.getExtendedBounds().intersects(thisBarrier.getBounds())){
						powerups.getPowerups().remove(powerup);
					}
				}
				if(p.getBounds().intersects(thisBarrier.getBounds())&& !thisBarrier.isBeach()){
					barriers.getBarriers().remove(thisBarrier);
				}
			}
			for(Enemy enemy: enemies.getEnemies()){
				for(PowerUp powerup: powerups.getPowerups()){
					if(enemy.getBounds().intersects(powerup.getBounds())){
						powerups.getPowerups().remove(powerup);
					}
				}
			}
		}catch(Exception e){}
		fillStarArray();
		addKeyListener(this);
		p.setGraphics(g2d);


	}
	public void start(){
		th = new Thread(this);
		th.start();
	}
	public void run(){
		while(true){
			if (paused){
				time += -getTime() + pausedStartTime;
				pausedStartTime = getTime();
			}
			if(time + startTime-System.currentTimeMillis()< 0 && go){
				lost = true;
				paused = true;
			}
			if(!paused && !p.isDead()){
				updateGame();
				if(gamePosition>win){
					calculateScore();
					won = true;
				}
				if( !go || gamePosition >= rightBarrier - p.getWidth() && !(left&&running) || gamePosition <= 0&&(!go ||(left &&running))){
					scrollSpeed = 0;
				}
				if (scrollSpeed != 0){
					scrollGame();
				}
				try{
					Thread.sleep(speed);
				}
				catch(InterruptedException ex){
				}
			}else if (!p.isDead()){
				long currentTime = System.currentTimeMillis();
				if(pausePressedTime + flashTime < currentTime){
					pausePressedTime = currentTime;
					flashing = !(flashing);
				}
			}else{
				lost = true;
				paused = true;
			}
			repaint();			
		}

	}

	private void fillStarArray(){
		stars = new Star[12000/400];
		for(int i = 0; i < 12000/400; i++){
			stars[i]= new Star(i*400,0, 400,screenHeight , this, "river.png");
			stars[i].setGraphics(g2d);
		}
	}
	private void scrollGame(){
		if(barriers.checkCollisions(scrollSpeed > 0,scrollSpeed < 0)||paused){
			scrollSpeed = 0;
		}
		for (int i = 0; i<stars.length; i++){
			Star star = stars[i];
			star.scrollStar(scrollSpeed);
			int x_pos_star = star.getX();
			int farX = x_pos_star + star.getWidth();
			if (x_pos_star < 0 && farX < 0 || farX > rightBarrier){
				star.setInSight(false);
			}else{
				star.setInSight(true);
			}
		}
		barriers.scrollBarriers(scrollSpeed);
		powerups.scroll(scrollSpeed);
		gamePosition += scrollSpeed;
		enemies.scroll(scrollSpeed);
		for(FireBall thisBall: fireballs){
			thisBall.scroll(scrollSpeed);
		}
		for(Laser thisBall: lasers){
			thisBall.scroll(scrollSpeed);
		}
	}
	public void updateGame(){
		enemies.onEnemy();
		fire();
		powerups.checkCollisions(this, p);
		enemies.checkCollisions(this);
//		if(powerups.checkCollisions(this, p)){
//		p.setX();
//		}
		if(up){
			p.driftUp();
		}
		if (down){
			p.driftDown();
		}
		if(left && running){
			scrollSpeed = -2;
			// p.faceLeft();
		}else if(left){
			scrollSpeed = 0;	
			// p.faceLeft();
		}else if(right && running){
			scrollSpeed = 6;
			// p.faceRight();
		}else if(right){
			scrollSpeed = 4;
			// p.faceRight();
		}else{
			scrollSpeed = 2;
		}
//		if(b != null && b.isSlidingPlatform()){
//		scrollSpeed += ((SlidingPlatform)b).getMovingSpeed();		
//		}
		Vector<Barrier> bar = barriers.getBarriers();
		try{

			for(Barrier thisBarrier: bar){
				for(Enemy enemy: enemies.getEnemies()){
					if(enemy.getBounds().intersects(thisBarrier.getBounds())){
						enemy.barrier();
					}
				}
				for(FireBall thisBall: fireballs){
					if(thisBall.getBounds().intersects(thisBarrier.getBounds())){
						thisBall.kill();
						hitSound.play();
					}
				}
				for(Laser thisShot: lasers){
					if(thisShot.getBounds().intersects(thisBarrier.getBounds())){
						thisShot.kill();
						hitSound.play();
					}
				}
			}
			for(Enemy enemy: enemies.getEnemies()){
				for(FireBall thisBall: fireballs){
					if(thisBall.getBounds().intersects(enemy.getBounds())&&enemy.isVisible(p)){
						thisBall.kill();
						enemy.kill();
						hitSound.play();
						kills += 1;
					}
				}
			}
			for(Laser thisShot: lasers){
				if(thisShot.getBounds().intersects(p.getBounds())){
					thisShot.kill();
					p.getHurt();
					hitSound.play();
				}
			}
			for (int i = 0; i< fireballs.size(); i++){
				if (!fireballs.get(i).isAlive()){
					fireballs.remove(i);
					i--;
				}
			}
			for (int i = 0; i< lasers.size(); i++){
				if (!lasers.get(i).isAlive()){
					lasers.remove(i);
					i--;
				}
			}
		}catch(Exception a){

		}
	}

	public void go(){
		if(!go){
			go =true;
			startTime = System.currentTimeMillis();
			for(Enemy enemy: enemies.getEnemies()){
				enemy.setLast();
			}
		}
	}

	public void keyPressed(KeyEvent e){
		go();
		int keyCode = e.getKeyCode();
		switch(keyCode){
		case KeyEvent.VK_UP:{

			up = true;
			break;
		}
		case KeyEvent.VK_DOWN:{

			down = true;
			break;
		}
		case KeyEvent.VK_LEFT:{
			left = true;
			break;
		}
		case KeyEvent.VK_RIGHT:{
			right = true;
			break;
		}
		case KeyEvent.VK_SPACE:{
			running = true;	
			break;
		}
		case KeyEvent.VK_P:{
			paused  = !paused;			
			if (paused){
				pausedStartTime = getTime();
			}else{
			}
			break;
		}
		case KeyEvent.VK_S:{
			fireSound.play();
			if(bullets>0){
				FireBall f = new FireBall(p.getX() + p.getWidth()/2, p.getY(), 10,10,0,-1);
				fireballs.add(f);
				bullets-=1;
			}
			break;
		}
		case KeyEvent.VK_C:{
			fireSound.play();
			if(bullets>0){
				FireBall f = new FireBall(p.getX() + p.getWidth(), p.getY() + p.getHeight()/2, 10,10,1,0);
				fireballs.add(f);
				bullets-=1;
			}
			break;
		}

		case KeyEvent.VK_Z:{
			fireSound.play();
			if(bullets>0){
				FireBall f = new FireBall(p.getX(), p.getY() + p.getHeight()/2, 10,10,-1,0);
				fireballs.add(f);
				bullets-=1;
			}
			break;
		}

		case KeyEvent.VK_X:{
			fireSound.play();
			if(bullets>0){
				FireBall f = new FireBall(p.getX() + p.getWidth()/2, p.getY(), 10,10,0,1);
				fireballs.add(f);
				bullets-=1;
			}
			break;
		}
		}
	}


	public void keyReleased(KeyEvent e){
		int keyCode = e.getKeyCode();	
		switch(keyCode){
		case KeyEvent.VK_UP:{

			up = false;
			break;
		}
		case KeyEvent.VK_DOWN:{

			down = false;
			break;
		}
		case KeyEvent.VK_LEFT:{
			left = false;
			break;
		}
		case KeyEvent.VK_RIGHT:{
			right = false;
			break;
		}
		case KeyEvent.VK_SPACE:{
			running = false;
			break;
		}	
		}
	}

	public void keyTyped(KeyEvent e){}

	public void update(Graphics g){
		g2d.setPaint(Color.black);
		g2d.fillRect(0, 0, this.getSize().width, this.getSize().height);
		paint(g2d);
		g.drawImage(backbuffer, 0,0,this);
		/*
		 * g2d.setColor(Color.blue); g2d.draw(b.getBounds());
		 * g2d.draw(b2.getBounds()); g2d(p.getBounds());
		 */
	}

	public void paint(Graphics g){
		for( int i = 0; i<stars.length; i++){
			stars[i].transform();
			stars[i].paintStar(g);
		}
		g.setColor(bgc);
		barriers.paint(g,paused);
		g.setColor(Color.black);
		if(!paused){
			for( int i = 0; i<fireballs.size(); i++){
				fireballs.get(i).paint(g);
			}
		}
		for( int i = 0; i<lasers.size(); i++){
			lasers.get(i).paint(g, paused);
		}

		enemies.paint(g,paused,p);		
		powerups.paint(g,paused);
		p.transform();
		p.paint(g,paused);
		g.setColor(Color.green);
		g.drawString("Ammo = " + bullets, 0, 10);
		g.drawString(getTimer(), screenWidth -50, 10);
		g.drawString("ENDPOINT HERE", win - gamePosition +150, 45);
		if (!won){calculateScore();}
		g.drawString("Score = " + score, screenWidth/2 - 50, 10);
		if(lost &&!won){
			g.setFont(pausedFont);
			g.setColor(Color.green);
			g.drawString("You Lose", (getSize().width/2) - 40, (getSize().height/2)- 10);
			g.setFont(normalFont);
		}else if(won){
			paused = true;
			g.setFont(pausedFont);
			g.setColor(Color.green);
			g.drawString("You won with a score of: "+ score, (getSize().width/2) - 40, (getSize().height/2)- 10);
			g.setFont(normalFont);
		}else if(paused && flashing){			
			g.setFont(pausedFont);
			g.setColor(Color.green);
			g.drawString("Paused", (getSize().width/2) - 40, (getSize().height/2)- 10);
			g.setFont(normalFont);
		}
	}

	public void addAmmo(){
		bullets += 20;
	}

	public void shootBullets(){
		if (bullets>0){
			bullets -= 1;
		}
	}
	public void fire(){
		for(Enemy e : enemies.getEnemies()){
			//System.out.println("yeah");
			if(go&&(e).isKigani()&&(e).fire() &&e.getX() + e.getWidth()>=p.getX()-p.getY()&&e.getX()<=p.getX()+p.getWidth()+2*p.getY()){
				Laser f = new Laser(e.getX() + e.getWidth()/2 - 5, e.getY() + e.getHeight(), 4,10, this, "spear.png");
				lasers.add(f);
				laserSound.play();
				//System.out.println("yeah");
			}			
		}
	}
	public void calculateScore(){
		score = 100 *kills + 10*bullets+(int)(10.0 * ((double)(time+startTime-System.currentTimeMillis()))/1000);
	}
	public String getTimer(){
		Long b = getTime();
		int minutes = 0;
		int seconds = 0;
		while(b>=60000){
			minutes +=1;
			b -= 60000;
		}
		while(b>=1000){
			seconds +=1;
			b -= 1000;
		}	
		if(seconds < 10){
			return minutes + ":0" + seconds;	
		}
		return minutes + ":" + seconds;
	}
	//Long.toString(time-startTime+System.currentTimeMillis()
	 public long getTime(){
		return time+startTime-System.currentTimeMillis();
	}
}
