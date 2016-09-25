package psn.ifplusor.infoweb.security.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "security_resc")
public class Resource implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String type;
	private String string;
	private int priority;
	private String description;
	private Set<Role> roles = new HashSet<>();
	
	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "resc_type")
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "resc_string")
	public String getString() {
		return string;
	}
	
	public void setString(String string) {
		this.string = string;
	}
	
	@Column(name = "priority")
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Column(name = "descn")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToMany(mappedBy = "resources")
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
