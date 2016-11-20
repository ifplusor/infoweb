package psn.ifplusor.infoweb.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author james
 * @version 11/5/16
 */

@Controller
@RequestMapping("/security")
public class AccessController {

	@RequestMapping("/denied")
	public ModelAndView denied() {
		return new ModelAndView("guest/accessDenied");
	}
}
