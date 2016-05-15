package psn.ifplusor.infoweb.security.persistence;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "security_user")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String username;
	private String password;
	private int status;
	private String description;
	private Set<Role> roles;
	private Set<Group> groups;
	
	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "username", unique = true, nullable = false)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "status")
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name = "descn")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany
	@JoinTable(name = "security_user_role",
			joinColumns={@JoinColumn(name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns={@JoinColumn(name = "role_id", referencedColumnName = "id")})
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "security_group_user",
			joinColumns={@JoinColumn(name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns={@JoinColumn(name = "group_id", referencedColumnName = "id")})
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
}
