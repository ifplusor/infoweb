package psn.ifplusor.infoweb.cms.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import psn.ifplusor.infoweb.cms.util.FileUtil;
 
 
@Controller
@RequestMapping("/cms/file")
public class FileController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@RequestMapping("/upload")
    public String uploadPage(){
		logger.debug("In cms/file/upload");
		
        return "cms/file/upload";
    }
	
	@RequestMapping("/op/upload")
    public ModelAndView upload(HttpServletRequest request){
		logger.debug("In cms/file/op/upload");
		
        try {
            FileUtil.upload(request);
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("msg", "ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:../list");
    }
    
    @RequestMapping("/list")
    public ModelAndView list(ModelMap model, @RequestParam(value="pos", required=false) String pos){
    	logger.debug("In cms/file/list");
    	
    	if (pos == null) {
    		pos = "/";
    	}
    	
        try {
        	model.put("list", FileUtil.fileList(pos));
        	model.put("pos", pos);
			return new ModelAndView("cms/file/list", model);
		} catch (FileNotFoundException e) {
			return new ModelAndView("redirect:list");
		}
    }
    
    @RequestMapping("/op/download")
    public void download(HttpServletRequest request, HttpServletResponse response){
    	logger.debug("In cms/file/op/download");
    	
        try {
            String path = request.getParameter("path");
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            
            //中文文件名支持
            String encodedfileName = null;
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE")) {//IE
                encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                encodedfileName = new String (fileName.getBytes("UTF-8"), "iso-8859-1");
            } else {
                encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
            
            FileUtil.download(path, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}