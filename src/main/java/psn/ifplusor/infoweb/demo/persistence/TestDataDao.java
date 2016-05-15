package psn.ifplusor.infoweb.demo.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

//import org.hibernate.Query;
//import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import psn.ifplusor.infoweb.demo.persistence.TestData;

@Repository
public class TestDataDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<TestData> getAllData() {
		String hql = "from TestData";
		Query query = entityManager.createQuery(hql);
		
		return query.getResultList();
	}
}
