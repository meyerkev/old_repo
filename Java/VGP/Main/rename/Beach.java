import java.applet.Applet;
import java.awt.Color;


public class Beach extends Barrier {

	public Beach(int x, int y, int w, int h, Player newP) {
		super(x, y, w, h, newP);
		// TODO Auto-generated constructor stub
	}

	public Beach(int x, int y, int w, int h, Player newP, Color c) {
		super(x, y, w, h, newP, c);
		// TODO Auto-generated constructor stub
	}

	public Beach(int x, int y, int w, int h, Player newP, Applet a, String img) {
		super(x, y, w, h, newP, a, img);
		// TODO Auto-generated constructor stub
	}

	public Beach(int x, int y, int w, int h, Player newP, Color c, Applet a,
			String img) {
		super(x, y, w, h, newP, c, a, img);
		// TODO Auto-generated constructor stub
	}
	public boolean isBeach(){
		return true;
	}

}
