package psn.ifplusor.infoweb.cms.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import psn.ifplusor.infoweb.cms.persistence.File;
import psn.ifplusor.infoweb.cms.persistence.Folder;
import psn.ifplusor.infoweb.security.Service.UserService;
import psn.ifplusor.infoweb.security.persistence.Group;
import psn.ifplusor.infoweb.security.persistence.User;

@Service
public class VirtualFilesystemAuthorityService {
	
	private static final Logger logger = LoggerFactory.getLogger(VirtualFilesystemAuthorityService.class);
	
	@Resource
	private UserService userService;
	
	public User getCurrentUser() {
		logger.debug("get current user entity.");
		return userService.getCurrentUser();
	}
	
	public int relation(User user) {
		User my = getCurrentUser();
		
		if (my.equals(user)) {
			return 0;
		}
		
		for (Group myGroup : my.getGroups()) {
			for (Group ownerGroup : user.getGroups()) {
				if (myGroup.equals(ownerGroup)) {
					return 1;
				}
			}
		}
		
		return 2;
	}
	
	public int relation(Folder folder) {
		int status = relation(folder.getOwner());
		logger.debug("relation code for folder: " + status);
		return status;
	}
	
	public int relation(File file) {
		int status = relation(file.getOwner());
		logger.debug("relation code for file: " + status);
		return status;
	}
	
	public void testReadAuthority(Folder folder) throws PermissionDeniedFileReadException {
		logger.debug("test folder read authority.");
		
		String auth = folder.getAuthority();
		
		if (auth.charAt(relation(folder)*2) != 'r') {
			logger.debug("could not read folder. folder: \"" + folder.getName() + "\" auth: \"" + auth + "\"");
			throw new PermissionDeniedFileReadException("没有目录的读权限！");
		}
	}
	
	public void testReadAuthority(File file) throws PermissionDeniedFileReadException {
		logger.debug("test file read authority.");
		
		String auth = file.getAuthority();
		
		logger.debug("file auth: " + auth);
		if (auth.charAt(relation(file)*2) != 'r') {
			logger.debug("could not read file. file: \"" + file.getName() + "\" auth: \"" + auth + "\"");
			throw new PermissionDeniedFileReadException("没有文件的读权限！");
		}
	}
	
	public void testWriteAuthority(Folder folder) throws PermissionDeniedFileWriteException {
		logger.debug("test folder write authority.");
		
		String auth = folder.getAuthority();
		
		if (auth.charAt(relation(folder)*2+1) != 'w') {
			logger.debug("could not write folder. folder: \"" + folder.getName() + "\" auth: \"" + auth + "\"");
			throw new PermissionDeniedFileWriteException("没有目录的写权限！");
		}
	}
	
	public void testWriteAuthority(File file) throws PermissionDeniedFileWriteException {
		logger.debug("test file write authority.");
		
		String auth = file.getAuthority();
		
		if (auth.charAt(relation(file)*2+1) != 'w') {
			logger.debug("could not write file. file: \"" + file.getName() + "\" auth: \"" + auth + "\"");
			throw new PermissionDeniedFileWriteException("没有文件的写权限！");
		}
	}
}
