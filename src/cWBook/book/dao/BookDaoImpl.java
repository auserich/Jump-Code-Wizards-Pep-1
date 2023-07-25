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
			Connection conn = ConnectionManager.getConnection();
			Statement stmt = conn.createStatement();
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

}
