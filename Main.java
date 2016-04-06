/**
 * Course: EECS 114 Fall 2015
 *
 * First Name: Derek
 * Last Name: Zhang
 * Lab Section: 1A
 * email address: derekz@uci.edu
 *
 *
 * Assignment: assn5
 * Filename : Main.java
 *
 * I hereby certify that the contents of this file represent
 * my own original individual work. Nowhere herein is there 
 * code from any outside resources such as another individual,
 * a website, or publishings unless specifically designated as
 * permissible by the instructor or TA.
 */ 
package assn5;

public class Main {

	public static void main(String[] args) {
		Jug die_hard = new Jug(3,5,4,1,1,1,1,1,1);
		die_hard.display_graph();
		System.out.println("Testing without weights");
		die_hard.solve();
		
		Jug die_hard2 = new Jug(3,5,4,1,2,3,4,5,6);
		System.out.println("Testing with weights");
		die_hard2.solve();
		
		Jug die_hard3 = new Jug(3,5,4,6,5,4,3,2,1);
		System.out.println("Testing with weights");
		die_hard3.solve();

	}
}
