import java.util.*;
public class Password{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		System.out.println("Input your first name followed by your last name separated by a space.");
		String name = input.nextLine();
		//This is an example of calling a constructor
		//This is how you create new objects
		//ClassType name = new Classtype(params);
		StringTokenizer word;
		word = new StringTokenizer(name);
		//This is an example of a non-static method inside the while loop
		//name.method(params);
		while(word.countTokens()<2){
			System.out.println("Improper input.");
			System.out.println("Input your first name followed by your last name separated by a space.");
			name = input.nextLine();
			word = new StringTokenizer(name);
		}
		//static method
		//Classtype.method(params);
		String firstName = Password.form(word.nextToken());
		String lastName = Password.form(word.nextToken());
		String userName = Password.getUserName(firstName, lastName);
		String password = Password.getPassword(userName);
		System.out.println("Your name is: " + firstName + " " + lastName);
		System.out.println("Your User Name is: " + userName);
		System.out.println("Your Password is: " + password);
	}
	//various methods
	public static String form(String firstName){
		firstName = firstName.toLowerCase();
		firstName = Character.toUpperCase((firstName.charAt(0))) + firstName.substring(1);
		return firstName;
	}
	public static String getUserName(String first, String last){
		if(last.length() >7){
			last = last.substring(0,7);
		}
		return (last + first.charAt(0)).toUpperCase();
	}
	public static String getPassword(String user){
		int counter = 1;
		user = user.toLowerCase();
		String Password = Character.toString(user.charAt(0));
		while(counter < user.length()){
			Password += Integer.toString(counter) + user.charAt(counter);
			counter ++;
		}
		return Password;
	}

}