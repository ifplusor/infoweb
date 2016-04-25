package psn.ifplusor.core.hibernate;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateEntityDao {
	
	@Resource
	public SessionFactory sessionFactory;
	
	/*
     * gerCurrentSession 会自动关闭session，使用的是当前的session事务
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /*
     * openSession 需要手动关闭session 意思是打开一个新的session
     */
    public Session getNewSession() {
        return sessionFactory.openSession();
    }
    
    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }
}
