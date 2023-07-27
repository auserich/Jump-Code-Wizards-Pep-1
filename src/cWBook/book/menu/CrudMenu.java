package cWBook.book.menu;

import java.sql.SQLException;
import java.util.List;
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
	
	// displays options users can take
	public void display() {
		System.out.println();
		System.out.println("Welcome to Your Book Progress Tracker");
		System.out.println();
		System.out.println("  1 -- Add Book");
		System.out.println("  2 -- Review Progress");
		System.out.println("  3 -- Update Progress");
		System.out.println("  4 -- Remove Book");
		System.out.println("  5 -- Logout");
		System.out.println();
	}
	
	// gets users input and passes it to processing function
	public void getUserInput() throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
		bookDao.establishConnection();
		
		boolean result = true;
		while (result) {
			display();
			System.out.print(">> ");
			String input = scanner.nextLine();
			result = processUserInput(scanner, input);
		}
	}
	
	// processes user input through switch-case and calls appropriate functions
	public boolean processUserInput(Scanner scanner, String input) throws SQLException {
		String name = null;
		switch (input) {
			// add
			case "1":
				System.out.print("Enter name for book to add: ");
				name = scanner.nextLine();
				boolean addResult = addBook(name);
				if (addResult) {
					System.out.println("Book added.");
				}
				else {
					System.out.println("Book already present.");
				}
				return true;
			// review	
			case "2":
				printUserBooksProgress();
				return true;
			// update	
			case "3":
				System.out.println();
				printUserBooks();
				
				boolean result = false;
				while (!result) {
					System.out.println();
					System.out.print("Enter name for book to update: ");
					name = scanner.nextLine();
					result = bookDao.checkByName(name);
				}
				
				String progress = getUserProgress(scanner);
				System.out.println();
				boolean updateResult = updateBook(name, progress);
				if (updateResult) {
					System.out.println("Book progress updated.");
				}
				else {
					System.out.println("Book not present.");
				}
				return true;
			// remove	
			case "4":
				System.out.println();
				System.out.print("Enter name for book to remove: ");
				name = scanner.nextLine();
				boolean removeResult = removeBook(name);
				if (removeResult) {
					System.out.println("Book removed.");
				}
				else {
					System.out.println("Book not present.");
				}
				return true;
			// logout	
			case "5":
				System.out.println("Logging user out.");
				return false;
				
			default:
				System.out.println("Invalid input.");

		}
		return true;
	}
	
	// prints all books a user has
	public void printUserBooks() throws SQLException {
		List<Book> books = bookDao.getUserBooks(this.user);
		System.out.println("Current Books:");
		for (Book book : books) {
			System.out.println("  " + book.getName());
		}
	}
	
	// prints all books and their progress (eventually rating)
	public void printUserBooksProgress() throws SQLException {
		List<Book> books = bookDao.getUserBooks(this.user);
		System.out.printf("%-30s %-30s %-30s\n", "Current Book", "Progress", "Rating");
		System.out.println();
		for (Book book : books) {
			System.out.printf("%-30s %-30s %-30s\n", book.getName(), book.getProgress(), book.getRating());
		}
	}
	
	// gets desired progress status from user
	public String getUserProgress(Scanner scanner) {
		String input;
		while (true) {
			System.out.println("Enter progress to set to:");
			System.out.println("  1 -- Not Completed");
			System.out.println("  2 -- In Progress");
			System.out.println("  3 -- Completed");
			System.out.println();
			System.out.print(">> ");
			
			input = scanner.nextLine();
			switch (input) {
				case "1":
					return "Not Completed";
				case "2":
					return "In Progress";
				case "3":
					return "Completed";
				default:
					System.out.println("Invalid input.");
			}
		}
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
	
	// removes book from user's books
	public boolean removeBook(String name) throws SQLException {
		// check that book exists
		Optional<Book> book = bookDao.findByName(name);
		
		// if book wasn't found
		if (book == null) {
			return false;
		}
		// if book is found, check that user has it added
		else {
			if (!bookDao.checkRelationship(this.user, book)) {
				return false;
			}
		}
		
		// remove only if book exists and is added by user
		return bookDao.removeBook(this.user, book);
	}
	
	// updates the progress of a book
	public boolean updateBook(String name, String progress) throws SQLException {
		// check that book exists
		Optional<Book> book = bookDao.findByName(name);
		
		// if book wasn't found
		if (book == null) {
			return false;
		}
		// if book is found, check that user has it added
		else {
			if (!bookDao.checkRelationship(this.user, book)) {
				return false;
			}
		}
		
		// update only if book exists and is added by user
		bookDao.updateProgress(this.user, book, progress);
		return true;
	}
}
