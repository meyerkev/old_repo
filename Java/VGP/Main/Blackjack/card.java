
public class card {
	protected int value;
	protected char suite;
	protected boolean faceup;
	
	public static void main(String[] args){
		card c= new card(12,'c');
		System.out.println(c);
		System.out.println(c.formalToString());
	}
	
	public card(int v, char s){
		if(valuesOK(v,Character.toUpperCase(s))){
			value = v;
			suite = Character.toUpperCase(s);
			faceup = true;
		}
	}
	
	public card(int v, char s, boolean f){
		if(valuesOK(v,Character.toUpperCase(s))){
			value = v;
			suite = Character.toUpperCase(s);
			faceup = f;
		}
	}
	
	public card(card c){
		value = c.value;
		suite = c.suite;
		faceup = c.faceup;
	}
	
	public void faceup(boolean f){
		faceup = f;
	}
	public boolean isFaceup(){
		return faceup;
	}
	
	public int getValue(){
		return value;
	}
	
	public char returnSuite(){
		return suite;
	}
	
	public String toString(){
		return getValueString() + Character.toString(suite);
	}
	public String formalToString(){
		return longValue() + " of " + longSuite();
	}
	
	
	private String getValueString(){
		switch (value){
			case 1:
				return "A";
			case 11:
				return "J";
			case 12:
				return "Q";
			case 13:
				return "K";
			default:
				return Integer.toString(value);
		}
	}
	
	public String longValue(){
		switch (value){
		case 1:
			return "Ace";
		case 11:
			return "Jack";
		case 12:
			return "Queen";
		case 13:
			return "King";
		default:
			return Integer.toString(value);
	}
	}
	
	public String longSuite(){
		switch (suite){
		case 'C':
			return "Clubs";
		case 'D':
			return "Diamonds";
		case 'H':
			return "Hearts";
		case 'S':
			return "Spades";
		default:
			return "Error";
		}
	}
	private boolean valuesOK(int v, char s){
		if(v>0 &&v<14 &&(s == 'D' || s == 'H' || s == 'C' ||s == 'S')){
			return true;
		}
		System.out.println(v +"" + s);
		System.exit(0);
		return false;
	}
	
	
		
}
