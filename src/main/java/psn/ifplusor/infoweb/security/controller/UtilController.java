package psn.ifplusor.infoweb.security.controller;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import psn.ifplusor.core.common.CodeAndMessage;
import psn.ifplusor.infoweb.security.Service.UserService;
import psn.ifplusor.infoweb.security.Service.UtilService;

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
    private UserService userService;

    @Resource
    private UtilService utilService;

    @RequestMapping(value = "/CAPTCHA", method = RequestMethod.GET)
    public void generateCAPTCHA(HttpServletRequest request, HttpServletResponse response){

        if (logger.isDebugEnabled())
            logger.debug("In UtilController#generateCAPTCHA");

        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        //表明生成的响应是图片
        response.setContentType("image/jpeg");

        HttpSession session = request.getSession();
        RenderedImage image = utilService.generateCAPTCHAForSession(session);

        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            utilService.clearCAPTCHAForSession(session);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkCAPTCHA", method = RequestMethod.POST)
    public String checkCAPTCHA(HttpServletRequest request, @RequestParam("code") String code) {

        if (logger.isDebugEnabled())
            logger.debug("In UtilController#checkCAPTCHA");

        HttpSession session = request.getSession();

        try {
            if (utilService.checkCAPTCHAForSession(session, code)) {
                return CodeAndMessage.JSONCodeAndMessage(0, "success");
            } else {
                return CodeAndMessage.JSONCodeAndMessage(1, "error");
            }
        } catch (NotFoundException e) {
            return CodeAndMessage.JSONCodeAndMessage(2, "overtime");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
    public String checkUsername(@RequestParam("username") String username) {

        if (logger.isDebugEnabled())
            logger.debug("In UtilController#checkUsername");

        return CodeAndMessage.JSONCodeAndMessageAndResult(0, "success", !userService.existUser(username));
    }
}
