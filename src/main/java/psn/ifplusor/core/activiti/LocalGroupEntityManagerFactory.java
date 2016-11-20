package psn.ifplusor.core.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;

import javax.annotation.Resource;

/**
 * @author james
 * @version 11/6/16
 */
public class LocalGroupEntityManagerFactory implements SessionFactory {

	@Resource
	LocalGroupEntityManager groupEntityManager;

	@Override
	public Class<?> getSessionType() {
		return GroupIdentityManager.class;
	}

	@Override
	public Session openSession() {
		return groupEntityManager;
	}
}
