package psn.ifplusor.core.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.MembershipIdentityManager;

import javax.annotation.Resource;

/**
 * @author james
 * @version 11/6/16
 */
public class LocalMembershipEntityManagerFactory implements SessionFactory {

	@Resource
	LocalMembershipEntityManager membershipEntityManager;

	@Override
	public Class<?> getSessionType() {
		return MembershipIdentityManager.class;
	}

	@Override
	public Session openSession() {
		return membershipEntityManager;
	}
}
