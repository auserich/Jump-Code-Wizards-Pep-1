package cWBook.book;

import java.sql.SQLException;
import java.util.Optional;

import cWBook.book.menu.CrudMenu;
import cWBook.book.menu.LoginMenu;
import cWBook.book.user.User;

public class Main {

	public static void main(String[] args) {
		
		// Prompt user for login information
		LoginMenu login = new LoginMenu();
		Optional<User> user = null;
		login.display();
		try {
			user = login.getUserInput();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		if (user == null)
			return;
		
		// TEST FUNCTIONS BELOW
		CrudMenu crud = new CrudMenu(user);
		try {
			crud.getUserInput();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}