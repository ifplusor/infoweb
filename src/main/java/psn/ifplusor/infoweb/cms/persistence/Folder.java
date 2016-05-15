package psn.ifplusor.infoweb.cms.persistence;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import psn.ifplusor.infoweb.security.persistence.User;

@Entity
@Table(name = "cms_folder")
public class Folder implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private User owner;
	private String name;
	private String authority;
	private String description;
	private Folder parentFolder;
	private Set<Folder> childFolders;
	private Set<File> files;
	
	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "auth", nullable = false)
	public String getAuthority() {
		return authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	@Column(name = "descn")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne
	@JoinColumn(name = "parent_folder_id", nullable = false)
	public Folder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	@OneToMany(mappedBy = "parentFolder")
	public Set<Folder> getChildFolders() {
		return childFolders;
	}

	public void setChildFolders(Set<Folder> childFolders) {
		this.childFolders = childFolders;
	}

	@OneToMany(mappedBy = "parentFolder")
	public Set<File> getFiles() {
		return files;
	}
	
	public void setFiles(Set<File> files) {
		this.files = files;
	}
}
