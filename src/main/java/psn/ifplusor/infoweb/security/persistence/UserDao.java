package psn.ifplusor.infoweb.security.persistence;

import java.util.List;

public interface UserDao {

	List<User> getAll();
	
	User getUserByName(String username);

	boolean addUser(String username, String password);
}
