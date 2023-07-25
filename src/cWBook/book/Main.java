package cWBook.book;

import java.sql.SQLException;
import cWBook.book.dao.*;



public class Main {

	public static void main(String[] args) {
			BookDao bookDao = new BookDaoImpl();
		
		// Call establishConnection() first to make sure you are connected to the chef database before
		// you call any other methods
		try {
			bookDao.establishConnection();
			
			
		} catch (ClassNotFoundException | SQLException e1) {
			
			System.out.println("\nCould not connect to the CWBook Database, application cannot run at this time.");
		}
		
		// TEST THE REST OF YOUR METHODS IN THE DAO FROM THIS LINE FORWARD
		
		
		// once done, always close your connection
		try {
			bookDao.closeConnection();
		} catch (SQLException e) {
			System.out.println("Could not close connection properly");
		}
	}

}
