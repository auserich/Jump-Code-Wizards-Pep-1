package cWBook.book.menu;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;
import cWBook.book.dao.*;
import cWBook.book.user.User;

// class that handles CRUD UI and DAO calls
public class CrudMenu {
	
	static BookDao bookDao = new BookDaoImpl();
	private Optional<User> user;

	public CrudMenu(Optional<User> user) {
		this.user = user;
	}
	
	public void display() {
		
	}
	
	// prints out options for user inputs
	//TODO rename it to something more meaningful
	public void getUserInput() throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
		bookDao.establishConnection();
		
		System.out.print("Enter 'Create', 'Read', 'Update' or 'Delete': ");
		getUserCommand(scanner);
	}
	
	// performs different CRUD operations based on user input
	//TODO finish implementation for rest of CRUD methods
	public void getUserCommand(Scanner scanner) throws SQLException {
		String input = scanner.nextLine();
		switch (input) {
			case "Create":
				System.out.print("Enter name for book: ");
				String name = scanner.nextLine();
				createBook(name);
				
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
	
	public void displayResults() {
		return;
	}
	
	// checks and creates a book if needed before adding both User and Book IDs to junction table
	public void createBook(String name) throws SQLException {
		// check that book doesn't already exist
		Optional<Book> book = bookDao.findByName(name);
		
		// create book
		if (book == null) {
			book = Optional.of(new Book(name));
			bookDao.add(book);
			bookDao.add(this.user, new Book(name));
		}
		else {
			bookDao.add(this.user, new Book(name));
		}
	}
	
}
