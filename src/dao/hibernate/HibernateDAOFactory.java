
package dao.hibernate;

import dao.DAOFactory;
import dao.GenericDAO;
import org.hibernate.Session;

public class HibernateDAOFactory extends DAOFactory {
 
    private Session session;
    
    @Override
    public GenericDAO getNewDAO(Class daoClass) {
        return instantiateDAO(daoClass);
    }
    
    private GenericHibernateDAO instantiateDAO(Class daoClass) {
        try {
            GenericHibernateDAO dao = (GenericHibernateDAO)daoClass.newInstance();
            dao.setSession(getSession());
            return dao;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }
    
    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    private Session getSession() {
        if (session == null) {
            throw new IllegalStateException("Session has not been set on DAO before usage");
        }
        return session;
    }
}
