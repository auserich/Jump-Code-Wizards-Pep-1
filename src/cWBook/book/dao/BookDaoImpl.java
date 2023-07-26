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
	public List<Book> getAll() {
		List<Book> books = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getConnection();
			// We do not need a prepared statement here as this can never be altered by input to cause error
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM book");

			while (rs.next()) {
				books.add(new Book(rs.getInt(1), rs.getString(2), rs.getInt(3)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return books;
	}

	@Override
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
	public Optional<Book> add(Optional<User> user, Book book) throws SQLException {
		PreparedStatement pstmt = this.connection.prepareStatement("INSERT INTO book_user (book_id, user_id) VALUES ((SELECT book_id FROM book WHERE book.name = ?), (SELECT user_id FROM user WHERE user.first_name = ?))");
		pstmt.setString(1, book.getName());
		pstmt.setString(2, user.get().getFirstName());
		pstmt.executeUpdate();
		
		return Optional.ofNullable(book);
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
}
