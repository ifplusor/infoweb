package psn.ifplusor.infoweb.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import psn.ifplusor.infoweb.demo.persistence.TestData;
import psn.ifplusor.infoweb.demo.persistence.TestDataDao;
import psn.ifplusor.infoweb.organization.persistence.StructType;
import psn.ifplusor.infoweb.organization.persistence.TestOrganizationDao;

@Controller
public class MvcController {
	private static final Logger logger = LoggerFactory.getLogger(MvcController.class);
	
	@Resource
	private TestDataDao testDataDao;
	
	@Resource
	private TestOrganizationDao testOrganizationDao;

	@RequestMapping(path = "/index")
	public ModelAndView index(@RequestParam(value="result", required=false) Integer result,
							  @RequestParam(value="msg", required=false) String message){
		logger.debug("In index.");

		Map<String, Object> model = new HashMap<String, Object>();

		if (result != null) {
    		model.put("result", result);
    	} else {
    		model.put("result", 0);
    	}
    	if (message != null) {
    		model.put("msg", message);
    	}

		return new ModelAndView("index", model);
	}

    @RequestMapping("/hello")
    public ModelAndView hello(){
    	logger.debug("In hello.");
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
    		    .getAuthentication()
    		    .getPrincipal();
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("message", "welcome " + userDetails.getUsername() + "!");
        return new ModelAndView("hello", model);
    }
    
    @RequestMapping("/print/{id}")
    public ModelAndView print(@PathVariable String id) {
    	logger.debug("In print.");
    	
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("command", "print");
    	model.put("content", id);
    	return new ModelAndView("print", model);
    }
    
    @RequestMapping("/data")
    @Transactional
    public ModelAndView getAllData() {
    	logger.debug("In test Data.");
    	
    	Map<String, Object> model = new HashMap<String, Object>();
    	try {
    		List<TestData> data = testDataDao.getAllData();
    		model.put("data", data);
    		
    		List<StructType> structType = testOrganizationDao.getAllStructTypes();
    		model.put("structTypes", structType);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return new ModelAndView("data", model);
    }
}