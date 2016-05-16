package psn.ifplusor.infoweb.cms.service;

public class PermissionDeniedFileReadException extends PermissionDeniedFileException {

	private static final long serialVersionUID = 1L;

	public PermissionDeniedFileReadException() {
        this("没有读文件权限！");
    }
	
	public PermissionDeniedFileReadException(String message) {
        super(message);
    }
}
