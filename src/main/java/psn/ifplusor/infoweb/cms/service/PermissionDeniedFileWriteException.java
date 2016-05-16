package psn.ifplusor.infoweb.cms.service;

public class PermissionDeniedFileWriteException extends PermissionDeniedFileException {

	private static final long serialVersionUID = 1L;

	public PermissionDeniedFileWriteException() {
        this("没有读文件权限！");
    }
	
	public PermissionDeniedFileWriteException(String message) {
        super(message);
    }
}
