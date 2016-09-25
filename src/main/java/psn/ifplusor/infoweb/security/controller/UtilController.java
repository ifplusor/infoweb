package psn.ifplusor.infoweb.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import psn.ifplusor.core.common.CodeAndMessage;
import psn.ifplusor.core.security.CAPTCHAUtil;
import psn.ifplusor.infoweb.security.Service.UserService;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;

@Controller
@RequestMapping("/security/util")
public class UtilController {

    private static final Logger logger = LoggerFactory.getLogger(UtilController.class);

    @Resource
    UserService userService;

    @RequestMapping(value = "/CAPTCHA", method = RequestMethod.GET)
    public void generateCAPTCHA(HttpServletRequest request, HttpServletResponse response){
        logger.debug("In security/util/CAPTCHA");

        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        //表明生成的响应是图片
        response.setContentType("image/jpeg");

        String sRand = CAPTCHAUtil.generateCAPTCHACode();
        RenderedImage image = CAPTCHAUtil.generateCAPTCHAImage(sRand);

        // 把产生的验证码存入到Session中
        HttpSession session = request.getSession();
        session.setAttribute("CAPTCHA", sRand);

        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkCAPTCHA", method = RequestMethod.POST)
    public String checkCAPTCHA(HttpServletRequest request, @RequestParam("code") String code) {

        HttpSession session = request.getSession();
        String CAPTCHA = (String) session.getAttribute("CAPTCHA");

        if (CAPTCHA == null) {
            return CodeAndMessage.JSONCodeAndMessage(2, "overtime");
        }

        if (CAPTCHA.equals(code)) {
            session.removeAttribute("CAPTCHA");
            return CodeAndMessage.JSONCodeAndMessage(0, "success");
        } else {
            return CodeAndMessage.JSONCodeAndMessage(1, "error");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
    public String checkUsername(@RequestParam("username") String username) {
        return CodeAndMessage.JSONCodeAndMessageAndResult(0, "success", userService.getUserByName(username)==null);
    }
}
