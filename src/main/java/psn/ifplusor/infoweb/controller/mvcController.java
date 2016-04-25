package psn.ifplusor.infoweb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import psn.ifplusor.infoweb.persistence.domain.TestData;
import psn.ifplusor.infoweb.persistence.manager.TestDataManager;

@Controller
@RequestMapping("/mvc")
public class mvcController {
	private static final Logger logger = LoggerFactory.getLogger(mvcController.class);
	
	@Resource
	private TestDataManager testDataManager;

    @RequestMapping("/hello")
    public String hello(){
    	logger.debug("In hello.");
        return "hello";
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
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return new ModelAndView("data", model);
    }
}