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
@Table(name = "security_role")
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String description;
	private Set<Resource> resources;
	private Set<User> users;
	private Set<Group> groups;
	
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
	@JoinTable(name = "security_resc_role",
			joinColumns={@JoinColumn(name = "role_id", referencedColumnName = "id")},
			inverseJoinColumns={@JoinColumn(name = "resc_id", referencedColumnName = "id")})
	public Set<Resource> getResources() {
		return resources;
	}
	
	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	
	@ManyToMany
	@JoinTable(name = "security_user_role",
			joinColumns={@JoinColumn(name = "role_id", referencedColumnName = "id")},
			inverseJoinColumns={@JoinColumn(name = "user_id", referencedColumnName = "id")})
	public Set<User> getUsers() {
		return users;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@ManyToMany
	@JoinTable(name = "security_group_role",
			joinColumns={@JoinColumn(name = "role_id", referencedColumnName = "id")},
			inverseJoinColumns={@JoinColumn(name = "group_id", referencedColumnName = "id")})
	public Set<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
}
