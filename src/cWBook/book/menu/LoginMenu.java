package cWBook.book.menu;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import cWBook.book.user.User;
import cWBook.book.user.UserDao;
import cWBook.book.user.UserDaoImpl;


public class LoginMenu extends Menu {
	
	static UserDao userDao = new UserDaoImpl();
	
	@Override
	public void display() {
		System.out.println("Welcome to Progress Tracker");
	}
	
	@Override
	// handles username and password inputs user provides and prints success status
	public Optional<User> getUserInput() throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
		userDao.establishConnection();
		
		Optional<User> user = null;
		boolean result = false;
		while (result == false)
		{
			String username = getUserUsername(scanner);
			String password = getUserPassword(scanner);
			try {
				user = userDao.authenticateUser(username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (user != null) {
				System.out.println("Login successful.");
				return user;
			}
				
			else
				System.out.println("Login failed. Incorrect username or password.");
		}
		
		userDao.closeConnection();
		scanner.close();
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
	
	@Override
	public void displayResults() {
		return;
	}

}
