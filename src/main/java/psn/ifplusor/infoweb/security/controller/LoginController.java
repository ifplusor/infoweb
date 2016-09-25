package psn.ifplusor.infoweb.security.controller;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import psn.ifplusor.infoweb.security.Service.UserService;
import psn.ifplusor.infoweb.security.Service.UtilService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/guest")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @Resource
    private UtilService utilService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        logger.info("In LoginController#login().");

        return new ModelAndView("guest/login");
    }

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public ModelAndView join() {
        logger.info("In LoginController#join().");

        return new ModelAndView("guest/join");
    }

    @RequestMapping(value = "/op/register", method = RequestMethod.POST)
    public ModelAndView register(HttpServletRequest request, @ModelAttribute RegisterForm registerForm) {
        logger.info("In LoginController#register().");

        if (registerForm.getUsername() == null) {
            // 用户名为空
        }

        if (userService.existUser(registerForm.getUsername())) {
            // 用户名已存在
        }

        if (registerForm.getPassword() == null) {
            // 密码为空
        }

        if (registerForm.getConfirm() == null) {
            // 确认密码为空
        }

        if (!registerForm.getPassword().equals(registerForm.getConfirm())) {
            // 两次输入密码不一致
        }

        if (registerForm.getCode() == null) {
            // 验证码为空
        }

        HttpSession session = request.getSession();

        try {
            if (!utilService.checkCAPTCHAForSession(session, registerForm.getCode())) {
                // 验证码不正确
            }
        } catch (NotFoundException e) {
            // 验证码过期
            e.printStackTrace();
        }

        userService.registerUser(registerForm.getUsername(), registerForm.getPassword());

        return new ModelAndView("redirect:/guest/login");
    }
}
