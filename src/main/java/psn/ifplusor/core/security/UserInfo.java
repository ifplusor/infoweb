package psn.ifplusor.core.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UserInfo extends BaseUserDetails {

    private static final long serialVersionUID = 1L;

    private String email;
	
    public UserInfo(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities)
            throws IllegalArgumentException {
    	super(username, password, enabled, true, true, true, authorities);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
