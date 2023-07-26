package cWBook.book.menu;

import java.sql.SQLException;
import java.util.Optional;

import cWBook.book.user.User;

public abstract class Menu {
	
	public abstract void display();
	
	public abstract Optional<User> getUserInput() throws ClassNotFoundException, SQLException;
	
	public abstract void displayResults();
	
}
