package cWBook.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cWBook.book.connection.ConnectionManager;
import cWBook.book.exception.InvalidRatingException;
import cWBook.book.user.User;

public class BookDaoImpl implements BookDao{
	
	private Connection connection = null;

	@Override
	public void establishConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			connection = ConnectionManager.getConnection();
		}
	}

	@Override
	public void closeConnection() throws SQLException {
		connection.close();
	}

	@Override
	// gets all books a user has
	public List<Book> getUserBooks(Optional<User> user) throws SQLException {
		List<Book> books = new ArrayList<>();
		PreparedStatement pstmt = this.connection.prepareStatement("SELECT name, progress, rating FROM book_user JOIN book ON book_user.book_id = book.book_id WHERE user_id = ?");
		pstmt.setInt(1, user.get().getId());
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			books.add(new Book(rs.getString(1), rs.getString(2), rs.getInt(3)));
		}
		return books;
	}

	@Override
	// finds a book based on its book_id
	public Optional<Book> findById(int id) {
		Book book;
		try {
			Statement stmt = this.connection.createStatement();
			// We do not need a prepared statement here as the code only runs if an int is entered and has an optional failsafe
			ResultSet rs = stmt.executeQuery("SELECT * FROM book WHERE book_id = " + id);
			if (rs.next()) {
				book = (new Book(rs.getInt(1), rs.getString(2), rs.getInt(3)));
				return Optional.of(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	// adds a User and a Book to the book_user junction table to establish book ownership
	public Optional<Book> add(Optional<User> user, Optional<Book> book) throws SQLException {
		PreparedStatement pstmt = this.connection.prepareStatement("INSERT INTO book_user (book_id, user_id) VALUES ((SELECT book_id FROM book WHERE book.name = ?), (SELECT user_id FROM user WHERE user.first_name = ?))");
		pstmt.setString(1, book.get().getName());
		pstmt.setString(2, user.get().getFirstName());
		pstmt.executeUpdate();
		
		return book;
	}
	
	@Override
	// checks if a relationship exists between a user and a book
	public boolean checkRelationship(Optional<User> user, Optional<Book> book) throws SQLException {
		int book_id = getBookId(book.get().getName());
		PreparedStatement pstmt = this.connection.prepareStatement("SELECT * FROM book_user WHERE user_id = ? AND book_id = " + book_id);
		pstmt.setInt(1, user.get().getId());
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	// adds a Book to the book table
	public Optional<Book> add(Optional<Book> book) throws SQLException {
		PreparedStatement pstmt = this.connection.prepareStatement("INSERT INTO book (name) VALUES (?)");
		pstmt.setString(1, book.get().getName());
		pstmt.executeUpdate();
		
		return book;
	}
	
	@Override
	// attempts to return a Book from the book table that has the name value passed in, will return null if none is found
	public Optional<Book> findByName(String name) throws SQLException {
		PreparedStatement pstmt = this.connection.prepareStatement("SELECT * FROM book WHERE book.name = ?");
		pstmt.setString(1, name);
		ResultSet rs = pstmt.executeQuery();
		
		// return book if found, otherwise return null
		if (rs.next()) {
			return Optional.of(new Book(rs.getString(2)));
		}
		else {
			return null;
		}
			
	}
	
	@Override
	// get book_id of a book by name
	public int getBookId(String name) throws SQLException {
		PreparedStatement pstmt = this.connection.prepareStatement("SELECT book_id FROM book WHERE name = ?");
		pstmt.setString(1, name);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			return rs.getInt(1);
		}
		else {
			return -1;
		}
	}
	
	@Override
	// updates the progress status of an entry in the book_user junction table
	public boolean updateProgress(Optional<User> user, Optional<Book> book, String progress) throws SQLException {
		int book_id = getBookId(book.get().getName());
		PreparedStatement pstmt = this.connection.prepareStatement("UPDATE book_user SET progress = ? WHERE user_id = ? AND book_id = " + book_id);
		pstmt.setString(1, progress);
		pstmt.setInt(2, user.get().getId());
		
		int result = pstmt.executeUpdate();
		
		if (result == 1)
			return true;
		return false;
	}
	
	@Override
	// updates the progress status of an entry in the book_user junction table
	public boolean updateRating(Optional<User> user, Optional<Book> book, int rating) throws SQLException, InvalidRatingException {
		int book_id = getBookId(book.get().getName());
		// rj cooking
		if (rating > 5 || rating < 1) {
			throw new InvalidRatingException("The rating scale is limited from 1 - 5 ");
		}else {
		PreparedStatement pstmt = this.connection.prepareStatement("UPDATE book_user SET rating = ? WHERE user_id = ? AND book_id = " + book_id);
		pstmt.setInt(1, rating);
		pstmt.setInt(2, user.get().getId());
		
		int result = pstmt.executeUpdate();

		if (result == 1)
			return true;
		}
		return false;
	}
	
	@Override
	// checks if book exists in book table
	public boolean checkByName(String name) throws SQLException {
		PreparedStatement pstmt = this.connection.prepareStatement("SELECT * FROM book WHERE book.name = ?");
		pstmt.setString(1, name);
		ResultSet rs = pstmt.executeQuery();
		
		// return book if found, otherwise return null
		if (rs.next()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	// removes relationship between a user and a book
	public boolean removeBook(Optional<User> user, Optional<Book> book) throws SQLException {
		int book_id = getBookId(book.get().getName());
		PreparedStatement pstmt = this.connection.prepareStatement("DELETE FROM book_user WHERE user_id = ? AND book_id = " + book_id);
		pstmt.setInt(1, user.get().getId());
		int result = pstmt.executeUpdate();
		
		if (result == 1)
			return true;
		return false;
	}
}
