package psn.ifplusor.core.activiti;

import org.activiti.engine.impl.persistence.AbstractManager;
import org.activiti.engine.impl.persistence.entity.MembershipIdentityManager;
import org.springframework.stereotype.Repository;

/**
 * @author james
 * @version 11/6/16
 */

@Repository
public class LocalMembershipEntityManager extends AbstractManager implements MembershipIdentityManager {

	@Override
	public void createMembership(String userId, String groupId) {

	}

	@Override
	public void deleteMembership(String userId, String groupId) {

	}
}
