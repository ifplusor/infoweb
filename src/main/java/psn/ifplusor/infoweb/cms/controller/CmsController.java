package psn.ifplusor.infoweb.cms.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import psn.ifplusor.infoweb.cms.service.PermissionDeniedFileReadException;
import psn.ifplusor.infoweb.cms.service.PermissionDeniedFileWriteException;
import psn.ifplusor.infoweb.cms.service.VirtualFilesystemService;

@Controller
@RequestMapping("/cms")
public class CmsController {

	private static final Logger logger = LoggerFactory.getLogger(CmsController.class);
	
	@Resource
	private VirtualFilesystemService virtualFilesystemService;
	
	@RequestMapping("/ls")
    public ModelAndView list(@RequestParam(value="pos", required=false) String pos,
    						@RequestParam(value="result", required=false) Integer result,
    						@RequestParam(value="msg", required=false) String message){
    	logger.debug("In cms/list");
    	
    	if (pos == null) {
    		pos = "/";
    	}
    	
    	Map<String, Object> model = new HashMap<String, Object>();
    	
    	if (result != null) {
    		model.put("result", result);
    	} else {
    		model.put("result", 0);
    	}
    	if (message != null) {
    		model.put("msg", message);
    	}
    	
        try {
        	model.put("list", virtualFilesystemService.listFolderAndFile(pos));
        	model.put("pos", pos);
			return new ModelAndView("cms/ls", model);
		} catch (PermissionDeniedFileReadException e) {
			model.put("msg", e.getMessage());
			model.put("result", 201);
			return new ModelAndView("redirect:/", model);
		} catch (FileNotFoundException e) {
			model.put("msg", e.getMessage());
			model.put("result", 1);
			return new ModelAndView("redirect:ls", model);
		}
    }
	
	@RequestMapping("/mkdir")
    public ModelAndView mkdirPage(@RequestParam(value="pos") String pos) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			model.put("pos", pos);
			if (virtualFilesystemService.testFolderOrFile(pos, true)) {
				model.put("result", 0);
				return new ModelAndView("cms/mkdir", model);
			}

			model.put("msg", "指定路径为文件，不能创建子目录！");
			model.put("result", 2);
		} catch (FileNotFoundException e) {
			model.put("msg", e.getMessage());
			model.put("result", 1);
		}
		return new ModelAndView("redirect:ls", model);
	}
	
	@RequestMapping("/op/mkdir")
    public ModelAndView mkdir(@RequestParam(value="pos") String pos, @RequestParam(value="dirname") String dirName) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			int result = virtualFilesystemService.createFolder(pos, dirName);
			
			model.put("pos", pos);
			switch (result) {
			case 0:
				model.put("msg", "创建成功！");
				model.put("result", 0);
				break;
			case 1:
				model.put("msg", "指定路径为文件，不能创建子目录！");
				model.put("result", 2);
				break;
			case 2:
				model.put("msg", "已存在同名目录或文件，不能创建子目录！");
				model.put("result", 3);
			}
		} catch (PermissionDeniedFileWriteException e) {
			model.put("msg", e.getMessage());
			model.put("result", 202);
		} catch (FileNotFoundException e) {
			model.put("msg", e.getMessage());
			model.put("result", 1);
		}
		
		return new ModelAndView("redirect:../ls", model);
	}
	
	@RequestMapping("/op/rm")
	public ModelAndView rm(@RequestParam(value="pos") String pos) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String parentPath = virtualFilesystemService.getParentPath(pos);
			int result = virtualFilesystemService.deleteFolderOrFile(pos);
			
			switch (result) {
			case 0:
				model.put("pos", parentPath);
				model.put("msg", "删除成功！");
				model.put("result", 0);
				break;
			case 1:
				model.put("pos", pos);
				model.put("msg", "目录不为空，不能进行级联删除！");
				model.put("result", 4);
			}
		} catch (PermissionDeniedFileWriteException e) {
			model.put("msg", e.getMessage());
			model.put("result", 202);
		} catch (FileNotFoundException e) {
			model.put("msg", e.getMessage());
			model.put("result", 1);
		}
		
		return new ModelAndView("redirect:../ls", model);
	}
	
	@RequestMapping("upload")
	public ModelAndView uploadPage(@RequestParam(value="pos") String pos) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			model.put("pos", pos);
			if (virtualFilesystemService.testFolderOrFile(pos, true)) {
				model.put("result", 0);
				return new ModelAndView("cms/upload", model);
			}
			
			model.put("msg", "指定路径为文件，不能上传文件！");
			model.put("result", 2);
		} catch (FileNotFoundException e) {
			model.put("msg", e.getMessage());
			model.put("result", 1);
		}
		return new ModelAndView("redirect:ls", model);
	}
	
	@RequestMapping("/op/upload")
    public ModelAndView upload(@RequestParam(value="pos") String pos,
    						@RequestParam("uploadfile") MultipartFile uploadFile) {
		logger.debug("In cms/op/upload");
		
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			virtualFilesystemService.uploadFile(pos, uploadFile);
            
			model.put("pos", pos);
            model.put("msg", "上传成功");
            model.put("result", 0);
		} catch (PermissionDeniedFileWriteException e) {
			model.put("msg", e.getMessage());
			model.put("result", 202);
        } catch (IOException e) {
            model.put("msg", "上传失败");
            model.put("result", 5);
		}
        return new ModelAndView("redirect:../ls", model);
    }
	
	@RequestMapping("/op/download")
    public void download(@RequestHeader(value="USER-AGENT", required=false) String agent,
						@RequestParam(value="path") String path, HttpServletResponse response) {
		logger.debug("In cms/op/download");
		
		int result = virtualFilesystemService.downloadFile(agent, path, response);
    }
}
