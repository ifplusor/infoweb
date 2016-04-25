package psn.ifplusor.infoweb.persistence.manager;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import psn.ifplusor.core.hibernate.HibernateEntityDao;
import psn.ifplusor.infoweb.persistence.domain.TestData;

@Repository
public class TestDataManager extends HibernateEntityDao{

	@Transactional
	public List<TestData> getAllData() {
		String hql = "from TestData";
		Session session = getSession();
		Query query = session.createQuery(hql);
		
		return query.list();
	}
}
