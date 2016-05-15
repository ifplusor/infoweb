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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import psn.ifplusor.infoweb.demo.persistence.domain.TestData;
import psn.ifplusor.infoweb.demo.persistence.manager.TestDataManager;
import psn.ifplusor.infoweb.organization.persistence.domain.StructType;
import psn.ifplusor.infoweb.organization.persistence.manager.TestOrganizationManager;

@Controller
@RequestMapping("/mvc")
public class MvcController {
	private static final Logger logger = LoggerFactory.getLogger(MvcController.class);
	
	@Resource
	private TestDataManager testDataManager;
	
	@Resource
	private TestOrganizationManager testOrganizationManager;

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
    public ModelAndView getAllData() {
    	logger.debug("In test Data.");
    	
    	Map<String, Object> model = new HashMap<String, Object>();
    	try {
    		List<TestData> data = testDataManager.getAllData();
    		model.put("data", data);
    		
    		List<StructType> structType = testOrganizationManager.getAllStructTypes();
    		model.put("structTypes", structType);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return new ModelAndView("data", model);
    }
}