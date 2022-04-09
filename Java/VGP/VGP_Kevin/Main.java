import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
public class Main extends Applet implements Runnable, KeyListener{
	private int scrollSpeed;
	private int gamePosition;
	private int speed;
	private Star [] stars ;
	private static final int rightBarrier = 2000;
	private static final int leftBarrier = 0;
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
	private boolean jump = false;
	private Graphics2D g2d;
	private BufferedImage backbuffer;
	private int score = 0;
	private EnemySet enemies;
	private Vector<FireBall> fireballs = new Vector<FireBall>();
	private Vector<Laser> lasers = new Vector<Laser>();
	private MidiSequence bgMusic;
	private SoundClip jumpSound;
	private SoundClip laserSound;
	private SoundClip fireSound;
	//private SoundClip hitSound;

	public void init(){
		bgMusic = new MidiSequence("Pink_Panther.mid");
		bgMusic.setLooping(true);
		bgMusic.play();
		laserSound = new SoundClip("laser.wav");
		fireSound = new SoundClip("fireball.wav");
		//hitSound = new SoundClip("hit.wav");

		backbuffer = new BufferedImage(rightBarrier, 300, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		setBackground(Color.black);
		gamePosition = 180;
		speed = 25;
		int size = 50;
		int size2 = (int)(.89 * ((double)size)/1.1);
		p = new Player((getSize().width/2)-size/2,getSize().height - size2, size, size2, getSize().height, this, "Player2.png");
		enemies = new EnemySet(getSize().width,getSize().height, p, this);
		barriers = new BarrierSet(getSize().width,getSize().height, p, this);
		powerups = new PowerUpSet(getSize().width,getSize().height, p, this);
		fillStarArray();
		addKeyListener(this);
		p.setGraphics(g2d);
	}
	public void start(){
		th = new Thread(this);
		th.start();
	}
	public void run(){
		while (true){
			if(!paused && !p.isDead()){
				updateGame();
				if(gamePosition <= leftBarrier && left){
					scrollSpeed = 0;
				}else if(gamePosition >= rightBarrier - p.getWidth() && right){
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
				lose();
			}
			repaint();			
		}
	}

	private void fillStarArray(){
//		Star [] star2 = new Star[301];
//		for(int i = 0; i < 300; i++){
//		Star star = new Star((int)(rightBarrier * Math.random()),(int)(300 * Math.random()), 10,10);
//		star2[i] = star;
//		}
		stars = new Star[5];
		for(int i = 0; i < 5; i++){
			stars[i]= new Star(i*400,0, 400 ,300 , this, "galaxy.png");
			stars[i].setGraphics(g2d);
		}
	}
	private void scrollGame(){
		if(barriers.checkCollisions(scrollSpeed > 0,scrollSpeed < 0)){
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
	}
	public void updateGame(){
		Barrier b = barriers.onBarrier();
		enemies.onEnemy();
		powerups.checkCollisions(this, p);
		enemies.checkCollisions(this);
//		if(powerups.checkCollisions(this, p)){
//		p.setX();
//		}
		if(jump){
			//	jumpSound.play();
			if(b == null){
				if(p.getY() + p.getHeight() == 300){
					//jumpsound.play();
				}
				p.jump(0, running);
			}else{
				p.jump(b.getY(), running);
			}
		}
		if(left && running){
			scrollSpeed = -4;
			p.faceLeft();
		}else if(left){
			scrollSpeed = -2;	
			p.faceLeft();
		}else if(right && running){
			scrollSpeed = 4;
			p.faceRight();
		}else if(right ){
			scrollSpeed = 2;
			p.faceRight();
		}else{
			scrollSpeed = 0;
		}
		if(b != null && b.isSlidingPlatform()){
			scrollSpeed += ((SlidingPlatform)b).getMovingSpeed();		
		}
		Vector<Barrier> bar = barriers.getBarriers();
		for(Barrier thisBarrier: bar){
			for(Enemy enemy: enemies.getEnemies()){
				if(enemy.getBounds().intersects(thisBarrier.getBounds())){
					enemy.barrier();
				}
			}
			for(FireBall thisBall: fireballs){
				if(thisBall.getBounds().intersects(thisBarrier.getBounds())){
					thisBall.kill();
					//hitSound.play();
				}
			}
			for(Laser thisShot: lasers){
				if(thisShot.getBounds().intersects(thisBarrier.getBounds())){
					thisShot.kill();
					//hitSound.play();
				}
			}
		}
		for(Enemy enemy: enemies.getEnemies()){
			for(FireBall thisBall: fireballs){
				if(thisBall.getBounds().intersects(enemy.getBounds())){
					thisBall.kill();
					enemy.kill();
					//hitSound.play();
				}
			}for(Laser thisShot: lasers){
				if(thisShot.getBounds().intersects(enemy.getBounds())){
					thisShot.kill();
					enemy.kill();
					//hitSound.play();
				}
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
		
	}
	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		switch(keyCode){
		case KeyEvent.VK_UP:{

			jump = true;
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
				bgMusic.stop();
			}else{
				bgMusic.play();
			}
			break;
		}
		case KeyEvent.VK_M:{
			fireSound.play();
			if(p.getDirection() == 1){
				FireBall f = new FireBall(p.getX() + p.getWidth(), p.getY() + p.getHeight(), 10,10,p.getDirection());
				fireballs.add(f);
			}else if(p.getDirection() == -1){
				FireBall f = new FireBall(p.getX(),p.getY() + p.getHeight(), 10,10,p.getDirection());
				fireballs.add(f);
			}			
			break;
		}
		case KeyEvent.VK_N:
			if(!paused){
				laserSound.play();
				if(p.getDirection() == 1){
					Laser f = new Laser(p.getX() + p.getWidth(), p.getY() + p.getHeight(), 10,10,p.getDirection());
					lasers.add(f);
				}else if(p.getDirection() == -1){
					Laser f = new Laser(p.getX(),p.getY() + p.getHeight(), 10,10,p.getDirection());
					lasers.add(f);
				}			
				break;
			}
		}
	}


	public void keyReleased(KeyEvent e){
		int keyCode = e.getKeyCode();	
		switch(keyCode){
		case KeyEvent.VK_UP:{
			jump = false;
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
		 g2d.setColor(Color.blue);
		 g2d.draw(b.getBounds());
		 g2d.draw(b2.getBounds());
		 g2d(p.getBounds());
		 */
	}

	public void paint(Graphics g){
		for( int i = 0; i<stars.length; i++){
			stars[i].transform();
			stars[i].paintStar(g);
		}
		for( int i = 0; i<fireballs.size(); i++){
			fireballs.get(i).paint(g);
		}
		for( int i = 0; i<lasers.size(); i++){
			lasers.get(i).paint(g);
		}
		enemies.paint(g,paused);
		barriers.paint(g,paused);
		powerups.paint(g,paused);
		p.transform();
		p.paint(g,paused);
		g.setColor(Color.green);
		g.drawString("Score = "+ score, 0, 10);
		if(lost){
			g.setFont(pausedFont);
			g.setColor(Color.green);
			g.drawString("You Lose", (getSize().width/2) - 40, (getSize().height/2)- 10);
			g.setFont(normalFont);
		}else if(paused && flashing){
			g.setFont(pausedFont);
			g.setColor(Color.green);
			g.drawString("Paused", (getSize().width/2) - 40, (getSize().height/2)- 10);
			g.setFont(normalFont);
		}
	}
	private void lose(){
		lost = true;
		paused = true;
	}

	public void addScore(){
		score += 1;
	}

}
