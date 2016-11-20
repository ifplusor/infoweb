package psn.ifplusor.core.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;

import javax.annotation.Resource;

/**
 * @author james
 * @version 11/6/16
 */
public class LocalUserEntityManagerFactory implements SessionFactory {

	@Resource
	LocalUserEntityManager userEntityManager;

	@Override
	public Class<?> getSessionType() {
		return UserIdentityManager.class;
	}

	@Override
	public Session openSession() {
		return userEntityManager;
	}
}
