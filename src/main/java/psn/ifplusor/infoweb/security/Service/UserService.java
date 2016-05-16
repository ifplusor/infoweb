package psn.ifplusor.infoweb.security.Service;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import psn.ifplusor.infoweb.security.persistence.User;
import psn.ifplusor.infoweb.security.persistence.UserDao;

@Service
public class UserService {

	@Resource
	private UserDao userDao;
	
	public User getUserByName(String username) {
		
		return userDao.getUserByName(username);
	}
	
	public User getCurrentUser() {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return getUserByName(userDetails.getUsername());
	}
}
