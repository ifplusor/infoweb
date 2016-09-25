package psn.ifplusor.infoweb.security.Service;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import psn.ifplusor.infoweb.security.persistence.User;
import psn.ifplusor.infoweb.security.persistence.UserDao;

@Service
public class UserService {

	@Resource(name = "UserDao")
	private UserDao userDao;
	
	public User getUserByName(String username) {
		
		return userDao.getUserByName(username);
	}
	
	public User getCurrentUser() {
		
		return getUserByName(getCurrentUserDetails().getUsername());
	}

	private UserDetails getCurrentUserDetails() {

		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public boolean existUser(String username) {

		return getUserByName(username) != null;
	}

	public boolean registerUser(String username, String password) {

		return userDao.addUser(username, password);
	}
}
