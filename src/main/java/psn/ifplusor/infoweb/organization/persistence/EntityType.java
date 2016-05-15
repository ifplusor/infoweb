package psn.ifplusor.infoweb.organization.persistence;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "organization_entity_type")
public class EntityType implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private int code;
	private String description;
	private StructType structType;
	private Set<EntityType> parentEntitiyTypes;
	private Set<EntityType> childEntityTypes;
	
	
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
	
	@Column(name = "code")
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	@Column(name = "descn")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne()
	@JoinColumn(name = "struct_type_id", nullable = false)
	public StructType getStructType() {
		return structType;
	}
	
	public void setStructType(StructType structType) {
		this.structType = structType;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "organization_struct_rule",
			joinColumns={@JoinColumn(name = "child_entity_type_id", referencedColumnName = "id")},
			inverseJoinColumns={@JoinColumn(name = "parent_entity_type_id", referencedColumnName = "id")})
	public Set<EntityType> getParentEntitiyTypes() {
		return parentEntitiyTypes;
	}

	public void setParentEntitiyTypes(Set<EntityType> parentEntitiyTypes) {
		this.parentEntitiyTypes = parentEntitiyTypes;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "organization_struct_rule",
			joinColumns={@JoinColumn(name = "parent_entity_type_id", referencedColumnName = "id")},
			inverseJoinColumns={@JoinColumn(name = "child_entity_type_id", referencedColumnName = "id")})
	public Set<EntityType> getChildEntityTypes() {
		return childEntityTypes;
	}

	public void setChildEntityTypes(Set<EntityType> childEntityTypes) {
		this.childEntityTypes = childEntityTypes;
	}
}
