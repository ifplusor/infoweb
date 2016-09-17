/**
 * Created by james on 9/16/16.
 */

package psn.ifplusor.infoweb.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import psn.ifplusor.core.security.CAPTCHAUtil;

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

    @RequestMapping("/CAPTCHA")
    public void generateCAPTCHA(HttpServletRequest request, HttpServletResponse response){
        logger.debug("In security/util/CAPTCHA");

        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        //表明生成的响应是图片
        response.setContentType("image/jpeg");

        String sRand = CAPTCHAUtil.generateCAPTCHACode();
        RenderedImage image = CAPTCHAUtil.generateCAPTCHAImage(sRand);

        HttpSession session = request.getSession(true);
        session.setAttribute("rand",sRand);
        try {
            ImageIO.write((RenderedImage) image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
