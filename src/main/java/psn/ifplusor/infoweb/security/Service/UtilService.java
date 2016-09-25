package psn.ifplusor.infoweb.security.Service;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import psn.ifplusor.core.security.CAPTCHAUtil;

import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;

@Service
public class UtilService {

    public RenderedImage generateCAPTCHAForSession(HttpSession session) {
        String CAPTCHA = CAPTCHAUtil.generateCAPTCHACode();
        session.setAttribute("CAPTCHA", CAPTCHA);
        return CAPTCHAUtil.generateCAPTCHAImage(CAPTCHA);
    }

    public boolean checkCAPTCHAForSession(HttpSession session, String code) throws NotFoundException {
        if (code == null)
            throw new IllegalArgumentException("the code could not null.");

        String CAPTCHA = (String) session.getAttribute("CAPTCHA");
        if (CAPTCHA == null) {
            throw new NotFoundException("CAPTCHA not exist");
        }

        return code.toUpperCase().equals(CAPTCHA.toUpperCase()); // 忽略大小写
    }

    public void clearCAPTCHAForSession(HttpSession session) {
        session.removeAttribute("CAPTCHA");
    }
}
