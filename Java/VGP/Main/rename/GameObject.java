import java.awt.*;
import java.awt.geom.*;
import java.net.*;
import java.applet.*;

public class GameObject {
	protected int xpos;
	protected int ypos;
	protected int width;
	protected int height; 
	protected Image image;
	protected Applet app;
	protected AffineTransform at;
	protected Graphics2D g2d;
	
	public GameObject(int x, int y, int w, int h){
		xpos = x;
		ypos = y;
		width = w;
		height = h;
	}
	
	public GameObject(int x, int y, int w, int h, Applet a, String file){
		xpos = x;
		ypos = y;
		width = w;
		height = h;
		app = a;
		load(file);
	}
	
	public Rectangle getBounds() {
		Rectangle r;
		r = new Rectangle(xpos -1, ypos, width + 2, height);
		return r;
	}
	public Rectangle getExtendedBounds() {
		Rectangle r;
		r = new Rectangle(xpos -50, ypos - 50, width +50, height+50);
		return r;
	}
	
	public int getX(){
		return xpos;
	}
	
	public int getY(){
		return ypos;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public Image getImage(){
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
		double x = app.getSize().width/2 - getWidth()/2;
		double y = app.getSize().width/2 - getHeight()/2;
		at = AffineTransform.getTranslateInstance(x,y);
	}
	
	public void  setGraphics(Graphics2D g) {
		g2d = g;
	}
	
	public URL getURL(String filename) {
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		} catch (Exception ex){}
		
		return url;
	}
	
	public void load(String filename){
		Toolkit tk = Toolkit.getDefaultToolkit();
		image = tk.getImage(getURL(filename));
		while(getImage().getWidth(app) <=0);
		double x = app.getSize().width/2 - getWidth()/2;
		double y = app.getSize().height/2 - getHeight()/2;
		at = AffineTransform.getTranslateInstance(x,y);
	}
	
	public void transform(){
		if(image == null) return;
		at.setToIdentity();
		double xScaleFactor = (((double)getWidth())/((double)image.getWidth(app)));
		double yScaleFactor = (((double)getHeight())/((double)image.getHeight(app)));
		at.translate(getX(),getY());
		at.scale(xScaleFactor, yScaleFactor);
	}
	
	public void draw(){
		g2d.drawImage(getImage(), at, app);
	}
	
	public Applet applet(){
		return app;
	}
}