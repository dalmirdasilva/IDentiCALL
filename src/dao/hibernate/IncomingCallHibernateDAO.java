package dao.hibernate;

import dao.IncomingCallDAO;
import entity.IncomingCall;

public class IncomingCallHibernateDAO
        extends GenericHibernateDAO<IncomingCall, String>
        implements IncomingCallDAO {
}