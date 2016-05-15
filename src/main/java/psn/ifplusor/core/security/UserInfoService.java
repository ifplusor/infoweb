package psn.ifplusor.core.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserInfoService implements UserDetailsService {
    private Map<String, UserInfo> userMap = null;

    public UserInfoService() {
        userMap = new HashMap<String, UserInfo>();

        UserInfo userInfo = null;
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        userInfo = new UserInfo("user", "user", true, auths);
        userInfo.setEmail("user@ifplusor.psn");
        userMap.put("user", userInfo);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        userInfo = new UserInfo("admin", "admin", true, authorities);
        userInfo.setEmail("admin@ifplusor.psn");
        userMap.put("admin", userInfo);
    }

    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException, DataAccessException {
        return userMap.get(username);
    }
}
