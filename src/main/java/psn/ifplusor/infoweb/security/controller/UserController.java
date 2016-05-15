package psn.ifplusor.infoweb.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import psn.ifplusor.infoweb.security.persistence.User;
import psn.ifplusor.infoweb.security.persistence.UserDao;

@Controller
@RequestMapping("/security/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserDao userDao;
	
	@RequestMapping("/list")
	public ModelAndView getList() {
		logger.debug("In UserController#getList().");
		
		List<User> userList = userDao.getAll();
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userList", userList);
		return new ModelAndView("user/list", model);
	}
}
