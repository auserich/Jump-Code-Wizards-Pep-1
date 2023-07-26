package cWBook.book.menu;

import java.sql.SQLException;

public abstract class Menu {
	
	public abstract void display();
	
	public abstract void getUserInput() throws ClassNotFoundException, SQLException;
	
	public abstract void displayResults();
	
}
