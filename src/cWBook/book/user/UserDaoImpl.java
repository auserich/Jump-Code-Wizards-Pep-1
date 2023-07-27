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
	// checks if user exists in user table based on username
	public boolean checkIfUserExists(String username) throws SQLException {
		
		PreparedStatement pstmt = this.connection.prepareStatement("SELECT * FROM user WHERE user.username = ?");
		pstmt.setString(1, username);
		ResultSet rs = pstmt.executeQuery();
		
		// if user was found with username, then username is already taken
		if (rs.next())
			return true;
		else
			return false;
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

	@Override
	// adds a user to user table
	public void addUser(User user) throws SQLException {
		
		PreparedStatement pstmt = this.connection.prepareStatement("INSERT INTO user (first_name, last_name, username, password) VALUES (?, ?, ?, ?)");
		pstmt.setString(1, user.getFirstName());
		pstmt.setString(2, user.getLastName());
		pstmt.setString(3, user.getUsername());
		pstmt.setString(4, user.getPassword());
		pstmt.executeUpdate();
	}
	
	@Override
	// gets the user_id of a user in the user table from username
	public int getId(String username) throws SQLException {
		
		PreparedStatement pstmt = this.connection.prepareStatement("SELECT user_id FROM user WHERE username = ?");
		pstmt.setString(1, username);
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next())
			return rs.getInt(1);
		else
			return -1;

	}
}
