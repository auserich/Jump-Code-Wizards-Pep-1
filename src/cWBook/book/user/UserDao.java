package cWBook.book.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
	public void establishConnection() throws ClassNotFoundException, SQLException;
	public void closeConnection() throws SQLException;
	public List<User> findByUsername(String username) throws SQLException;
	public Optional<User> authenticateUser(String username, String password) throws SQLException;
	public boolean checkIfUserExists(String username) throws SQLException;
	public void addUser(User user) throws SQLException;
	public int getId(String username) throws SQLException;
}
