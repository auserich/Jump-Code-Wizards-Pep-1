package cWBook.book.menu;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import cWBook.book.user.User;
import cWBook.book.user.UserDao;
import cWBook.book.user.UserDaoImpl;


public class LoginMenu  {
	
	static UserDao userDao = new UserDaoImpl();
	
	// start of login process, displays prompt for user to create account or login
	public Optional<User> startLoginProcess() throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
		userDao.establishConnection();
		Optional<User> user = null;
		
		boolean result = true;
		while (result) {
			System.out.println("Welcome to Progress Tracker");
			System.out.println();
			System.out.println("  1 -- Login as User");
			System.out.println("  2 -- Create New User");
			System.out.println();
			System.out.print(">> ");
			
			String input = scanner.nextLine();
			user = getInitialInput(scanner, input);
			if (user.isPresent())
				break;
		}
		
		return user;
	}
	
	// gets the initial input of user to either create account or login
	public Optional<User> getInitialInput(Scanner scanner, String input) throws ClassNotFoundException, SQLException {
		User user;
		switch (input) {
		case "1":
			user = loginUser(scanner);
			return Optional.ofNullable(user);
		case "2":
			user = createUser(scanner);
			return Optional.ofNullable(user);
		}
		System.out.println("Invalid input.");
		return null;
	}
	
	// handles creation of a user
	public User createUser(Scanner scanner) throws SQLException {
		System.out.println("Creating New User");
		String username;
		
		while (true) {
			System.out.println();
			System.out.print("Enter username: ");
			username = scanner.nextLine();
			boolean result = userDao.checkIfUserExists(username);
			if (!result)
				break;
			System.out.println("Username already taken.");
		}
		
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		System.out.print("Enter first name: ");
		String firstname = scanner.nextLine();
		System.out.print("Enter last name: ");
		String lastname = scanner.nextLine();
		
		User user = new User(firstname, lastname, username, password);
		userDao.addUser(user);
		user.setId(userDao.getId(username));
		return user;
	}

	// handles username and password inputs user provides and prints success status
	public User loginUser(Scanner scanner) throws ClassNotFoundException, SQLException {
		
		Optional<User> user = null;
		String username = getUserUsername(scanner);
		String password = getUserPassword(scanner);
		try {
			user = userDao.authenticateUser(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		if (user != null) {
			System.out.println();
			System.out.println("Login successful.");
			System.out.println();
			return user.get();
		}	
		else {
			System.out.println();
			System.out.println("Login failed. Incorrect username or password.");
			System.out.println();
		}
		return null;
	}
	
	public String getUserUsername(Scanner scanner) {
		System.out.print("Enter username: ");
		String username = scanner.nextLine();
		return username;
	}
	
	public String getUserPassword(Scanner scanner) {
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		return password;
	}
}
