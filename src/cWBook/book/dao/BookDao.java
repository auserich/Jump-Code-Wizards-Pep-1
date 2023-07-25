package cWBook.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



public interface BookDao {
	// this will open the connection
	public void establishConnection() throws ClassNotFoundException, SQLException;
	
	// this method will close the connection
	public void closeConnection() throws SQLException ;
	
	// good testing functions for first draft
	public List<Book> getAll();
	public Optional<Book> findById(int id);
}
