package psn.ifplusor.infoweb.security.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import psn.ifplusor.infoweb.security.persistence.User;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<User> getAll() {
		String hql = "from User";
		Query query = entityManager.createQuery(hql);
		
		List<User> userList = query.getResultList();
		
		return userList;
	}
	
	public User getUserByName(String username) {
		String jpql = "from User u where u.username=?";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, username);

		try {
			return (User) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
}
