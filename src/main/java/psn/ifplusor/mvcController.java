package psn.ifplusor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mvc")
public class mvcController {
	private static final Logger logger = LoggerFactory.getLogger(mvcController.class);

    @RequestMapping("/hello")
    public String hello(){
    	logger.debug("In hello.");
        return "hello";
    }
    
    @RequestMapping("/print/{id}")
    public ModelAndView pring(@PathVariable String id) {
    	logger.debug("In print.");
    	
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("command", "print");
    	model.put("content", id);
    	return new ModelAndView("print", model);
    }
}