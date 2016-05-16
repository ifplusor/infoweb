package psn.ifplusor.infoweb.cms.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import psn.ifplusor.infoweb.cms.persistence.File;
import psn.ifplusor.infoweb.cms.persistence.Folder;
import psn.ifplusor.infoweb.cms.persistence.VirtualFilesystemDao;
import psn.ifplusor.infoweb.cms.util.FileUtil;
import psn.ifplusor.infoweb.security.Service.UserService;
import psn.ifplusor.infoweb.security.persistence.User;

@Service
public class VirtualFilesystemService {
	
	@Resource
	private VirtualFilesystemDao virtualFilesystemDao;
	
	@Resource
	private VirtualFilesystemAuthorityService authorityService;
	
	public String getParentPath(String path) throws FileNotFoundException {
		
		if (!path.startsWith("/"))
			throw new FileNotFoundException("非绝对路径不能作为参数, path=\"" + path + "\"");
		
		int index = path.lastIndexOf("/");
		if (index == 0)
			return "/";
		
		return path.substring(0, index);
	}
	
	/**
	 * 获取由路径指定的对象，目录或文件
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	private Object getFolderOrFile(String path) throws FileNotFoundException {
		
		if (!path.startsWith("/"))
			throw new FileNotFoundException("非绝对路径不能作为参数, path=\"" + path + "\"");
		
		Folder folder = virtualFilesystemDao.getTopFolder();
		if (path.equals("/"))
			return folder;
		
		int i = 0;
		String[] folderNames = path.substring(1).split("/");
	fn: for ( ; i < folderNames.length - 1; i++) {
			if (folderNames[i].equals("")) {
				throw new FileNotFoundException("非法的路径参数, path=\"" + path + "\"");
			}
			
			for (Folder childFolder : folder.getChildFolders()) {
				if (childFolder.getName().equals(folderNames[i])) {
					folder = childFolder;
					continue fn;
				}
			}
			
			// can't match child folder
			throw new FileNotFoundException("不存在的目录或文件, path=\"" + path + "\"");
		}
		
		for (Folder childFolder : folder.getChildFolders()) {
			if (childFolder.getName().equals(folderNames[i])) {
				return childFolder;
			}
		}
		for (File file : folder.getFiles()) {
			if (file.getName().equals(folderNames[i])) {
				return file;
			}
		}

		throw new FileNotFoundException("不存在的目录或文件, path=\"" + path + "\"");
	}

	/**
	 * 获取子目录名列表
	 * @note 无安全检查
	 */
	private List<String> getChildFolderList(Folder folder) {
		List<String> folders = new ArrayList<String>();
		if (folder.getId() == 1) { // 顶级目录
			for (Folder childFolder : folder.getChildFolders()) {
				if (childFolder.getId() == 1)
					continue;
				folders.add(childFolder.getName());
			}
		} else {
			for (Folder childFolder : folder.getChildFolders()) {
				folders.add(childFolder.getName());
			}
		}
		folders.sort(new Comparator<String>(){
			public int compare(String arg0, String arg1) {
				return arg0.compareTo(arg1);
			}
		});
		return folders;
	}
	
	/**
	 * 获取子文件名列表
	 * @note 无安全检查
	 */
	private List<String> getChildFileList(Folder folder) {
		List<String> files = new ArrayList<String>();
		for (File childFile : folder.getFiles()) {
			files.add(childFile.getName());
		}
		files.sort(new Comparator<String>(){
			public int compare(String arg0, String arg1) {
				return arg0.compareTo(arg1);
			}
		});
		return files;
	}

	@Transactional
	public Map<String, Object> listFolderAndFile(String path) 
			throws PermissionDeniedFileReadException, FileNotFoundException {
		
		Object obj = getFolderOrFile(path);

		Map<String, Object> map = new HashMap<String, Object>();
        
		if (obj instanceof Folder) {
			Folder folder = (Folder) obj;
			
			authorityService.testReadAuthority(folder);
			
			map.put("type", 1);
        	map.put("folders", getChildFolderList(folder));
        	map.put("files", getChildFileList(folder));
		} else {
			File file = (File) obj;
			
			authorityService.testReadAuthority(file);
			
			List<String> files = new ArrayList<String>();
			files.add(file.getName());
			
			map.put("type", 0);
        	map.put("files", files);
		}
		
		return map;
	}
	
	/**
	 * 测试指定路径类型
	 * 
	 * @param path
	 * @param isFolder - true: 测试目标为文件夹; false: 测试目标为文件.
	 * @return
	 * @throws FileNotFoundException
	 */
	@Transactional
	public boolean testFolderOrFile(String path, boolean isFolder) throws FileNotFoundException {
		
		Object obj = getFolderOrFile(path);
		
		if (isFolder && obj instanceof Folder || !isFolder && obj instanceof File)
			return true;

		return false;
	}
	
	/**
	 * 测试文件夹下是否包含给定名称的子项
	 */
	private boolean containChildWithName(Folder folder, String name) {
		for (Folder childFolder : folder.getChildFolders()) {
			if (childFolder.getName().equals(name))
				return true;
		}
		for (File childFile : folder.getFiles()) {
			if (childFile.getName().equals(name))
				return true;
		}
		return false;
	}
	
	/**
	 * 在指定目录下创建文件夹
	 * 
	 * @param path
	 * @param name
	 * @return 0: 创建成功;<br/> 1: 指定路径为文件;<br/> 2: 存在给定名称子项.
	 * @throws FileNotFoundException 指定路径不存在
	 * @throws  
	 */
	@Transactional
	public int createFolder(String path, String name)
			throws PermissionDeniedFileWriteException, FileNotFoundException {
		
		Object obj = getFolderOrFile(path);
		
		if (obj instanceof File)
			return 1;
		
		Folder folder = (Folder) obj;
		
		authorityService.testWriteAuthority(folder);
		
		if (containChildWithName(folder, name))
			return 2;
		
		Folder childFolder = new Folder();
		childFolder.setParentFolder(folder);
		childFolder.setOwner(authorityService.getCurrentUser());
		childFolder.setName(name);
		childFolder.setAuthority("rw----");	
		
		virtualFilesystemDao.saveFolder(childFolder);
		
		return 0;
	}
	
	@Transactional
	public int modifyFolder(String path, String name) {
		
		return 0;
	}
	
	@Transactional
	public int uploadFile(String path, MultipartFile multiFile)
			throws PermissionDeniedFileWriteException, IOException {
		
		Object obj = getFolderOrFile(path);
		
		if (obj instanceof File)
			return 1;
		
		Folder folder = (Folder) obj;
		
		authorityService.testWriteAuthority(folder);
		
		try {
			String uri = FileUtil.saveMultipartFile(multiFile);
			
			File file = new File();
			file.setParentFolder(folder);
			file.setOwner(authorityService.getCurrentUser());
			file.setName(multiFile.getOriginalFilename());
			file.setAuthority("rw----");
			file.setUri(uri);
			
			virtualFilesystemDao.saveFile(file);
		} catch (IOException e) {
			throw e;
		}
		
		return 0;
	}
	
	@Transactional
	public int downloadFile(String agent, String path, HttpServletResponse response) {
		
		try {
            Object obj = getFolderOrFile(path);
    		
    		if (obj instanceof Folder) {
    			String msg = java.net.URLEncoder.encode("指定路径为目录，不能下载！","utf-8"); 
    			response.sendRedirect("../ls?pos=" + path + "&result=2&msg=" + msg);
    			return 1;
    		}
    		
    		File file = (File) obj;
    		
    		authorityService.testReadAuthority(file);
    		
            String fileName = file.getName();
            String uri = file.getUri();
            
            //中文文件名支持
            String encodedfileName = null;
            if (null != agent && -1 != agent.indexOf("MSIE")) {//IE
                encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                encodedfileName = new String (fileName.getBytes("UTF-8"), "iso-8859-1");
            } else {
                encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
            
            FileUtil.downloadByUri(uri, response.getOutputStream());
		} catch (FileNotFoundException e) {
			try {
				String msg = java.net.URLEncoder.encode(e.getMessage(),"utf-8"); 
				response.sendRedirect("../ls?result=1&msg=" + msg);
			} catch (IOException e1) {
				response.setStatus(500);
			}
		} catch (PermissionDeniedFileReadException e) {
			try {
				String msg = java.net.URLEncoder.encode(e.getMessage(),"utf-8"); 
				response.sendRedirect("../ls?result=201&msg=" + msg);
			} catch (IOException e1) {
				response.setStatus(500);
			}
        } catch (UnsupportedEncodingException e) {
        	response.setStatus(500);
        } catch (IOException e) {
        	response.setStatus(500);
		} 
		
		return 0;
	}
	
	@Transactional
	public int modifyFile(String path, String name) {
		
		return 0;
	}
	
	@Transactional
	public int deleteFolderOrFile(String path)
			throws PermissionDeniedFileWriteException, FileNotFoundException {
		
		Object obj = getFolderOrFile(path);
		
		if (obj instanceof Folder) {
			Folder folder = (Folder) obj;
			
			authorityService.testWriteAuthority(folder);
			
			if (!folder.getChildFolders().isEmpty() || !folder.getFiles().isEmpty()) {
				return 1;
			}
			virtualFilesystemDao.deleteFolder(folder);
		} else {
			File file = (File) obj;
			
			authorityService.testWriteAuthority(file);
			
			virtualFilesystemDao.deleteFile(file);
		}
		return 0;
	}
}
