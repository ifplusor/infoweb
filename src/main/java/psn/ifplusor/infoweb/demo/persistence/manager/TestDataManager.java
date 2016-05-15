package psn.ifplusor.infoweb.demo.persistence.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

//import org.hibernate.Query;
//import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import psn.ifplusor.infoweb.demo.persistence.domain.TestData;

@Repository
//public class TestDataManager extends HibernateEntityDao {
public class TestDataManager {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public List<TestData> getAllData() {
		String hql = "from TestData";
//		Session session = getSession();
//		Query query = session.createQuery(hql);
		Query query = entityManager.createQuery(hql);
		
		return query.getResultList();
		//return query.list();
	}
}
