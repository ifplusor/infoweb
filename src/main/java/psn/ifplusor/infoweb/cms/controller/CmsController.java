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

import psn.ifplusor.core.common.CodeAndMessage;
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
    public ModelAndView list(@RequestParam(value="pos", required=false, defaultValue="/") String pos,
							 @RequestParam(value="code", required=false, defaultValue="0") Integer code,
							 @RequestParam(value="message", required=false, defaultValue="success") String message) {
    	logger.debug("In cms/list");
    	
    	Map<String, Object> model = new HashMap<String, Object>();
		CodeAndMessage.setCodeAndMessage(model, code, message);
    	
        try {
        	model.put("list", virtualFilesystemService.listFolderAndFile(pos));
        	model.put("pos", pos);
			return new ModelAndView("cms/ls", model);
		} catch (PermissionDeniedFileReadException e) {
			CodeAndMessage.setCodeAndMessage(model, 201, e.getMessage());
			return new ModelAndView("redirect:/index", model);
		} catch (FileNotFoundException e) {
			CodeAndMessage.setCodeAndMessage(model, 101, e.getMessage());
			return new ModelAndView("redirect:ls", model);
		}
    }
	
	@RequestMapping("/mkdir")
    public ModelAndView mkdirPage(@RequestParam(value="pos") String pos) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			model.put("pos", pos);
			if (virtualFilesystemService.testFolderOrFile(pos, true)) {
				CodeAndMessage.setCodeAndMessage(model, 0, "success");
				return new ModelAndView("cms/mkdir", model);
			}

			CodeAndMessage.setCodeAndMessage(model, 102, "指定路径为文件，不能创建子目录！");
		} catch (FileNotFoundException e) {
			CodeAndMessage.setCodeAndMessage(model, 101, e.getMessage());
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
				CodeAndMessage.setCodeAndMessage(model, 0, "创建成功！");
				break;
			case 1:
				CodeAndMessage.setCodeAndMessage(model, 102, "指定路径为文件，不能创建子目录！");
				break;
			case 2:
				CodeAndMessage.setCodeAndMessage(model, 103, "已存在同名目录或文件，不能创建子目录！");
				break;
			}
		} catch (PermissionDeniedFileWriteException e) {
			CodeAndMessage.setCodeAndMessage(model, 202, e.getMessage());
		} catch (FileNotFoundException e) {
			CodeAndMessage.setCodeAndMessage(model, 101, e.getMessage());
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
				CodeAndMessage.setCodeAndMessage(model, 0, "删除成功！");
				model.put("pos", parentPath);
				break;
			case 1:
				CodeAndMessage.setCodeAndMessage(model, 104, "目录不为空，不能进行级联删除！");
				model.put("pos", pos);
				break;
			}
		} catch (PermissionDeniedFileWriteException e) {
			CodeAndMessage.setCodeAndMessage(model, 202, e.getMessage());
		} catch (FileNotFoundException e) {
			CodeAndMessage.setCodeAndMessage(model, 101, e.getMessage());
		}
		
		return new ModelAndView("redirect:../ls", model);
	}
	
	@RequestMapping("/upload")
	public ModelAndView uploadPage(@RequestParam(value="pos") String pos) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			model.put("pos", pos);
			if (virtualFilesystemService.testFolderOrFile(pos, true)) {
				CodeAndMessage.setCodeAndMessage(model, 0, "success");
				return new ModelAndView("cms/upload", model);
			}

			CodeAndMessage.setCodeAndMessage(model, 102, "指定路径为文件，不能上传文件！");
		} catch (FileNotFoundException e) {
			CodeAndMessage.setCodeAndMessage(model, 101, e.getMessage());
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

			CodeAndMessage.setCodeAndMessage(model, 0, "上传成功");
			model.put("pos", pos);
		} catch (PermissionDeniedFileWriteException e) {
			CodeAndMessage.setCodeAndMessage(model, 202, e.getMessage());
        } catch (IOException e) {
			CodeAndMessage.setCodeAndMessage(model, 5, "上传失败");
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
