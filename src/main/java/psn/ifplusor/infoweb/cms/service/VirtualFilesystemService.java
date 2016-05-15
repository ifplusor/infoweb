package psn.ifplusor.infoweb.cms.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import psn.ifplusor.infoweb.cms.persistence.File;
import psn.ifplusor.infoweb.cms.persistence.Folder;
import psn.ifplusor.infoweb.cms.persistence.VirtualFilesystemDao;
import psn.ifplusor.infoweb.security.Service.UserService;
import psn.ifplusor.infoweb.security.persistence.User;

@Service
public class VirtualFilesystemService {
	
	@Resource
	private VirtualFilesystemDao virtualFilesystemDao;
	
	@Resource
	private UserService userService;
	
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
		return folders;
	}
	
	/**
	 * 获取子文件名列表
	 */
	private List<String> getChildFileList(Folder folder) {
		List<String> files = new ArrayList<String>();
		for (File childFile : folder.getFiles()) {
			files.add(childFile.getName());
		}
		return files;
	}

	@Transactional
	public Map<String, Object> listFolderAndFile(String path) throws FileNotFoundException {
		
		Object obj = getFolderOrFile(path);

		Map<String, Object> map = new HashMap<String, Object>();
        
		if (obj instanceof Folder) {
			Folder folder = (Folder) obj;
			
			map.put("type", 1);
        	map.put("folders", getChildFolderList(folder));
        	map.put("files", getChildFileList(folder));
		} else {
			File file = (File) obj;
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
	 */
	@Transactional
	public int createFolder(String path, String name) throws FileNotFoundException {
		
		Object obj = getFolderOrFile(path);
		
		if (obj instanceof File)
			return 1;
		
		Folder folder = (Folder) obj;
		
		if (containChildWithName(folder, name))
			return 2;
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.getUserByName(userDetails.getUsername());
		
		Folder childFolder = new Folder();
		childFolder.setParentFolder(folder);
		childFolder.setOwner(user);
		childFolder.setName(name);
		childFolder.setAuthority("rw----");	
		
		virtualFilesystemDao.saveFolder(childFolder);
		
		return 0;
	}
	
	@Transactional
	public int renameFolder(String path, String name) {
		
		return 0;
	}
	
	@Transactional
	public int createFile(String path, String name) {
		
		return 0;
	}
	
	@Transactional
	public int renameFile(String path, String name) {
		
		return 0;
	}
	
	@Transactional
	public int deleteFolderOrFile(String path) throws FileNotFoundException {
		
		Object obj = getFolderOrFile(path);
		
		if (obj instanceof Folder) {
			Folder folder = (Folder) obj;
			if (!folder.getChildFolders().isEmpty() || !folder.getFiles().isEmpty()) {
				return 1;
			}
			virtualFilesystemDao.deleteFolder(folder);
		} else {
			File file = (File) obj;
			virtualFilesystemDao.deleteFile(file);
		}
		return 0;
	}
}
