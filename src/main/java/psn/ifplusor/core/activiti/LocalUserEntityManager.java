package psn.ifplusor.core.activiti;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.AbstractManager;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.stereotype.Repository;
import psn.ifplusor.infoweb.security.persistence.UserDao;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author james
 * @version 11/6/16
 */

@Repository
public class LocalUserEntityManager extends AbstractManager implements UserIdentityManager {

	@Resource(name = "UserDao")
	private UserDao userDao;

	private User genActUserFromUser(psn.ifplusor.infoweb.security.persistence.User user) {
		User actUser = new UserEntity();
		actUser.setId(user.getUsername());
		actUser.setFirstName(user.getUsername());
		actUser.setLastName(user.getDescription());
		actUser.setEmail(user.getEmail());

		return actUser;
	}

	@Override
	public User createNewUser(String userId) {
		return null;
	}

	@Override
	public void insertUser(User user) {

	}

	@Override
	public void updateUser(User updatedUser) {

	}

	@Override
	public User findUserById(String userId) {

		psn.ifplusor.infoweb.security.persistence.User user = userDao.getUserByName(userId);

		if (user == null) {
			return null;
		} else {
			return genActUserFromUser(user);
		}
	}

	@Override
	public void deleteUser(String userId) {

	}

	@Override
	public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {

		List<psn.ifplusor.infoweb.security.persistence.User> lstUsers = userDao.getAll();

		return lstUsers.stream().map(this::genActUserFromUser).collect(Collectors.toList());
	}

	@Override
	public long findUserCountByQueryCriteria(UserQueryImpl query) {
		return 0;
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		return null;
	}

	@Override
	public UserQuery createNewUserQuery() {
		return new UserQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutor());
	}

	@Override
	public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
		return null;
	}

	@Override
	public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
		return null;
	}

	@Override
	public Boolean checkPassword(String userId, String password) {
		return null;
	}

	@Override
	public List<User> findPotentialStarterUsers(String proceDefId) {
		return null;
	}

	@Override
	public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		return null;
	}

	@Override
	public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
		return 0;
	}

	@Override
	public boolean isNewUser(User user) {
		return false;
	}

	@Override
	public Picture getUserPicture(String userId) {
		return null;
	}

	@Override
	public void setUserPicture(String userId, Picture picture) {

	}
}
