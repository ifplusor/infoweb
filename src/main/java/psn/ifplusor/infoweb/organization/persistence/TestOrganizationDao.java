package psn.ifplusor.infoweb.organization.persistence;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import psn.ifplusor.infoweb.organization.persistence.EntityType;
import psn.ifplusor.infoweb.organization.persistence.StructType;

@Repository
public class TestOrganizationDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	public Set<EntityType> getAllData() {
		String hql = "from StructType";
		Query query = entityManager.createQuery(hql);
		
		List<StructType> structTypes = query.getResultList();
		
		if (structTypes != null)
			return structTypes.get(0).getEntityTypes();
		else
			return null;
	}
	
	public List<StructType> getAllStructTypes() {
		String hql = "from StructType";
		Query query = entityManager.createQuery(hql);
		
		List<StructType> structTypes = query.getResultList();
		
		return structTypes;
	}
}
