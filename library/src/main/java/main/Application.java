package main;

import java.util.Scanner;

import rs.dud.model.Library;

public class Application {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter library owner");
		String libraryOwner = keyboard.nextLine();
		System.out.println("Enter library address");
		String libraryAddress = keyboard.nextLine();
		System.out.println("Enter additional address, if finished press enter on blank");
		keyboard.close();
		
		Library library = new Library(libraryOwner,libraryAddress);
		

	}

}
