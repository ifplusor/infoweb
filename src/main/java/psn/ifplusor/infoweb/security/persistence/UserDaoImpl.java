package psn.ifplusor.infoweb.security.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import psn.ifplusor.core.common.IdGenerator;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.List;

@Repository("UserDao")
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Resource(name = "GroupDao")
	private GroupDao groupDao;

	@Override
	public List<User> getAll() {
		String jpql = "from User";
		Query query = entityManager.createQuery(jpql);

		try {
			 return (List<User>) query.getResultList();
		} catch (IllegalStateException ise) {
			return null;
		} catch (QueryTimeoutException qte) {
			return null;
		} catch (TransactionRequiredException tre) {
			return null;
		} catch (PessimisticLockException ple) {
			return null;
		} catch (LockTimeoutException lte) {
			return null;
		} catch (PersistenceException pe) {
			return null;
		}
	}

	@Override
	public User getUserByName(String username) {
		String jpql = "from User u where u.username=?";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, username);

		try {
			return (User) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (NonUniqueResultException nure) {
			return null;
		} catch (IllegalStateException ise) {
			return null;
		} catch (QueryTimeoutException qte) {
			return null;
		} catch (TransactionRequiredException tre) {
			return null;
		} catch (PessimisticLockException ple) {
			return null;
		} catch (LockTimeoutException lte) {
			return null;
		} catch (PersistenceException pe) {
			return null;
		}
	}

	@Override
	@Transactional
	public boolean addUser(String username, String password) {
		Group group = groupDao.getGroupByName("user");
		if (group == null) {
			return false;
		}

		User user = new User();
		user.setId(IdGenerator.get().nextId());
		user.setUsername(username);
		user.setPassword(password);
		user.setStatus(1);

		try {
			entityManager.persist(user);

			group.getUsers().add(user);
			entityManager.persist(group);

			return true;
		} catch (EntityExistsException eee) {
			return false;
		} catch (IllegalArgumentException iae) {
			return false;
		} catch (TransactionRequiredException tre) {
			tre.printStackTrace();
			return false;
		}
	}
}
