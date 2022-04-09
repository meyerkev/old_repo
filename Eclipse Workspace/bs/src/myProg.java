import java.util.Vector;


public class myProg {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "FourscoreandsevenyearsagoourfaathersbroughtforthonthiscontainentanewnationconceivedinzLibertyanddedicatedtothepropositionthatallmenarecreatedequalNowweareengagedinagreahtcivilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
		String sub = "FOOF";
		System.out.println(reverse("FOOF"));
		if( reverse(sub).equals(sub)){
			System.out.println(sub);
		}

		for(int i = 2; i<s.length(); i++){
			for (int j =1; j<s.length()-i;j++){
				sub = s.substring(j, i+j);
				//System.out.println(sub);
				if( reverse(sub).equals(sub)){
					System.out.println(sub);
				}

			}
			System.out.println(i+ " ");


		}

	}



	public static String reverse(String s){
		String ret = "";
		for (int i =0; i <s.length(); i++){
			ret = s.charAt(i) + ret;

		}
		return ret;


	}
}
