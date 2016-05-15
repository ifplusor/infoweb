package psn.ifplusor.infoweb.security.Service;

import javax.annotation.Resource;

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
}
