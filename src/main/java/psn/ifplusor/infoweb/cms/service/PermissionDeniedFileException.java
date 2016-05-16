package psn.ifplusor.infoweb.cms.service;

public class PermissionDeniedFileException extends Exception {

	private static final long serialVersionUID = 1L;

	public PermissionDeniedFileException() {
        super();
    }
	
	public PermissionDeniedFileException(String message) {
        super(message);
    }
}
