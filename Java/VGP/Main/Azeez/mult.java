
public class mult {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[][] table = createTable(10,10);
		printTable(table);
	}

	public static int[][] createTable(int a, int b){
		int[][] table = new int[a+1][b+1];
		for(int i = 0; i<=a; i++){
			for(int i2 = 0; i2<=b; i2++){
				if(i == 0){
					table[i][i2] = i2;
				}else if(i2 ==0){
					table[i][i2] = i;
				}else{
					table[i][i2] = i * i2;
				}
			}
		}
		return table;
	}

	public static void printTable(int[][] table){
		int len = (Integer.toString(table[table.length-1][table[table.length-1].length-1])).length();
		for(int i = 0; i < table.length; i++){
			for(int i2 = 0; i2<=table[table.length-1].length-1; i2++){
				for(int i3 = 0; i3 < len - Integer.toString(table[i][i2]).length() + 1; i3 ++){
					System.out.print(" ");
				}
				System.out.print(table[i][i2]);
			}
			System.out.println();
		}
	}
}
