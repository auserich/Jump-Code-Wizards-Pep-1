package cWBook.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import cWBook.book.user.User;



public interface BookDao {
	// this will open the connection
	public void establishConnection() throws ClassNotFoundException, SQLException;
	
	// this method will close the connection
	public void closeConnection() throws SQLException ;
	
	// good testing functions for first draft
	public List<Book> getUserBooks(Optional<User> user) throws SQLException;
	public Optional<Book> findById(int id);
	public Optional<Book> findByName(String name) throws SQLException;
	public Optional<Book> add(Optional<User> user, Optional<Book> book) throws SQLException;
	public Optional<Book> add(Optional<Book> book) throws SQLException;
	public boolean checkRelationship(Optional<User> user, Optional<Book> book) throws SQLException;
	public int getBookId(String name) throws SQLException;
	public boolean updateProgress(Optional<User> user, Optional<Book> book, String progress) throws SQLException;
	public boolean checkByName(String name) throws SQLException;
	public boolean removeBook(Optional<User> user, Optional<Book> book) throws SQLException;
}
