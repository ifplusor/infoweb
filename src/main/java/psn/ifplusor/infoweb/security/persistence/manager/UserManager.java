package psn.ifplusor.infoweb.security.persistence.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import psn.ifplusor.infoweb.security.persistence.domain.User;

@Repository
public class UserManager {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public List<User> getAll() {
		String hql = "from User";
		Query query = entityManager.createQuery(hql);
		
		List<User> userList = query.getResultList();
		
		return userList;
	}
}
