package psn.ifplusor.infoweb.security.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "security_role")
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String description;
	private Set<Resource> resources = new HashSet<>();
	private Set<User> users = new HashSet<>();
	private Set<Group> groups = new HashSet<>();
	
	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "descn")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToMany
	@JoinTable(name = "security_role_resc",
			joinColumns={@JoinColumn(name = "role_id", referencedColumnName = "id")},
			inverseJoinColumns={@JoinColumn(name = "resc_id", referencedColumnName = "id")})
	public Set<Resource> getResources() {
		return resources;
	}
	
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	
	@ManyToMany(mappedBy = "roles")
	public Set<User> getUsers() {
		return users;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@ManyToMany(mappedBy = "roles")
	public Set<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
}
