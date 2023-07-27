package cWBook.book.menu;

import java.sql.SQLException;
import java.util.Scanner;
import cWBook.book.user.UserDao;
import cWBook.book.user.UserDaoImpl;


public class LoginMenu extends Menu {
	
	static UserDao userDao = new UserDaoImpl();
	
	@Override
	public void display() {
		System.out.println("Welcome to Progress Tracker");
	}
	
	@Override
	public void getUserInput() throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
		userDao.establishConnection();
		
		boolean result = false;
		while (!result)
		{
			String username = getUserUsername(scanner);
			String password = getUserPassword(scanner);
			try {
				result = userDao.authenticateUser(username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (result)
				System.out.println("Login successful.");
			else
				System.out.println("Login failed. Incorrect username or password.");
		}
		
		userDao.closeConnection();
		//scanner.close();
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
