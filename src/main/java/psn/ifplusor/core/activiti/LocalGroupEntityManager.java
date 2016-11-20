package psn.ifplusor.core.activiti;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.AbstractManager;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author james
 * @version 11/6/16
 */

@Repository
public class LocalGroupEntityManager extends AbstractManager implements GroupIdentityManager {

	@Override
	public Group createNewGroup(String groupId) {
		return null;
	}

	@Override
	public void insertGroup(Group group) {

	}

	@Override
	public void updateGroup(Group updatedGroup) {

	}

	@Override
	public void deleteGroup(String groupId) {

	}

	@Override
	public GroupQuery createNewGroupQuery() {
		return new GroupQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutor());
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
		return null;
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		return 0;
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		return null;
	}

	@Override
	public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		return null;
	}

	@Override
	public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
		return 0;
	}

	@Override
	public boolean isNewGroup(Group group) {
		return false;
	}
}
