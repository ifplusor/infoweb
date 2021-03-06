package psn.ifplusor.infoweb.security.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "security_user")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String username;
	private String password;
    private String email;
	private String phone;
	private int status;
	private String description;
	private Set<Role> roles = new HashSet<>();
	private Set<Group> groups = new HashSet<>();
	
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

	@Column(name = "email")
	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	@Column(name = "phone")
	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }

	@Column(name = "status")
	public int getStatus() { return status; }

	public void setStatus(int status) { this.status = status; }

	@Column(name = "descn")
	public String getDescription() { return description; }
	
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

	@ManyToMany(mappedBy = "users")
	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
}
