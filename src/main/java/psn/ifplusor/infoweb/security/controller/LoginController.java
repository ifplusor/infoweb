package psn.ifplusor.infoweb.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(path = "/guest/login", method = RequestMethod.GET)
    public ModelAndView login() {
        logger.info("In LoginController#login().");

        return new ModelAndView("guest/login");
    }

    @RequestMapping(path = "/guest/register", method = RequestMethod.GET)
    public ModelAndView register() {
        logger.info("In LoginController#register().");

        return new ModelAndView("guest/register");
    }
}
