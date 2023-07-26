package cWBook.book.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cWBook.book.connection.ConnectionManager;

public class UserDaoImpl implements UserDao {

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
	public List<User> getAll() {
		
		return null;
	}

	@Override
	public Optional<User> findById(int id) {
		
		return Optional.empty();
	}

	@Override
	// returns a list of Users that share the username value passed in
	public List<User> findByUsername(String username) throws SQLException {
		
		List<User> users = new ArrayList<>();
		
		PreparedStatement pstmt = this.connection.prepareStatement("SELECT * FROM user WHERE user.username = ?");
		pstmt.setString(1, username);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			users.add(new User
			(
					rs.getInt(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5))
			);
		}
		
		return users;
	}

	@Override
	// checks that the username and password values match their respective attributes in the User table
	public Optional<User> authenticateUser(String username, String password) throws SQLException {
		
		PreparedStatement pstmt = this.connection.prepareStatement("SELECT * FROM user WHERE user.username = ? AND user.password = ?");
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		ResultSet rs = pstmt.executeQuery();
		
		 if (rs.next())
			 return Optional.of(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
		 else
			 return null;
	}
}
