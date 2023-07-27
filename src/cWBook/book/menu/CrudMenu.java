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
		System.out.println();
		System.out.println("Welcome to Your Book Progress Tracker");
		System.out.println();
		System.out.println("\t1 -- Add Book");
		System.out.println("\t2 -- Review Progress");
		System.out.println("\t3 -- Update Progress");
		System.out.println("\t4 -- Remove Book");
		System.out.println("\t5 -- Logout");
		System.out.println();
		System.out.print(">> ");
	}
	
	// prints out options for user inputs
	//TODO rename it to something more meaningful
	public void getUserInput() throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
		bookDao.establishConnection();
		
		boolean result = true;
		while (result) {
			display();
			String input = scanner.nextLine();
			result = processUserInput(scanner, input);
		}
	}
	
	// performs different CRUD operations based on user input
	//TODO finish implementation for rest of CRUD methods
	public boolean processUserInput(Scanner scanner, String input) throws SQLException {
		switch (input) {
			case "1":
				System.out.print("Enter name for book: ");
				String name = scanner.nextLine();
				boolean result = addBook(name);
				if (result) {
					System.out.println("Book added.");
				}
				else {
					System.out.println("Book already present.");
				}
			case "2":
				return true;
			case "3":
				return true;
			case "4":
				return true;
			case "5":
				System.out.println("Logging user out.");
			default:
				System.out.println("Invalid input.");

		}
		return true;
	}
	
	// checks and creates a book if needed before adding both User and Book IDs to junction table
	public boolean addBook(String name) throws SQLException {
		// check that book doesn't already exist in the book table
		Optional<Book> book = bookDao.findByName(name);
		
		// if book wasn't found, create book
		if (book == null) {
			book = Optional.of(new Book(name));
			bookDao.add(book);
		}
		else {
			if (bookDao.checkRelationship(this.user, book)) {
				return false;
			}
		}
		
		// add book-user relationship
		bookDao.add(this.user, book);
		book.get().setId(bookDao.getBookId(name));
		return true;
	}
	
	public void reviewProgress() {
		
	}
	
	public void updateBook() {
		
	}
	
	public void removeBook() {
		
	}
	
	public void logout() {
		
	}
	
	public void displayResults() {
		return;
	}
}
