package psn.ifplusor.infoweb.organization.persistence;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "organization_struct_type")
public class StructType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String description;
	private Set<EntityType> entityTypes;
	private EntityType topEntityType;
	
	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "name", unique = true, nullable = false)
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

	@OneToMany(mappedBy = "structType", fetch = FetchType.EAGER)
	public Set<EntityType> getEntityTypes() {
		return entityTypes;
	}

	public void setEntityTypes(Set<EntityType> entityTypes) {
		this.entityTypes = entityTypes;
	}

	@OneToOne
	@JoinColumn(name = "top_entity_type_id")
	public EntityType getTopEntityType() {
		return topEntityType;
	}

	public void setTopEntityType(EntityType topEntityType) {
		this.topEntityType = topEntityType;
	}
}
