package cWBook.book.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
	public void establishConnection() throws ClassNotFoundException, SQLException;
	
	public void closeConnection() throws SQLException;
	
	public List<User> getAll();
	public Optional<User> findById(int id);
	public List<User> findByUsername(String username) throws SQLException;
	public Optional<User> authenticateUser(String username, String password) throws SQLException;
}
