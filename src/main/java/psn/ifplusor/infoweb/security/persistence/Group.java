package psn.ifplusor.infoweb.security.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "security_group")
public class Group implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String groupname;
	private String description;
	private Set<User> users = new HashSet<>();
	private Set<Role> roles = new HashSet<>();
	
	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "groupname")
	public String getGroupname() {
		return groupname;
	}
	
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	@Column(name = "descn")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToMany
	@JoinTable(name = "security_group_user",
			joinColumns={@JoinColumn(name = "group_id", referencedColumnName = "id")},
			inverseJoinColumns={@JoinColumn(name = "user_id", referencedColumnName = "id")})
	public Set<User> getUsers() {
		return users;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@ManyToMany
	@JoinTable(name = "security_group_role",
			joinColumns={@JoinColumn(name = "group_id", referencedColumnName = "id")},
			inverseJoinColumns={@JoinColumn(name = "role_id", referencedColumnName = "id")})
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
