import java.lang.*;
import java.math.*;
import java.util.*;
public class Game{
	public static void main(String[] args){
		Shooter A = new Shooter("Adam", 1.0/3.0, 1);
		Shooter B = new Shooter("Ben", .5, 2);
		Shooter C = new Shooter("Charles", 1, 3);
		int turn;
		int count = 0;
		int duels = 10000;
		System.out.println(A.toString());
		System.out.println(B.toString());
		System.out.println(C.toString() + "\n");
		System.out.println("If "+ A.name + " does not deliberately miss his first shot:");
		while(count < duels){
			count ++;
			A.alive = true;
			B.alive = true;
			C.alive = true;
			Shooter.reset();
			while((A.alive == true && B.alive == true)||(A.alive && C.alive == true)||(C.alive && B.alive == true)||(A.alive && B.alive && C.alive == true)){
				turn = Shooter.getTurn();
				if(A.turnGo == turn && A.alive == true){
					//System.out.println("A");
					if (C.alive == true ){
						if(Shooter.shootAtTarget(A.probability)){
							C.alive = false;
						}
					}else{
						if(Shooter.shootAtTarget(A.probability)){
							B.alive = false;
						}
					}
				}
				if(B.turnGo == turn && B.alive == true){
					//System.out.println("B");
					if (C.alive == true){
						if(Shooter.shootAtTarget(B.probability)){
							B.alive = false;
						}
					}else{
						if(Shooter.shootAtTarget(B.probability)){
							A.alive = false;
						}
					}
				}
				if(C.turnGo == turn && C.alive == true){
					//System.out.println("C");
					if (B.alive == true){
						if(Shooter.shootAtTarget(C.probability)){
							B.alive = false;
						}
					}else{
						if(Shooter.shootAtTarget(C.probability)){
							A.alive = false;
						}
					}
				}
			}
			if(A.alive == true){
				A.counter ++;
			}
			if(B.alive == true){
				B.counter ++;
			}
			if(C.alive == true){
				C.counter ++;
			}
		}
		A.getString(count);
		B.getString(count);
		C.getString(count);
		int surv = A.counter;
		count = 0;
		A.counter = 0;
		B.counter = 0;
		C.counter = 0;
		System.out.println("\nIf " +A.name + " deliberately misses his first shot:");
		while(count < duels){
			count ++;
			A.alive = true;
			B.alive = true;
			C.alive = true;
			Shooter.reset();
			Shooter.skip();
			while((A.alive == true && B.alive == true)||(A.alive && C.alive == true)||(C.alive && B.alive == true)||(A.alive && B.alive && C.alive == true)){
				turn = Shooter.getTurn();
				if(A.turnGo == turn && A.alive == true){
					//System.out.println("A");
					if (C.alive == true ){
						if(Shooter.shootAtTarget(A.probability)){
							C.alive = false;
						}
					}else{
						if(Shooter.shootAtTarget(A.probability)){
							B.alive = false;
						}
					}
				}
				if(B.turnGo == turn && B.alive == true){
					//System.out.println("B");
					if (C.alive == true){
						if(Shooter.shootAtTarget(B.probability)){
							C.alive = false;
						}
					}else{
						if(Shooter.shootAtTarget(B.probability)){
							A.alive = false;
						}
					}
				}
				if(C.turnGo == turn && C.alive == true){
					//System.out.println("C");
					if (B.alive == true){
						if(Shooter.shootAtTarget(C.probability)){
							B.alive = false;
						}
					}else{
						if(Shooter.shootAtTarget(C.probability)){
							A.alive = false;
						}
					}
				}
			}
			if(A.alive == true){
				A.counter ++;
			}
			if(B.alive == true){
				B.counter ++;
			}
			if(C.alive == true){
				C.counter ++;
			}
		}
		A.getString(count);
		B.getString(count);
		C.getString(count);
		if(surv >= A.counter){
			System.out.println("\n" + A.name + " should not miss his first shot");
		}else{
			System.out.println("\n" + A.name + " should miss his first shot");
		}
	}
}