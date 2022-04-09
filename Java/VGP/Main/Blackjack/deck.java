import java.util.*;
public class deck {
	protected Vector<card> d;
	protected Vector<card> dealDeck;
	protected Vector<card> discard;
	protected int count;

	public static void main(String[] args){
		deck d = new deck();
		d.displayDeck();
	}

	public deck(){
		this(1);
	}

	public deck(int n){
		d = new Vector<card>();
		for (int i = 1; i <14; i++){
			for (int i2 = 0; i2 <4; i2++){
				for (int i3 = 0; i3 <n; i3++){
					//System.out.println(i +" " + i2 +" " + i3);
					d.add(new card(i, getSuite(i2)));
				}
			}
		}
		this.doStuff();
	}
	public deck(deck d1){
		d = new Vector<card>();
		for(card c : d1.getDeck()){
			d.add(c);
		}
		this.doStuff();
	}

	public void doStuff(){
		d = new Vector<card>(shuffle());
		dealDeck = new Vector<card>(d);
		discard = new Vector<card>();
	}

	public Vector<card> shuffle(){
		for(card c : d){
			c.faceup(true);
		}
		Vector<card> shuffle = new Vector<card>();
		while(!d.isEmpty()){
			int a = (int)(d.size()*Math.random());
			card c = new card(d.get(a));
			d.remove(a);
			shuffle.add(c);
		}
		return shuffle;
	}

	public void displayDeck(){
		System.out.println(d);
	}

	public Vector<card> getDeck(){
		return d;
	}

	public Vector<card> getDiscard(){
		return discard;
	}

	public Vector<card> getDealdeck(){
		return dealDeck;
	}

	private char getSuite(int s){
		switch(s){
		case 0:
			return 'C';
		case 1:
			return 'D';
		case 2:
			return 'H';
		case 3:
			return 'S';
		default:
			return 'X';
		}
	}



}
