package psn.ifplusor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mvc")
public class mvcController {

    @RequestMapping("/hello")
    public String hello(){        
        return "hello";
    }
    
    @RequestMapping("/print/{id}")
    public ModelAndView pring(@PathVariable String id) {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("command", "print");
    	model.put("content", id);
    	return new ModelAndView("print", model);
    }
}