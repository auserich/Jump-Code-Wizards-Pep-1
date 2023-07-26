package cWBook.book;

import java.sql.SQLException;
import cWBook.book.menu.LoginMenu;

public class Main {

	public static void main(String[] args) {
		
		// Prompt user for login information
		LoginMenu login = new LoginMenu();
		login.display();
		try {
			login.getUserInput();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		// TEST FUNCTIONS BELOW
		
	}
}