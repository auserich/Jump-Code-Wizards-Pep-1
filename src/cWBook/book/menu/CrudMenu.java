package cWBook.book.menu;

import java.util.Scanner;

public class CrudMenu extends Menu {

	@Override
	public void display() {
		
	}
	
	@Override
	public void getUserInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter 'Create', 'Read', 'Update' or 'Delete': ");
		getUserCommand(scanner);
	}
	
	public void getUserCommand(Scanner scanner) {
		String input = scanner.nextLine().toLowerCase();
		switch (input) {
			case "Create":
				return;
			case "Read":
				return;
			case "Update":
				return;
			case "Delete":
				return;
			default:
				return;
		}
	}
	
	@Override
	public void displayResults() {
		return;
	}
	
}
